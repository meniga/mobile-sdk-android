/*
 * Copyright 2017 Meniga Iceland Inc.
 */
@file:JvmName("FetchBudgetFilterExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.webservices.budget.GetBudget
import org.joda.time.DateTime

data class FetchBudgetFilter @JvmOverloads constructor(
        var id: Long,
        var categoryIds: List<Long>? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var allowOverlappingEntries: Boolean? = false,
        var includeEntries: Boolean? = true,
        var includeOptionalHistoricalData: Boolean? = false)

internal fun FetchBudgetFilter.toGetBudget(): GetBudget =
        GetBudget(
                id,
                categoryIds,
                startDate,
                endDate,
                allowOverlappingEntries,
                includeEntries,
                includeOptionalHistoricalData)
