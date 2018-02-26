/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.helpers.DateTimeUtils
import com.meniga.sdk.webservices.requests.QueryRequestObject
import org.joda.time.DateTime

internal data class GetBudgetEntries(
        @Transient
        var id: Long = 0,
        var categoryIds: List<Long>? = null,
        var startDate: DateTime? = null,
        var endDate: DateTime? = null,
        var allowOverlappingEntries: Boolean = false,
        var includeOptionalHistoricalData: Boolean = false
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    override fun toQueryMap(): Map<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                id.toString().let { queryMap["id"] = it }
                categoryIds?.let { queryMap["categoryIds"] = it.joinToString(",") }
                startDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["startDate"] = it }
                endDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["endDate"] = it }
                allowOverlappingEntries.toString().let { queryMap["allowOverlappingEntries"] = it }
                includeOptionalHistoricalData.toString().let { queryMap["includeOptionalHistoricalData"] = it }
            }
}
