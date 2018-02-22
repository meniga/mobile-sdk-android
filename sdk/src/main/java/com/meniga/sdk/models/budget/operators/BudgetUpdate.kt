package com.meniga.sdk.models.budget.operators

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.budget.enums.GenerationType
import com.meniga.sdk.models.categories.MenigaCategory
import com.meniga.sdk.webservices.budget.UpdateBudgetRules
import org.joda.time.DateTime
import org.joda.time.Months

data class BudgetUpdate(
        val targetAmount: MenigaDecimal,
        val startDate: DateTime,
        val endDate: DateTime?,
        val category: MenigaCategory,
        val generationType: GenerationType,
        val generationTypeValue: Int) {

    fun toUpdateBudgetRules(): UpdateBudgetRules {
        val data: UpdateBudgetRules.UpdateBudgetData = UpdateBudgetRules.UpdateBudgetData().also {
            it.targetAmount = targetAmount
            it.startDate = startDate
            it.endDate = endDate
            it.categoryIds = mutableListOf(category.id)
            it.generationType = toGenerationTypeValue(generationType, generationTypeValue)
            if (endDate != null) {
                it.recurringPattern = UpdateBudgetRules.RecurringPattern().apply {
                    monthInterval = Months.monthsBetween(endDate, startDate).months
                }
                it.repeatUntil = endDate
            }
        }
        return UpdateBudgetRules().apply { rules = listOf(data) }
    }

    private fun toGenerationTypeValue(generationType: GenerationType, value: Int): Int =
            when (generationType) {
                GenerationType.SAME_AS_MONTH -> -Math.abs(value)
                GenerationType.AVERAGE_MONTHS -> Math.abs(value)
                else -> value
            }

    companion object {
        @JvmStatic
        fun builder(): Builder = Builder()
    }

    class Builder {
        private lateinit var targetAmount: MenigaDecimal
        fun targetAmount(value: MenigaDecimal): Builder = apply { targetAmount = value }

        private lateinit var startDate: DateTime
        fun startDate(value: DateTime): Builder = apply { startDate = value }

        private var endDate: DateTime? = null
        fun endDate(value: DateTime): Builder = apply { endDate = value }

        private lateinit var category: MenigaCategory
        fun category(value: MenigaCategory): Builder = apply { category = value }

        private lateinit var generationType: GenerationType
        fun generationType(value: GenerationType): Builder = apply { generationType = value }

        private var generationTypeValue: Int = 0
        fun generationTypeValue(value: Int): Builder = apply { generationTypeValue = value }

        fun build() = BudgetUpdate(
                targetAmount = targetAmount,
                startDate = startDate,
                endDate = endDate,
                category = category,
                generationType = generationType,
                generationTypeValue = generationTypeValue
        )
    }
}
