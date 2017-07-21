package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
		JsonParser parser = new JsonParser();
		JsonElement el;
		String text;

		// messageData comes as string.
		el = ((JsonObject) element).get("messageData");
		text = el.toString().replace("\\", "");
		if (text.startsWith("\"")) {
			text = text.substring(1);
		}
		if (text.endsWith("\"")) {
			text = text.substring(0, text.length() - 1);
		}
		JsonObject dataObj = parser.parse(text).getAsJsonObject();
		MenigaAccountEvent event = gson.fromJson(element, MenigaAccountEvent.class);
		MenigaAccountEventData data = gson.fromJson(dataObj, MenigaAccountEventData.class);
		event.setMessageData(data);
		return event;
	}

	@Override
	public String eventName() {
		return "accounts_available_amount";
	}
}
