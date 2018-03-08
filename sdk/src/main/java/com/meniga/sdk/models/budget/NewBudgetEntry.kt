/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("CreateBudgetEntryExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.budget.CreateBudgetEntries
import com.meniga.sdk.webservices.budget.CreateBudgetEntry
import org.joda.time.DateTime

data class NewBudgetEntry(
        var targetAmount: MenigaDecimal? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var categoryIds: List<Long>? = null)

internal fun NewBudgetEntry.toCreateBudgetEntries(): CreateBudgetEntries =
        CreateBudgetEntries(entries = listOf(CreateBudgetEntry(
                targetAmount,
                startDate,
                endDate,
                categoryIds)))
