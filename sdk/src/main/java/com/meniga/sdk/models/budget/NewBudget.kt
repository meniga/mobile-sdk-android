/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("NewBudgetExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.models.budget.enums.BudgetType
import com.meniga.sdk.webservices.budget.CreateBudget

data class NewBudget @JvmOverloads constructor(
        var name: String,
        var description: String? = null,
        var accountIds: List<Long>? = null)

internal fun NewBudget.toCreateBudget(): CreateBudget =
        CreateBudget(
                BudgetType.BUDGET,
                name,
                description,
                accountIds,
                null,
                null)
