package com.meniga.sdk.eventconverters;

import com.google.gson.JsonElement;
import com.meniga.sdk.models.feed.MenigaFeedItem;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface EventBaseConverter<T extends MenigaFeedItem> {
	MenigaFeedItem eventConverter(JsonElement object);

	String eventName();
}
