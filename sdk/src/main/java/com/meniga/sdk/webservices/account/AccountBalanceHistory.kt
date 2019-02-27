package com.meniga.sdk.webservices.account

import com.meniga.sdk.helpers.MenigaDecimal
import org.joda.time.DateTime

data class AccountBalanceHistory(
        val id: Long? = null,
        val accountId: Long? = null,
        val balance: MenigaDecimal? = null,
        val balanceInUserCurrency: MenigaDecimal? = null,
        val balanceDate: DateTime? = null,
        val isDefault: Boolean = false)
