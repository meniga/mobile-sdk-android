/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.requests.QueryRequestObject

internal data class CreateBudget(
        var type: BudgetType? = null,
        var name: String? = null,
        var description: String? = null,
        var accountIds: List<Long>? = null,
        var period: String? = null,
        var offset: Int? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
