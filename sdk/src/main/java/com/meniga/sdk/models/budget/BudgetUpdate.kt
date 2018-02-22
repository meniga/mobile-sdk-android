/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("BudgetUpdateExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.webservices.budget.UpdateBudget

data class BudgetUpdate(
        var name: String? = null,
        var description: String? = null,
        var accountIds: List<Long>? = null)

internal fun BudgetUpdate.toUpdateBudget(): UpdateBudget =
        UpdateBudget(
                name,
                description,
                accountIds)
