package com.meniga.sdk.models.eventtracking

import android.annotation.SuppressLint
import android.os.Parcelable
import com.meniga.sdk.models.eventtracking.operators.MenigaEventTrackingOperations
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import com.meniga.sdk.helpers.Result

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 4.6.2018.
 *
 * An analytics event object for tracking specific things in the Meniga system such as dialogs and offers
 *
 * @property trackingType The type of event that is being tracked, e.g. dialog, offer etc.
 * @property trackingState The event that is being tracked for the type, such as seen, clicken and so on.
 * @property trackerId The id of the entity that is being tracked, such as dialog id, offer id etc.
 * @property media The platform where the event is being tracked, such as mobile_android, web, mobile_iso and so on.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class MenigaEventTracking internal constructor(
        val trackingType: String = "",
        val trackingState: String = "",
        val trackerId: Long = 0,
        val media: String = ""
) : Parcelable, Serializable, Cloneable {

    override fun clone(): MenigaEventTracking = copy()

    /**
     * Tracks this event into the tracking system
     *
     * @return A Void result
     */
    fun track(): Result<Void> {
        return MenigaEventTracking.apiOperator.track(this)
    }

    companion object {
        private lateinit var apiOperator: MenigaEventTrackingOperations

        @JvmStatic
        fun setOperator(operator: MenigaEventTrackingOperations) {
            MenigaEventTracking.apiOperator = operator
        }

        @JvmStatic
        fun build(trackingType: String, trackingState: String, trackerId: Long, media: String): MenigaEventTracking {
            return MenigaEventTracking(trackingType, trackingState, trackerId, media);
        }
    }
}
