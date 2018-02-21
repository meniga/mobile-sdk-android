/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.requests

import com.meniga.sdk.helpers.DateTimeUtils
import org.joda.time.DateTime

class GetBudgetParameters : QueryRequestObject() {

    var id: Long? = null
    var categoryIds: List<Long>? = null
    var startDate: DateTime? = null
    var endDate: DateTime? = null
    var allowOverlappingEntries: Boolean = false
    var includeEntries: Boolean? = null
    var includeOptionalHistoricalData: Boolean? = null

    override fun getValueHash(): Long {
        var result = if (id != null) id!!.hashCode() else 0
        result = 31 * result + if (categoryIds != null) categoryIds!!.hashCode() else 0
        result = 31 * result + if (startDate != null) startDate!!.hashCode() else 0
        result = 31 * result + if (endDate != null) endDate!!.hashCode() else 0
        result = 31 * result + if (allowOverlappingEntries) 1 else 0
        result = 31 * result + if (includeEntries != null) includeEntries!!.hashCode() else 0
        result = 31 * result + if (includeOptionalHistoricalData != null) includeOptionalHistoricalData!!.hashCode() else 0
        return result.toLong()
    }

    override fun toQueryMap(): Map<String, String> {
        return mutableMapOf<String, String>().also { queryMap ->
            id.toString().let { queryMap["id"] = it }
            categoryIds?.let { queryMap["categoryIds"] = it.joinToString(",") }
            startDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["startDate"] = it }
            endDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["endDate"] = it }
            allowOverlappingEntries.toString().let { queryMap["allowOverlappingEntries"] = it }
        }
    }
}
