/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.Interceptor
import com.meniga.sdk.helpers.KeyVal
import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.Merge
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort
import com.meniga.sdk.models.accounts.enums.AccountCategory
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.io.Serializable

/**
 * An account in the Meniga System containing transactions.
 *
 * @property id The account id for the account.
 * @property accountIdentifier The identifier for the account set by the originating bank. This identifier is used when getting account statements.
 * @property realmIdentifier The realm identifier for this account. Realm here can mean e.g. a bank institute.
 * @property realmAccountTypeId The realm account type for this account. Realm here can mean e.g. a bank institute.
 * @property accountTypeId The id of the account type.
 * @property name The name of the account set by the user or the originating bank.
 * @property balance The current balance of the account.
 * @property originalBalance The balance for the account when it was created.
 * @property committedAmount The committed amount to this account.
 * @property limit The limit or overdraft for the account.
 * @property accountClass The account class that is for example used for displaying an image for this account in CSS.
 * @property organizationIdentifier A code that identifies the organization associated with this account globally, such as a Swift code.
 * @property realmCredentialsId Identifier for an online bank user that owns this account.
 * @property accountAuthorizatonType Indicates the type of account authorization during account aggregation = ['0', '1', '2', '3'].
 * @property orderId Accounts are ordered in ascending order by this order key.
 * @property isImportAccount Indicates if this account causes transactions to be imported manually or not.
 * @property lastUpdate  Last update time of this account.
 * @property personId Identifier for the person that owns this account.
 * @property userEmail Email for the person that owns this account.
 * @property createDate Creation date for this account.
 * @property accountCategory Indicates if this is a Current account, Credit account or Saving account = ['0', '1', '2', '3', '4', '5', '21', '22'].
 * @property emergencyFundBalanceLimit If this account is used to track Life goals, this amount indicates the amount that is not used for goal allocation.
 * @property isInactive True if the account is inactive.
 * @property attachedToUserDate DateTime when the user added thia account.
 * @property isHidden True if the account should be marked as hidden. It is up to each component/widget what to do with this information.
 * @property isDisabled True if the this account should be excluded from calculations.
 * @property metadata List of key-vals for various metadata.
 *
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class MenigaAccount internal constructor(
        val id: Long = 0,
        val accountIdentifier: String? = null,
        val realmIdentifier: String? = null,
        val realmAccountTypeId: Int = 0,
        val accountTypeId: Int = 0,
        private var _name: String? = null,
        val accountCategory: AccountCategory? = null,
        private var _emergencyFundBalanceLimit: MenigaDecimal? = null,
        val balance: MenigaDecimal? = null,
        val originalBalance: MenigaDecimal? = null,
        val committedAmount: MenigaDecimal? = null,
        val limit: MenigaDecimal? = null,
        val accountClass: String? = null,
        val organizationIdentifier: String? = null,
        val realmCredentialsId: Long? = null,
        val accountAuthorizatonType: AccountAuthorizationType? = null,
        private var _orderId: Int = 0,
        val isImportAccount: Boolean = false,
        val lastUpdate: DateTime? = null,
        val personId: Long? = null,
        val userEmail: String? = null,
        val createDate: DateTime? = null,
        val inactive: Boolean = false,
        val attachedToUserDate: DateTime? = null,
        private var _isHidden: Boolean = false,
        private var _isDisabled: Boolean = false,
        val metadata: List<MenigaAccountMetaData> = emptyList()
) : Parcelable, Serializable, Cloneable {

    @Deprecated("Use inactive instead.", replaceWith = ReplaceWith("inactive"))
    val isInactive: Boolean
        get() = inactive

    var name: String?
        get() = _name
        set(value) {
            _name = value
        }

    var orderId: Int
        get() = _orderId
        set(value) {
            _orderId = value
        }

    var isHidden: Boolean
        get() = _isHidden
        set(value) {
            _isHidden = value
        }

    var isDisabled: Boolean
        get() = _isDisabled
        set(value) {
            _isDisabled = value
        }

    var emergencyFundBalanceLimit: MenigaDecimal?
        get() = _emergencyFundBalanceLimit
        set(value) {
            _emergencyFundBalanceLimit = value
        }

    @Deprecated("Use isDisabled() instead", replaceWith = ReplaceWith("isDisabled()"))
    fun getIsDisabled(): Boolean = isDisabled


    @Deprecated("Use isHidden() instead", replaceWith = ReplaceWith("isHidden()"))
    fun getIsHidden(): Boolean = isHidden

    @Deprecated("Use isHidden(Boolean) instead", replaceWith = ReplaceWith("isHidden(Boolean)"))
    fun setIsHidden(hidden: Boolean) {
        isHidden = hidden
    }

    override fun clone(): MenigaAccount = copy()

    /**
     * @return List of key-vals for various metadata as a map.
     */
    val metaDataAsMap: Map<String, String?>
        get() = metadata.associate { it.name to it.value }

    /*
	--- API calls below ---
	 */

    /**
     * Updates the data in the account on the server, only the following fields can be updated:
     * name and isHidden
     *
     * @return A Task indicating if the update was successful
     */
    fun update(): Result<Void> {
        return MenigaAccount.apiOperator.updateAccount(this)
    }

    /**
     * Deletes the account
     *
     * @return A Result of type void, the task will have an error and be marked as failed if deletion is not successful
     */
    fun delete(): Result<Void> {
        return MenigaAccount.apiOperator.deleteAccount(id)
    }

    /**
     * Gets a list of all parameters for the account instance
     *
     * @return List of parameters for the account
     */
    fun fetchMetadata(): Result<List<KeyVal<String, String>>> {
        return MenigaAccount.apiOperator.getMetadata(id)
    }

    /**
     * Adds or updates a metadata key value pair
     *
     * @return The updated key value metadata
     */
    fun updateMetadata(keyVal: KeyVal<String, String>): Result<KeyVal<String, String>> {
        return MenigaAccount.apiOperator.updateMetadata(id, keyVal)
    }

    /**
     * Gets a value for a single account parameter
     *
     * @param key The key who's value we want to get
     * @return The keyval for the account parameter
     */
    fun fetchMetadataKeyVal(key: String): Result<KeyVal<String, String>> {
        return MenigaAccount.apiOperator.getMetadataKeyVal(id, key)
    }

    /**
     * Gets a list of account balance history entries for this account
     *
     * @param from The start date for the entries to be fetched.
     * @param to   The end date for the entries to be fetched.
     * @param sort Allows sorting by BalanceDate and Balance. Prefixing the property by "-" indicates ordering by DESC else ASC. The default is BalanceDate.
     * @return A list of the account balance history matching the given criteria
     */
    fun fetchBalanceHistory(from: DateTime, to: DateTime, sort: AccountBalanceHistorySort): Result<List<MenigaAccountBalanceHistory>> {
        return MenigaAccount.apiOperator.getBalanceHistory(id, from, to, sort)
    }

    /**
     * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
     *
     * @return A Result of type void, the task will have an error and be marked as failed if it is not successful
     */
    fun refresh(): Result<MenigaAccount> {
        val task = MenigaAccount.fetch(this.id)
        return MenigaSDK.getMenigaSettings().taskAdapter.intercept(task, object : Interceptor<MenigaAccount>() {
            override fun onFinished(result: MenigaAccount, failed: Boolean) {
                Merge.merge(this@MenigaAccount, result)
            }
        })
    }

    companion object {

        private lateinit var apiOperator: MenigaAccountOperations

        /**
         * Sets the api operator for doing api calls
         *
         * @param operator An object that implements the MenigaAccountOperations interface for carrying out api operations on this class.
         */
        @JvmStatic
        fun setOperator(operator: MenigaAccountOperations) {
            MenigaAccount.apiOperator = operator
        }

        /**
         * Retrieves an account with id
         *
         * @param id The id of the account to retrieve
         * @return A Result containing the account object
         */
        @JvmStatic
        fun fetch(id: Long): Result<MenigaAccount> {
            return MenigaAccount.apiOperator.getAccount(id)
        }

        /**
         * Get all accounts the user has
         *
         * @return A Result containing a list of all the accounts the user has
         */
        @JvmStatic
        fun fetch(): Result<List<MenigaAccount>> {
            return MenigaAccount.apiOperator.getAccounts(false, false)
        }

        @JvmStatic
        fun fetch(includeHidden: Boolean, includeDisabled: Boolean): Result<List<MenigaAccount>> {
            return MenigaAccount.apiOperator.getAccounts(includeHidden, includeDisabled)
        }

        /**
         * Gets a list of all available account types.
         *
         * @return A list of all account types.
         */
        @JvmStatic
        fun fetchAccountTypes(): Result<List<MenigaAccountType>> {
            return MenigaAccount.apiOperator.accountTypes
        }

        @JvmStatic
        fun fetchCategories(): Result<List<AccountCategory>> {
            return MenigaAccount.apiOperator.categories
        }

        /**
         * Gets a list of all available values as an array of NameId values.
         *
         * @return A list of all authorization types
         */
        @JvmStatic
        fun fetchAuthorizationTypes(): Result<List<MenigaAuthorizationType>> {
            return MenigaAccount.apiOperator.accountAuthorizationTypes
        }
    }
}
