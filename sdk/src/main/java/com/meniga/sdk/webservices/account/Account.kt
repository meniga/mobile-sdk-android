package com.meniga.sdk.webservices.account

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType
import com.meniga.sdk.models.transactions.MetaData
import org.joda.time.DateTime

internal data class Account(
        val id: Long = 0,
        val accountIdentifier: String? = null,
        val realmIdentifier: String? = null,
        val realmAccountTypeId: Int = 0,
        val accountTypeId: Int = 0,
        val balance: MenigaDecimal? = null,
        val originalBalance: MenigaDecimal? = null,
        val committedAmount: MenigaDecimal? = null,
        val limit: MenigaDecimal? = null,
        val accountClass: String? = null,
        val organizationIdentifier: String? = null,
        val realmCredentialsId: Long? = null,
        val accountAuthorizatonType: AccountAuthorizationType? = null,
        val isImportAccount: Boolean = false,
        val lastUpdate: DateTime? = null,
        val personId: Long? = null,
        val userEmail: String? = null,
        val createDate: DateTime? = null,
        val accountCategory: AccountCategory? = null,
        val accountType: AccountCategory? = null,
        val isInactive: Boolean = false,
        val attachedToUserDate: DateTime? = null,
        val metadata: List<MetaData>? = null)
