package com.meniga.sdk.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.feed.MenigaOfferEvent;
import com.meniga.sdk.models.feed.MenigaScheduledEvent;
import com.meniga.sdk.models.feed.MenigaTransactionEvent;
import com.meniga.sdk.models.transactions.MenigaTransaction;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class FeedItemFactory {

	public MenigaFeedItem getMenigaFeetItem(JsonObject element) {
		Gson gson = GsonProvider.getGsonBuilder().create();
		String type = element.get("typeName").getAsString();

		switch (type) {

			case "TransactionFeedItemModel":
				return gson.fromJson(element, MenigaTransaction.class);

			case "UserEventFeedItemModel":
				String eventTypeIdentifier = element.get("eventTypeIdentifier").getAsString();
				EventBaseConverter converter = resolveConverter(eventTypeIdentifier);
				if (converter != null) {
					return converter.eventConverter(element);
				} else {
					return gson.fromJson(element, MenigaTransactionEvent.class);
				}

			case "ScheduledFeedItemModel":
				return gson.fromJson(element, MenigaScheduledEvent.class);

			case "OfferFeedItem":
				return gson.fromJson(element, MenigaOfferEvent.class);

			default:
				return gson.fromJson(element, MenigaTransactionEvent.class);
		}
	}


	protected List<EventBaseConverter> getUserEventFeedConverters() {
		return MenigaSDK.getMenigaSettings().getUserEventFeedConverters();
	}

	private EventBaseConverter resolveConverter(String eventName) {
		List<EventBaseConverter> converters = getUserEventFeedConverters();
		for (EventBaseConverter converter : converters) {
			if (converter.eventName().equals(eventName))
				return converter;
		}
		return null;
	}
}
