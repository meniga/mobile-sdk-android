/*
 * Copyright 2017-2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed.operators

import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.feed.FeedFilter
import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.MenigaScheduledEvent
import com.meniga.sdk.webservices.requests.GetFeed
import com.meniga.sdk.webservices.requests.GetScheduledEvent

internal class MenigaFeedOperationsImp : MenigaFeedOperations {

    override fun getScheduledEvent(id: Long): Result<MenigaScheduledEvent> {
        val req = GetScheduledEvent()
        req.id = id
        req.type = "UserEvents"
        return MenigaSDK.executor().getScheduledEvent(req)
    }

    override fun getFeed(feedFilter: FeedFilter): Result<MenigaFeed> {
        return MenigaSDK.executor().getFeed(GetFeed(feedFilter))
    }
}
