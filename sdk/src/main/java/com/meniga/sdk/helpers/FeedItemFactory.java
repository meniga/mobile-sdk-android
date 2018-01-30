package com.meniga.sdk.helpers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.eventconverters.generic.BaseEventConverter;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.feed.MenigaOfferEvent;
import com.meniga.sdk.models.feed.MenigaScheduledEvent;
import com.meniga.sdk.models.transactions.MenigaTransaction;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class FeedItemFactory {
	public MenigaFeedItem getMenigaFeetItem(final JsonObject element) {
		Gson gson = GsonProvider.getGsonBuilder();
		String type = element.get("typeName").getAsString();

		switch (type) {
			case "TransactionFeedItemModel":
				return gson.fromJson(element, MenigaTransaction.class);

			case "UserEventFeedItemModel":
				String eventTypeIdentifier = element.get("eventTypeIdentifier").getAsString();
				EventBaseConverter converter = resolveConverter(eventTypeIdentifier);
				return converter.eventConverter(element);

			case "ScheduledFeedItemModel":
				return gson.fromJson(element, MenigaScheduledEvent.class);

			case "OfferFeedItem":
				return gson.fromJson(element, MenigaOfferEvent.class);

			default:
				return new MenigaFeedItem() {
					@Override
					public MenigaFeedItem clone() throws CloneNotSupportedException {
						return this;
					}

					@Override
					public DateTime getDate() {
						return DateTime.now();
					}

					@Override
					public String getEventTypeIdentifier() {
						return element.get("eventTypeIdentifier").getAsString();
					}

					@Override
					public String getTopicName() {
						return element.get("topicName").getAsString();
					}
				};
		}
	}

	protected List<EventBaseConverter> getUserEventFeedConverters() {
		return MenigaSDK.getMenigaSettings().getUserEventFeedConverters();
	}

	private EventBaseConverter resolveConverter(String eventName) {
		List<EventBaseConverter> converters = getUserEventFeedConverters();
		Log.e("Meniga", "resolveConverter: " + eventName);
		for (EventBaseConverter converter : converters) {
			if (converter.eventNames().contains(eventName)) {
				return converter;
			}
		}
		return new BaseEventConverter();
	}
}
