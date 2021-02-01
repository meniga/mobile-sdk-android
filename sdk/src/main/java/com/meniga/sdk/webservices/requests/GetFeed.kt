/*
 * Copyright 2017-2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.requests

import com.meniga.sdk.helpers.toSkipAndTake
import com.meniga.sdk.models.feed.FeedFilter
import org.joda.time.format.ISODateTimeFormat

data class GetFeed(
        @JvmField var feedFilter: FeedFilter
) : QueryRequestObject() {

    override fun toQueryMap(): MutableMap<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                val formatter = ISODateTimeFormat.dateTime()
                feedFilter.from.let { queryMap["dateFrom"] = formatter.print(it) }
                feedFilter.to.let { queryMap["dateTo"] = formatter.print(it) }
                feedFilter.type?.let { queryMap["type"] = it.joinToString(",") }
                feedFilter.eventTypeIdentifiers?.let { queryMap["eventTypeIdentifiers"] = it.joinToString(",") }
                val (skip, take) = toSkipAndTakeIfProvided(feedFilter)
                skip?.let { queryMap["skip"] = it.toString() }
                take?.let { queryMap["take"] = it.toString() }
            }

    override fun getValueHash() = hashCode().toLong()

    private fun toSkipAndTakeIfProvided(feedFilter: FeedFilter) =
            if (feedFilter.page != null && feedFilter.itemsPerPage != null) {
                toSkipAndTake(feedFilter.page, feedFilter.itemsPerPage)
            } else {
                Pair(null, null)
            }
}
