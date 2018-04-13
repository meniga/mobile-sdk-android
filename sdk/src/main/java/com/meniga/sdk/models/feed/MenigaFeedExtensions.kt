@file:JvmName("MenigaFeedExtensions")

package com.meniga.sdk.models.feed

import org.joda.time.DateTime

internal fun MenigaFeed.setActualEndDate(actualEndDate: DateTime) {
    this.actualEndDate = actualEndDate
}

internal fun MenigaFeed.setHasMoreData(hasMoreData: Boolean) {
    this.hasMoreData = hasMoreData
}

internal fun MenigaFeed.setTotalCount(totalCount: Int?) {
    this.totalCount = totalCount
}
