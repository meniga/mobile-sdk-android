package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaCustomEvent;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCustomEventConverter implements EventBaseConverter<MenigaCustomEvent> {
	@Override
	public MenigaCustomEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGsonBuilder();
		return gson.fromJson(element, MenigaCustomEvent.class);
	}

	@Override
	public List<String> eventNames() {
		return Collections.singletonList("segment_based_custom_content");
	}
}
