/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.requests.QueryRequestObject

import org.joda.time.DateTime

internal data class CreateBudgetEntry(
        var targetAmount: MenigaDecimal? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var categoryIds: List<Long>? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
