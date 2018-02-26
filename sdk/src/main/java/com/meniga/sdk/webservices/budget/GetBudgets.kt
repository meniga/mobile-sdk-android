/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.requests.QueryRequestObject

internal data class GetBudgets(
        var ids: List<Long>? = null,
        var accountIds: List<Long>? = null,
        var type: BudgetType? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    override fun toQueryMap(): Map<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                ids?.let { queryMap["ids"] = it.joinToString(",") }
                accountIds?.let { queryMap["accountIds"] = it.joinToString(",") }
                type?.let { queryMap["type"] = it.toString() }
            }
}
