/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.sync

import android.os.Handler
import android.os.Parcelable
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.Interceptor
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.sync.operators.MenigaSyncOperations
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.io.Serializable

/**
 * Represents the status of a sync procedure.
 */
@Parcelize
@SuppressWarnings("ParcelCreator")
data class MenigaSync(
        val syncHistoryId: Long = 0,
        val isSyncDone: Boolean = false,
        val syncSessionStartTime: DateTime? = null,
        val realmSyncResponses: List<RealmSyncResponse>? = null
): Serializable, Parcelable, Cloneable {

    /**
     * @return Number of new transactions that came into the system as a result of the sync.
     */
    val numNewTransactions: Int
        get() = realmSyncResponses
                ?.flatMap { it.getAccountSyncStatuses() }
                ?.sumBy { it.getTransactionsProcessed() }
                ?: 0

    @Throws(CloneNotSupportedException::class)
    override fun clone(): MenigaSync {
        return super.clone() as MenigaSync
    }

    /**
     * A class used as a callback. It will be called when the sync procedure is done or if it fails or times out.
     */
    abstract class PostSyncCallback {
        var hasNewData: Boolean = false
        /**
         * @return The start time stamp.
         */
        var startTimeStamp: Long? = null

        /**
         * @return Number of new transactions that came into the system as a result of the sync.
         */
        var numNewTransactions: Int = 0
        var sync: MenigaSync? = null

        abstract fun onFailure(exception: Exception)

        abstract fun onFinished()

        abstract fun onTimedOut()
    }

    /*
	--- API calls below ---
	 */

    private fun checkSync(postSync: PostSyncCallback, timeout: Long, interval: Long) {
        if (postSync.startTimeStamp == null) {
            postSync.startTimeStamp = DateTime.now().millis
        }
        val handler = Handler()
        handler.postDelayed({
            val task = getSyncStatus()
            MenigaSDK.getMenigaSettings().taskAdapter.intercept(task, object : Interceptor<MenigaSyncStatus>() {
                override fun onFinished(result: MenigaSyncStatus?, failed: Boolean) {

                    val delta = DateTime.now().millis - postSync.startTimeStamp!!
                    if (failed || result == null) {
                        postSync.onFailure(Exception("Failure syncing"))
                    } else if (result.getHasCompletedSyncSession()) {
                        val subTask = MenigaSync.fetch(syncHistoryId)
                        MenigaSDK.getMenigaSettings().taskAdapter.intercept(subTask, object : Interceptor<MenigaSync>() {
                            override fun onFinished(result: MenigaSync?, failed: Boolean) {
                                if (failed || result == null) {
                                    postSync.onFailure(Exception("Failure syncing"))
                                }
                                postSync.hasNewData = result != null && result.hasNewData()
                                postSync.numNewTransactions = result?.numNewTransactions ?: 0
                                postSync.sync = result
                                postSync.onFinished()
                            }
                        })
                    } else if (delta > timeout) {
                        postSync.onTimedOut()
                    } else {
                        checkSync(postSync, timeout, interval)
                    }
                }
            })
        }, interval)
    }

    private fun hasNewData(): Boolean = realmSyncResponses
            ?.flatMap { it.getAccountSyncStatuses() }
            ?.firstOrNull { it.getTransactionsProcessed() > 0 } != null

    /**
     * Checks to see if the synchronization operation has finished
     *
     * @return A Task containing a boolean indicating if the synchronization is done or not
     */
    @Deprecated("Use static getSyncStatus instead", replaceWith = ReplaceWith("getSyncStatus"))
    fun isSyncDone(): Result<MenigaSyncStatus> = apiOperator.syncStatus

    companion object {
        private const val SYNC_CHECK_INTERVAL_MILLISECONDS: Long = 1000

        private lateinit var apiOperator: MenigaSyncOperations

        /**
         * Sets the api operator for doing api calls
         *
         * @param operator An object that implements the MenigaSyncOperations interface for carrying out api operations on this class.
         */
        @JvmStatic
        fun setOperator(operator: MenigaSyncOperations) {
            MenigaSync.apiOperator = operator
        }

        /**
         * Gets a sync object that was created by [start]
         *
         * @param syncHistoryId The id of the sync object
         * @return The sync object
         */
        @JvmStatic
        fun fetch(syncHistoryId: Long): Result<MenigaSync> {
            return MenigaSync.apiOperator.getSync(syncHistoryId)
        }

        /**
         * Use [syncRealms] instead.
         */
        @Deprecated("Use syncRealms instead")
        @JvmStatic
        fun start(timeout: Long): Result<MenigaSync> {
            return launchSync(null, timeout, null)
        }

        /**
         * Use [syncRealms] instead.
         */
        @Deprecated("Use syncRealms instead")
        @JvmStatic
        fun start(timeout: Long, interval: Long, onDone: Interceptor<MenigaSync>): Result<MenigaSync> {
            return launchSync(null, timeout, onDone)
        }

        /**
         * Starts the accounts synchronization process and also returns a MenigaSync object
         * that contains further details.
         *
         * @param timeout Timeout for the sync procedure.
         * @return A new sync object created by starting the sync procedure
         */
        @JvmStatic
        fun syncRealms(timeout: Long): Result<MenigaSync> {
            return launchSync(null, timeout, null)
        }

        /**
         * Starts the accounts synchronization process and also returns a MenigaSync object
         * that contains further details.
         *
         * @param timeout  The amount of time the background process should try to check if the sync has completed before terminating.
         * @return A new sync object.
         */
        @JvmStatic
        fun syncRealms(timeout: Long, onDone: Interceptor<MenigaSync>? = null): Result<MenigaSync> {
            return launchSync(null, timeout, onDone)
        }

        /**
         * Starts the accounts synchronization process for a specific realm and also returns a MenigaSync object
         * that contains further details.
         *
         * @param realmUserId  The id of the realm account user - a realm is a "department" in e.g. a bank. Most organizations (most often banks) have only one but some
         * large organizations can have many realms (e.g. insurance, banking, credit cards and so on). This id identifies the user's realm user account.
         * @param timeout  The amount of time the background process should try to check if the sync has completed before terminating.
         * @return A new sync object.
         */
        @JvmStatic
        fun syncRealm(realmUserId: Long, timeout: Long, onDone: Interceptor<MenigaSync>? = null): Result<MenigaSync> {
            return launchSync(realmUserId, timeout, onDone)
        }

        private fun launchSync(realmUserId: Long?, timeout: Long, onDone: Interceptor<MenigaSync>?): Result<MenigaSync> {
            val task = getSyncStatus().task.continueWithTask { task ->
                if (task.isFaulted || task.result.hasCompletedSyncSession) {
                    apiOperator.startSync(realmUserId, timeout).task
                } else {
                    fetch(task.result.getSynchronizationStatus().syncHistoryId).task
                }
            }
            return MenigaSDK.getMenigaSettings().taskAdapter.intercept(task, object : Interceptor<MenigaSync>() {
                override fun onFinished(result: MenigaSync?, failed: Boolean) {
                    if (result == null) {
                        onDone?.onFinished(null, true)
                        return
                    }
                    result.checkSync(object : PostSyncCallback() {
                        override fun onFailure(exception: Exception) {
                            onDone?.onFinished(null, true)
                        }

                        override fun onFinished() {
                            onDone?.onFinished(sync, false)
                        }

                        override fun onTimedOut() {
                            onDone?.onFinished(null, true)
                        }
                    }, timeout, SYNC_CHECK_INTERVAL_MILLISECONDS)
                }
            })
        }

        /**
         * Checks to see the status of all synchronization operations on the back end
         *
         * @return A Task indicating if the synchronization is done or not
         */
        @JvmStatic
        fun getSyncStatus(): Result<MenigaSyncStatus> = apiOperator.syncStatus
    }
}
