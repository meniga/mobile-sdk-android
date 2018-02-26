@file:JvmName("FetchBudgetEntriesFilterExtensions")

package com.meniga.sdk.models.budget

import com.meniga.sdk.webservices.budget.GetBudgetEntries
import org.joda.time.DateTime

data class FetchBudgetEntriesFilter @JvmOverloads constructor(
        var id: Long,
        var categoryIds: List<Long>? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var allowOverlappingEntries: Boolean = false,
        var includeOptionalHistoricalData: Boolean = false)

internal fun FetchBudgetEntriesFilter.toGetBudgetEntries(): GetBudgetEntries =
        GetBudgetEntries(
                id,
                categoryIds,
                startDate,
                endDate,
                allowOverlappingEntries,
                includeOptionalHistoricalData)
