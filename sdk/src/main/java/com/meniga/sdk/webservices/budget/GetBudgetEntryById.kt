/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.webservices.requests.QueryRequestObject

internal data class GetBudgetEntryById(
        var budgetId: Long = 0,
        var entryId: Long = 0
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
