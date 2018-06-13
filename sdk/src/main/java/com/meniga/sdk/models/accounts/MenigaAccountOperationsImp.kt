/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.KeyVal
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort
import com.meniga.sdk.models.accounts.enums.AccountCategory
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations
import com.meniga.sdk.webservices.account.Account
import com.meniga.sdk.webservices.account.AccountAuthorizationTypeName
import com.meniga.sdk.webservices.account.AccountBalanceHistory
import com.meniga.sdk.webservices.account.AccountMetaData
import com.meniga.sdk.webservices.account.AccountType
import com.meniga.sdk.webservices.account.AccountTypeCategory
import com.meniga.sdk.webservices.account.UpdateAccount
import com.meniga.sdk.webservices.account.UpdateAccountMetadata
import com.meniga.sdk.webservices.requests.DeleteAccount
import com.meniga.sdk.webservices.requests.GetAccount
import com.meniga.sdk.webservices.requests.GetAccountBalanceHistory
import com.meniga.sdk.webservices.requests.GetAccountMetadata
import com.meniga.sdk.webservices.requests.GetAccountMetadataKeyVal
import com.meniga.sdk.webservices.requests.GetAccountTypes
import com.meniga.sdk.webservices.requests.GetAccounts
import com.meniga.sdk.webservices.requests.GetAuthorizationTypes
import org.joda.time.DateTime

internal class MenigaAccountOperationsImp : MenigaAccountOperations {
    override fun getAccount(id: Long): Result<MenigaAccount> {
        val request = GetAccount().also {
            it.accountId = id
        }
        return MenigaSDK.executor().getAccount(request)
                .map { it.toMenigaAccount() }
    }

    override fun getAccounts(includeHidden: Boolean, includeDisabled: Boolean): Result<List<MenigaAccount>> {
        val request = GetAccounts().also {
            it.includeHidden = includeHidden
            it.includeDisabled = includeDisabled
        }
        return MenigaSDK.executor().getAccounts(request)
                .map { it.map { it.toMenigaAccount() } }
    }

    override fun updateAccount(account: MenigaAccount): Result<Void> {
        val request = UpdateAccount().also {
            it.id = account.id
            it.orderId = account.orderId
            it.name = account.name
            it.isHidden = account.isHidden
            it.isDisabled = account.isDisabled
            it.emergencyFundBalanceLimit = account.emergencyFundBalanceLimit?.doubleValue()
        }
        return MenigaSDK.executor().updateAccount(request)
    }

    override fun deleteAccount(accountId: Long): Result<Void> {
        val request = DeleteAccount().also {
            it.accountId = accountId
        }
        return MenigaSDK.executor().deleteAccount(request)
    }

    override fun getAccountTypes(): Result<List<MenigaAccountType>> {
        val req = GetAccountTypes()
        return MenigaSDK.executor().getAccountTypes(req)
                .map { it.map { it.toMenigaAccountType() } }
    }

    override fun getAccountAuthorizationTypes(): Result<List<MenigaAuthorizationType>> {
        val req = GetAuthorizationTypes()
        return MenigaSDK.executor().getAccountAuthorizationTypes(req)
                .map { it.map { it.toMenigaAuthorizationType() } }
    }

    override fun getCategories(): Result<List<AccountCategory>> {
        return MenigaSDK.executor().accountCategories
                .map { it.map { it.toMenigaAccountCategory() } }
    }

    override fun getMetadata(accountId: Long): Result<List<KeyVal<String, String?>>> {
        val req = GetAccountMetadata()
        req.id = accountId
        return MenigaSDK.executor().getAccountMetadata(req)
                .map { it.map { KeyVal(it.name, it.value) } }
    }

    override fun updateMetadata(accountId: Long, keyVal: KeyVal<String, String>): Result<KeyVal<String, String?>> {
        val request = UpdateAccountMetadata().also {
            it.name = keyVal.key
            it.value = keyVal.value
        }
        return MenigaSDK.executor().updateAccountMetadata(accountId, request)
                .map { KeyVal(it.name, it.value) }
    }

    override fun getMetadataKeyVal(accId: Long, key: String): Result<KeyVal<String?, String?>> {
        val request = GetAccountMetadataKeyVal().also {
            it.id = accId
            it.name = key
        }
        return MenigaSDK.executor().getAccountMetadataKeyVal(request)
                .map { KeyVal(it.value, it.name) }
    }

    override fun getBalanceHistory(accId: Long, from: DateTime, to: DateTime, sort: AccountBalanceHistorySort): Result<List<MenigaAccountBalanceHistory>> {
        val request = GetAccountBalanceHistory().also {
            it.id = accId
            it.dateFrom = from
            it.dateTo = to
            it.sort = sort
        }
        return MenigaSDK.executor().getAccountBalanceHistory(request)
                .map { it.map { it.toMenigaAccountBalanceHistory() } }
    }

    private fun Account.toMenigaAccount() =
            MenigaAccount(
                    id = id,
                    name = name,
                    emergencyFundBalanceLimit = emergencyFundBalanceLimit,
                    isHidden = isHidden,
                    isDisabled = isDisabled,
                    accountIdentifier = accountIdentifier,
                    realmIdentifier = realmIdentifier,
                    realmAccountTypeId = realmAccountTypeId,
                    accountTypeId = accountTypeId,
                    balance = balance,
                    originalBalance = originalBalance,
                    committedAmount = committedAmount,
                    limit = limit,
                    accountClass = accountClass,
                    organizationIdentifier = organizationIdentifier,
                    realmCredentialsId = realmCredentialsId,
                    accountAuthorizatonType = accountAuthorizationType?.toAccountAuthorizationType(),
                    isImportAccount = isImportAccount,
                    lastUpdate = lastUpdate,
                    personId = personId,
                    userEmail = userEmail,
                    createDate = createDate,
                    accountCategory = (accountCategory ?: accountType)?.toMenigaAccountCategory(),
                    inactive = inactive,
                    attachedToUserDate = attachedToUserDate,
                    metadata = metadata?.map { it.toMenigaAccountMetaData() }.orEmpty())

    private fun AccountBalanceHistory.toMenigaAccountBalanceHistory() =
            MenigaAccountBalanceHistory(
                    id ?: 0,
                    accountId ?: 0,
                    balance,
                    balanceInUserCurrency,
                    balanceDate,
                    isDefault)

    private fun AccountAuthorizationTypeName.toAccountAuthorizationType() =
            when (this) {
                AccountAuthorizationTypeName.NONE -> AccountAuthorizationType.NONE
                AccountAuthorizationTypeName.EXTERNAL -> AccountAuthorizationType.EXTERNAL
                AccountAuthorizationTypeName.INTERNAL -> AccountAuthorizationType.INTERNAL
                AccountAuthorizationTypeName.EXTERNAL_MULTIFACTOR -> AccountAuthorizationType.EXTERNAL_MULTIFACTOR
            }

    private fun AccountTypeCategory.toMenigaAccountCategory() =
            name?.let { AccountCategory.valueOf(it.toUpperCase()) } ?: AccountCategory.UNKNOWN

    private fun com.meniga.sdk.webservices.account.AccountCategory.toMenigaAccountCategory() =
            AccountCategory.valueOf(name)

    private fun AccountMetaData.toMenigaAccountMetaData() =
            MenigaAccountMetaData(name, value)

    private fun com.meniga.sdk.webservices.account.AccountAuthorizationType.toMenigaAuthorizationType() =
            MenigaAuthorizationType(id, name)

    private fun AccountType.toMenigaAccountType(): MenigaAccountType =
            MenigaAccountType(
                    id ?: 0,
                    name,
                    description,
                    accountCategory?.toMenigaAccountCategory(),
                    organizationId,
                    realmId,
                    accountCategoryDetails,
                    enableCashback ?: false)
}

