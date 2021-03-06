package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaDialogEvent;
import com.meniga.sdk.models.feed.MenigaDialogEventData;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDialogEventConverter implements EventBaseConverter<MenigaDialogEvent> {
	private static final String MESSAGE_DATA = "messageData";

	@Override
	public MenigaDialogEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGson();
		JsonElement je = element.getAsJsonObject().get(MESSAGE_DATA);

		if (!je.isJsonObject()) {
			element.getAsJsonObject().remove(MESSAGE_DATA);
			element.getAsJsonObject().add(MESSAGE_DATA, convertToObject(je.getAsString()));
		}

		MenigaDialogEventData data = gson.fromJson(element.getAsJsonObject().get(MESSAGE_DATA), MenigaDialogEventData.class);

		MenigaDialogEvent event = gson.fromJson(element, MenigaDialogEvent.class);
		event.setMessageData(data);
		return event;
	}

	@Override
	public List<String> eventNames() {
		return Collections.singletonList("dialog_message");
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
