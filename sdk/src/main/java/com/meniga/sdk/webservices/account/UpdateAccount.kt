/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.account

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class UpdateAccount(
        @Transient var id: Long = 0,
        var name: String? = null,
        var orderId: Int = 0,
        var emergencyFundBalanceLimit: Double? = null,
        var isHidden: Boolean = false,
        var isDisabled: Boolean = false
) : QueryRequestObject() {

    override fun getValueHash() = hashCode().toLong()
}
