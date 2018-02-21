package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaEvent;
import com.meniga.sdk.models.feed.MenigaTransactionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionEventConverter implements EventBaseConverter<MenigaTransactionEvent> {
	private static final String MESSAGE_DATA = "messageData";

	@Override
	public MenigaEvent eventConverter(JsonElement element) {
		Gson gson = GsonProvider.getGsonBuilder();
		JsonElement je = element.getAsJsonObject().get(MESSAGE_DATA);

		if (!je.isJsonObject()) {
			element.getAsJsonObject().remove(MESSAGE_DATA);
			element.getAsJsonObject().add(MESSAGE_DATA, convertToObject(je.getAsString()));
		}

		return gson.fromJson(element, MenigaTransactionEvent.class);
	}

	@Override
	public List<String> eventNames() {
		List<String> types = new ArrayList<>();
		types.add("transactions_threshold_deposit");
		types.add("transactions_category_budget_watch");
		types.add("transactions_threshold_expenses");
		types.add("transactions_unusual_spending");
		types.add("cashback_offer_redemption");
		return types;
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
