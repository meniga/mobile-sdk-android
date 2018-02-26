/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("BudgetEntryUpdateExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.budget.UpdateBudgetEntry
import org.joda.time.DateTime

data class BudgetEntryUpdate @JvmOverloads constructor(
        var targetAmount: MenigaDecimal? = null,
        var startDate: DateTime,
        var endDate: DateTime? = null,
        var categoryId: Long)

internal fun BudgetEntryUpdate.toUpdateBudgetEntry(): UpdateBudgetEntry {
    return UpdateBudgetEntry().also {
        it.targetAmount = targetAmount
        it.startDate = startDate
        it.endDate = endDate
        it.categoryIds = listOf(categoryId)
    }
}
