/*
 * Copyright 2017-2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed.operators

import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.feed.FeedFilter
import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.MenigaScheduledEvent

interface MenigaFeedOperations {
    fun getFeed(feedFilter: FeedFilter): Result<MenigaFeed>
    fun getScheduledEvent(id: Long): Result<MenigaScheduledEvent>
}
