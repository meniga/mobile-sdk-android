/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class UpdateBudget(
        var name: String? = null,
        var description: String? = null,
        var accountIds: List<Long>? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
