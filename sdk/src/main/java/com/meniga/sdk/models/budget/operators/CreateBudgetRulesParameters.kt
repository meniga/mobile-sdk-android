package com.meniga.sdk.models.budget.operators

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.budget.enums.GenerationType
import com.meniga.sdk.models.categories.MenigaCategory
import com.meniga.sdk.webservices.requests.QueryRequestObject
import com.meniga.sdk.webservices.requests.UpdateBudgetRules
import org.joda.time.DateTime
import org.joda.time.Months

data class CreateBudgetRulesParameters(
        val targetAmount: MenigaDecimal,
        val startDate: DateTime,
        val endDate: DateTime?,
        val category: MenigaCategory,
        val generationType: GenerationType,
        val generationTypeValue: Int): QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    fun toUpdateBudgetRules(): UpdateBudgetRules {
        val data: UpdateBudgetRules.UpdateBudgetData = UpdateBudgetRules.UpdateBudgetData().also {
            it.targetAmount = targetAmount
            it.startDate = startDate
            it.endDate = endDate
            it.categoryIds = mutableListOf(category.id)

            if (endDate != null) {
                it.recurringPattern = UpdateBudgetRules.RecurringPattern().apply {
                    monthInterval = Months.monthsBetween(endDate, startDate).months
                }
                it.repeatUntil = endDate
            }
        }
        return UpdateBudgetRules().apply { rules = listOf(data) }
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

        fun build() = CreateBudgetRulesParameters(
                targetAmount = targetAmount,
                startDate = startDate,
                endDate = endDate,
                category = category,
                generationType = generationType,
                generationTypeValue = generationTypeValue
        )
    }
}
