package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
		MenigaTransactionCountEventData messageData = gson.fromJson(object.getAsJsonObject().get("messageData"), MenigaTransactionCountEventData.class);

		MenigaTransactionCountEvent event = gson.fromJson(object, MenigaTransactionCountEvent.class);
		event.setMessageData(messageData);
		return event;
	}

	@Override
	public String eventName() {
		return "transactions_merchant_count";
	}
}
