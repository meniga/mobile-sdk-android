/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed.operators

import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.helpers.toSkipAndTake
import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.MenigaScheduledEvent
import com.meniga.sdk.webservices.requests.GetFeed
import com.meniga.sdk.webservices.requests.GetScheduledEvent
import org.joda.time.DateTime

internal class MenigaFeedOperationsImp : MenigaFeedOperations {

    override fun getScheduledEvent(id: Long): Result<MenigaScheduledEvent> {
        val req = GetScheduledEvent()
        req.id = id
        req.type = "UserEvents"
        return MenigaSDK.executor().getScheduledEvent(req)
    }

    override fun getFeed(from: DateTime, to: DateTime): Result<MenigaFeed> {
        val req = GetFeed()
        req.dateFrom = from
        req.dateTo = to
        return MenigaSDK.executor().getFeed(req)
    }

    override fun getFeed(from: DateTime, to: DateTime, page: Int, itemsPerPage: Int): Result<MenigaFeed> {
        val req = GetFeed()
        req.dateFrom = from
        req.dateTo = to
        toSkipAndTake(page, itemsPerPage).let {
            req.skip = it.first
            req.take = it.second
        }
        return MenigaSDK.executor().getFeed(req)
    }
}
