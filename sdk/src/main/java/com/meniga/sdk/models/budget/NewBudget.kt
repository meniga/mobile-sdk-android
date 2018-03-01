/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("NewBudgetExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.models.budget.enums.BudgetPeriod
import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.budget.CreateBudget

data class NewBudget @JvmOverloads constructor(
        var type: BudgetType,
        var name: String,
        var description: String? = null,
        var accountIds: List<Long> = emptyList(),
        var period: BudgetPeriod? = null,
        var periodOffset: Int? = null)

internal fun NewBudget.toCreateBudget(): CreateBudget =
        CreateBudget(
                type,
                name,
                description,
                accountIds,
                period?.toString(),
                periodOffset)
