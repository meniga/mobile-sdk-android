/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.account

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class UpdateAccount(
        var id: Long = 0,
        var name: String? = null,
        var isHidden: Boolean = false,
        var isDisabled: Boolean = false,
        var emergencyFundBalanceLimit: Double? = null
) : QueryRequestObject() {

    override fun getValueHash() = hashCode().toLong()
}
