package com.meniga.sdk.models.feed.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.feed.MenigaScheduledEvent;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaFeedOperations {

	Result<MenigaFeed> getFeed(DateTime from, DateTime to);

	Result<MenigaFeed> getFeed(DateTime from, DateTime to, int skip, int take);

	Result<MenigaFeedItem> getEvent(long id);

    Result<MenigaScheduledEvent> getScheduledEvent(long id);
}
