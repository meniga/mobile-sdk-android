package com.meniga.sdk.models.feed

import com.meniga.sdk.models.feed.enums.FeedItemType
import org.joda.time.DateTime

class FeedFilter private constructor(
        val from: DateTime,
        val to: DateTime,
        val type: List<FeedItemType>?,
        val eventTypeIdentifiers: List<String>?,
        val page: Int?,
        val itemsPerPage: Int?
) {
    data class Builder(
            var from: DateTime? = null,
            var to: DateTime? = null,
            var type: List<FeedItemType>? = null,
            var eventTypeIdentifiers: List<String>? = null,
            var page: Int? = null,
            var itemsPerPage: Int? = null
    ) {
        fun from(from: DateTime) = apply { this.from = from }
        fun to(to: DateTime) = apply { this.to = to }
        fun type(type: List<FeedItemType>?) = apply { this.type = type }
        fun eventTypeIdentifiers(eventTypeIdentifiers: List<String>?) = apply { this.eventTypeIdentifiers = eventTypeIdentifiers }
        fun page(page: Int?) = apply { this.page = page }
        fun itemsPerPage(itemsPerPage: Int?) = apply { this.itemsPerPage = itemsPerPage }
        fun build() = FeedFilter(from!!, to!!, type, eventTypeIdentifiers, page, itemsPerPage)
    }
}
