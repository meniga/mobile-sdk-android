@file:JvmName("FetchBudgetsFilterExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.budget.GetBudgets

data class FetchBudgetsFilter(
        var ids: List<Long>? = null,
        var accountIds: List<Long>? = null,
        var type: BudgetType? = null)

internal fun FetchBudgetsFilter.toGetBudgets(): GetBudgets =
        GetBudgets(
                ids,
                accountIds,
                type)
