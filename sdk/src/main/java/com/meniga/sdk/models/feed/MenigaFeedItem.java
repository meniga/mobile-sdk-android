package com.meniga.sdk.models.feed;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaFeedItem {

	MenigaFeedItem clone() throws CloneNotSupportedException;

	DateTime getDate();

	String getEventTypeIdentifier();

	String getTopicName();
}
