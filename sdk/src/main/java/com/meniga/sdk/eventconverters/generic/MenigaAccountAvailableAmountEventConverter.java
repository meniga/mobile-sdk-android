package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaAccountEvent;
import com.meniga.sdk.models.feed.MenigaAccountEventData;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaAccountAvailableAmountEventConverter implements EventBaseConverter<MenigaAccountEvent> {

	@Override
	public MenigaAccountEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGsonBuilder().create();
		MenigaAccountEventData data = gson.fromJson(element.getAsJsonObject().get("messageData"), MenigaAccountEventData.class);
		MenigaAccountEvent event = gson.fromJson(element, MenigaAccountEvent.class);
		event.setMessageData(data);
		return event;
	}

	@Override
	public String eventName() {
		return "accounts_available_amount";
	}
}
