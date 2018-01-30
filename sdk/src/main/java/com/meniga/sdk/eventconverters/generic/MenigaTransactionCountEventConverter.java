package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaTransactionCountEvent;
import com.meniga.sdk.models.feed.MenigaTransactionCountEventData;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionCountEventConverter implements EventBaseConverter<MenigaTransactionCountEvent> {
	private final String MESSAGE_DATA = "messageData";

	@Override
	public MenigaTransactionCountEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGsonBuilder();
		JsonElement je = element.getAsJsonObject().get(MESSAGE_DATA);

		if (!je.isJsonObject()) {
			element.getAsJsonObject().remove(MESSAGE_DATA);
			element.getAsJsonObject().add(MESSAGE_DATA, convertToObject(je.getAsString()));
		}

		MenigaTransactionCountEventData messageData = gson.fromJson(element.getAsJsonObject().get(MESSAGE_DATA), MenigaTransactionCountEventData.class);

		MenigaTransactionCountEvent event = gson.fromJson(element, MenigaTransactionCountEvent.class);
		event.setMessageData(messageData);
		return event;
	}

	@Override
	public List<String> eventNames() {
		return Collections.singletonList("transactions_merchant_count");
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
