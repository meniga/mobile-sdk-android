/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.helpers.DateTimeUtils
import com.meniga.sdk.webservices.requests.QueryRequestObject
import org.joda.time.DateTime

internal data class GetBudgetRules(
        @Transient
        val id: Long = 0,
        val categoryIds: List<Long>? = null,
        val startDate: DateTime? = null,
        val endDate: DateTime? = null,
        val allowOverlappingRules: Boolean = false
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    override fun toQueryMap(): Map<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                categoryIds?.let { queryMap["categoryIds"] = it.joinToString(",") }
                startDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["startDate"] = it }
                endDate?.let { DateTimeUtils.toString(it) }?.let { queryMap["endDate"] = it }
                allowOverlappingRules.toString().let { queryMap["allowOverlappingRules"] = it }
            }
}
