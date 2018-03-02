/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("NewPlanningBudgetExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.models.budget.enums.BudgetPeriod
import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.budget.CreateBudget

data class NewPlanningBudget @JvmOverloads constructor(
        var name: String,
        var description: String? = null,
        var accountIds: List<Long> = emptyList(),
        var period: BudgetPeriod? = null,
        var periodOffset: Int? = null)

internal fun NewPlanningBudget.toCreateBudget(): CreateBudget =
        CreateBudget(
                BudgetType.PLANNING,
                name,
                description,
                accountIds,
                period?.toString(),
                periodOffset)
