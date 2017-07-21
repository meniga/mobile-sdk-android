package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaTransactionCountEvent;
import com.meniga.sdk.models.feed.MenigaTransactionCountEventData;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionCountEventConverter implements EventBaseConverter<MenigaTransactionCountEvent> {

	@Override
	public MenigaTransactionCountEvent eventConverter(JsonElement object) {
		Gson gson = GsonProvider.getGsonBuilder().create();
		JsonParser parser = new JsonParser();
		JsonElement el;
		String text;

		// messageData comes as string.
		el = ((JsonObject) object).get("messageData");
		text = el.toString().replace("\\", "");
		if (text.startsWith("\"")) {
			text = text.substring(1);
		}
		if (text.endsWith("\"")) {
			text = text.substring(0, text.length() - 1);
		}
		JsonObject dataObj = parser.parse(text).getAsJsonObject();
		MenigaTransactionCountEvent event = gson.fromJson(object, MenigaTransactionCountEvent.class);
		MenigaTransactionCountEventData data = gson.fromJson(dataObj, MenigaTransactionCountEventData.class);
		event.setMessageData(data);
		return event;
	}

	@Override
	public String eventName() {
		return "transactions_merchant_count";
	}
}
