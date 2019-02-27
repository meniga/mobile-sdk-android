/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.requests.QueryRequestObject

import org.joda.time.DateTime

data class CreateBudgetRules(var rules: List<CreateBudgetRuleData>? = null) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    data class CreateBudgetRuleData(
            var targetAmount: MenigaDecimal? = null,
            var startDate: DateTime? = null,
            var endDate: DateTime? = null,
            var categoryIds: List<Long>? = null,
            var generationType: Int? = 0,
            var recurringPattern: RecurringPattern? = null,
            var repeatUntil: DateTime? = null)

    data class RecurringPattern(var monthInterval: Int = 1)
}
