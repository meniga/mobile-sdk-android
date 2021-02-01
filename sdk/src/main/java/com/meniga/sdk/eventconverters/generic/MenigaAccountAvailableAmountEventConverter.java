package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaAccountEvent;
import com.meniga.sdk.models.feed.MenigaAccountEventData;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountAvailableAmountEventConverter implements EventBaseConverter<MenigaAccountEvent> {
	private static final String MESSAGE_DATA = "messageData";

	@Override
	public MenigaAccountEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGson();
		JsonElement je = element.getAsJsonObject().get(MESSAGE_DATA);

		if (!je.isJsonObject()) {
			element.getAsJsonObject().remove(MESSAGE_DATA);
			element.getAsJsonObject().add(MESSAGE_DATA, convertToObject(je.getAsString()));
		}

		MenigaAccountEventData data = gson.fromJson(element.getAsJsonObject().get(MESSAGE_DATA), MenigaAccountEventData.class);

		MenigaAccountEvent event = gson.fromJson(element, MenigaAccountEvent.class);
		event.messageData = data;
		return event;
	}

	@Override
	public List<String> eventNames() {
		return Collections.singletonList("accounts_available_amount");
	}

	private JsonElement convertToObject(String stringified) {
		stringified = stringified.replace("\\", "");
		if (stringified.startsWith("\"")) {
			stringified = stringified.substring(1);
		}
		if (stringified.endsWith("\"")) {
			stringified = stringified.substring(0, stringified.length() - 1);
		}
		JsonParser parser = new JsonParser();
		return parser.parse(stringified);
	}
}
