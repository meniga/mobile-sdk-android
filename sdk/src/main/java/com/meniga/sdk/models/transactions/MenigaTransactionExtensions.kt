/*
 * Copyright 2018 Meniga Iceland Inc.
 */
@file:JvmName("MenigaTransactionExtensions")

package com.meniga.sdk.models.transactions

import com.meniga.sdk.models.accounts.MenigaAccount
import com.meniga.sdk.models.merchants.MenigaMerchant

internal fun MenigaTransaction.setAccount(account: MenigaAccount?) {
    this.account = account
}

internal fun MenigaTransaction.setMerchant(merchant: MenigaMerchant?) {
    this.merchant = merchant
}
