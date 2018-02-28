/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.Interceptor
import com.meniga.sdk.helpers.KeyVal
import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.Merge
import com.meniga.sdk.models.StateObject
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort
import com.meniga.sdk.models.accounts.enums.AccountCategory
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations
import com.meniga.sdk.models.transactions.MetaData
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
 * @property accountTypeId
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
 * @property accountType
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
class MenigaAccount internal constructor(
        val id: Long = 0,
        val accountIdentifier: String? = null,
        val realmIdentifier: String? = null,
        val realmAccountTypeId: Int = 0,
        val accountTypeId: Int = 0,
        @SerializedName("name") var _name: String? = null,
        val accountCategory: AccountCategory? = null,
        @SerializedName("emergencyFundBalanceLimit") var _emergencyFundBalanceLimit: MenigaDecimal? = null,
        val balance: MenigaDecimal? = null,
        val originalBalance: MenigaDecimal? = null,
        val committedAmount: MenigaDecimal? = null,
        val limit: MenigaDecimal? = null,
        val accountClass: String? = null,
        val organizationIdentifier: String? = null,
        val realmCredentialsId: Long? = null,
        val accountAuthorizatonType: AccountAuthorizationType? = null,
        @SerializedName("orderId") var _orderId: Int = 0,
        val isImportAccount: Boolean = false,
        val lastUpdate: DateTime? = null,
        val personId: Long? = null,
        val userEmail: String? = null,
        val createDate: DateTime? = null,
        val isInactive: Boolean = false,
        val attachedToUserDate: DateTime? = null,
        @SerializedName("isHidden") var _isHidden: Boolean = false,
        @SerializedName("isDisabled") var _isDisabled: Boolean = false,
        val metadata: List<MetaData>? = null
) : StateObject(), Parcelable, Serializable, Cloneable {

    var name: String?
        get() = _name
        set(value) {
            if (hasChanged(_name, value)) {
                changed()
                _name = value
            }
        }

    var orderId: Int
        get() = _orderId
        set(value) {
            if (hasChanged(_orderId, value)) {
                changed()
                _orderId = value
            }
        }

    var isHidden: Boolean
        get() = _isHidden
        set(value) {
            if (hasChanged(_isHidden, value)) {
                changed()
                _isHidden = value
            }
        }

    var isDisabled: Boolean
        get() = _isDisabled
        set(value) {
            if (hasChanged(_isDisabled, value)) {
                changed()
                _isDisabled = value
            }
        }

    var emergencyFundBalanceLimit: MenigaDecimal?
        get() = _emergencyFundBalanceLimit
        set(value) {
            if (hasChanged(_emergencyFundBalanceLimit, value)) {
                changed()
                _emergencyFundBalanceLimit = value
            }
        }

    @Deprecated("Use isDisabled() instead", replaceWith = ReplaceWith("isDisabled()"))
    fun getIsDisabled(): Boolean = isDisabled


    @Deprecated("Use isHidden() instead", replaceWith = ReplaceWith("isHidden()"))
    fun getIsHidden(): Boolean = isHidden

    @Deprecated("Use isHidden(Boolean) instead", replaceWith = ReplaceWith("isHidden(Boolean)"))
    fun setIsHidden(hidden: Boolean) {
        isHidden = hidden
    }

    override fun clone(): MenigaAccount = super<StateObject>.clone() as MenigaAccount

    /**
     * @return List of key-vals for various metadata as a map.
     */
    val metaDataAsMap: Map<String, String>
        get() = metadata?.associate { it.name to it.value } ?: emptyMap()

    override fun revertToRevision(lastRevision: StateObject) {
        if (lastRevision is MenigaAccount) {
            name = lastRevision.name
            orderId = lastRevision.orderId
            emergencyFundBalanceLimit = lastRevision.emergencyFundBalanceLimit
            isHidden = lastRevision.isHidden
            isDisabled = lastRevision.isDisabled
        }
    }

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
        val task = MenigaAccount.apiOperator.updateAccount(this)
        return MenigaSDK.getMenigaSettings().taskAdapter.intercept(task, object : Interceptor<Void>() {
            override fun onFinished(result: Void, failed: Boolean) {
                if (!failed) {
                    this@MenigaAccount.resetState()
                }
            }
        })
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MenigaAccount

        if (id != other.id) return false
        if (accountIdentifier != other.accountIdentifier) return false
        if (realmIdentifier != other.realmIdentifier) return false
        if (realmAccountTypeId != other.realmAccountTypeId) return false
        if (accountTypeId != other.accountTypeId) return false
        if (_name != other._name) return false
        if (accountCategory != other.accountCategory) return false
        if (_emergencyFundBalanceLimit != other._emergencyFundBalanceLimit) return false
        if (balance != other.balance) return false
        if (originalBalance != other.originalBalance) return false
        if (committedAmount != other.committedAmount) return false
        if (limit != other.limit) return false
        if (accountClass != other.accountClass) return false
        if (organizationIdentifier != other.organizationIdentifier) return false
        if (realmCredentialsId != other.realmCredentialsId) return false
        if (accountAuthorizatonType != other.accountAuthorizatonType) return false
        if (_orderId != other._orderId) return false
        if (isImportAccount != other.isImportAccount) return false
        if (lastUpdate != other.lastUpdate) return false
        if (personId != other.personId) return false
        if (userEmail != other.userEmail) return false
        if (createDate != other.createDate) return false
        if (isInactive != other.isInactive) return false
        if (attachedToUserDate != other.attachedToUserDate) return false
        if (_isHidden != other._isHidden) return false
        if (_isDisabled != other._isDisabled) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (accountIdentifier?.hashCode() ?: 0)
        result = 31 * result + (realmIdentifier?.hashCode() ?: 0)
        result = 31 * result + realmAccountTypeId
        result = 31 * result + accountTypeId
        result = 31 * result + (_name?.hashCode() ?: 0)
        result = 31 * result + (accountCategory?.hashCode() ?: 0)
        result = 31 * result + (_emergencyFundBalanceLimit?.hashCode() ?: 0)
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + (originalBalance?.hashCode() ?: 0)
        result = 31 * result + (committedAmount?.hashCode() ?: 0)
        result = 31 * result + (limit?.hashCode() ?: 0)
        result = 31 * result + (accountClass?.hashCode() ?: 0)
        result = 31 * result + (organizationIdentifier?.hashCode() ?: 0)
        result = 31 * result + (realmCredentialsId?.hashCode() ?: 0)
        result = 31 * result + (accountAuthorizatonType?.hashCode() ?: 0)
        result = 31 * result + _orderId
        result = 31 * result + isImportAccount.hashCode()
        result = 31 * result + (lastUpdate?.hashCode() ?: 0)
        result = 31 * result + (personId?.hashCode() ?: 0)
        result = 31 * result + (userEmail?.hashCode() ?: 0)
        result = 31 * result + (createDate?.hashCode() ?: 0)
        result = 31 * result + isInactive.hashCode()
        result = 31 * result + (attachedToUserDate?.hashCode() ?: 0)
        result = 31 * result + _isHidden.hashCode()
        result = 31 * result + _isDisabled.hashCode()
        result = 31 * result + (metadata?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "MenigaAccount(id=$id, accountIdentifier=$accountIdentifier, realmIdentifier=$realmIdentifier, realmAccountTypeId=$realmAccountTypeId, accountTypeId=$accountTypeId, _name=$_name, accountCategory=$accountCategory, _emergencyFundBalanceLimit=$_emergencyFundBalanceLimit, balance=$balance, originalBalance=$originalBalance, committedAmount=$committedAmount, limit=$limit, accountClass=$accountClass, organizationIdentifier=$organizationIdentifier, realmCredentialsId=$realmCredentialsId, accountAuthorizatonType=$accountAuthorizatonType, _orderId=$_orderId, isImportAccount=$isImportAccount, lastUpdate=$lastUpdate, personId=$personId, userEmail=$userEmail, createDate=$createDate, isInactive=$isInactive, attachedToUserDate=$attachedToUserDate, _isHidden=$_isHidden, _isDisabled=$_isDisabled, metadata=$metadata)"
    }

    companion object {

        lateinit var apiOperator: MenigaAccountOperations

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
