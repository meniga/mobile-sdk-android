/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("BudgetRulesUpdateExtensions")
package com.meniga.sdk.models.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.budget.enums.GenerationType
import com.meniga.sdk.models.budget.enums.GenerationTypeValue
import com.meniga.sdk.webservices.budget.UpdateBudgetRules
import org.joda.time.DateTime
import org.joda.time.Months

data class BudgetRulesUpdate @JvmOverloads constructor(
        var targetAmount: MenigaDecimal? = null,
        var startDate: DateTime,
        var endDate: DateTime? = null,
        var categoryId: Long,
        var generationTypeValue: GenerationTypeValue? = null)

internal fun BudgetRulesUpdate.toUpdateBudgetRules(): UpdateBudgetRules {
    val data: UpdateBudgetRules.UpdateBudgetData = UpdateBudgetRules.UpdateBudgetData().also {
        it.targetAmount = targetAmount
        it.startDate = startDate
        it.endDate = endDate
        it.categoryIds = mutableListOf(categoryId)
        generationTypeValue?.let { generationValue -> it.generationType = toGenerationType(generationValue) }
        if (endDate != null) {
            it.recurringPattern = UpdateBudgetRules.RecurringPattern().apply {
                monthInterval = Months.monthsBetween(endDate, startDate).months
            }
            it.repeatUntil = endDate
        }
    }
    return UpdateBudgetRules().apply { rules = listOf(data) }
}

private fun toGenerationType(generationTypeValue: GenerationTypeValue): Int =
        when (generationTypeValue.type) {
            GenerationType.SAME_AS_MONTH -> -Math.abs(generationTypeValue.value)
            GenerationType.AVERAGE_MONTHS -> Math.abs(generationTypeValue.value)
            else -> generationTypeValue.value
        }
