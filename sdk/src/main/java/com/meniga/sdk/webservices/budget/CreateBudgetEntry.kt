/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.requests.QueryRequestObject

import org.joda.time.DateTime

internal data class CreateBudgetEntries(val entries: List<CreateBudgetEntry>) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}

internal data class CreateBudgetEntry(
        var targetAmount: MenigaDecimal? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var categoryIds: List<Long>? = null
)
