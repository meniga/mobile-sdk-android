package com.meniga.sdk.eventconverters.generic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.models.feed.MenigaFeedItem;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 6.12.2017.
 */
public class BaseEventConverter implements EventBaseConverter<MenigaFeedItem> {
    @Override
    public MenigaFeedItem eventConverter(JsonElement el) {
        final JsonObject element = el.getAsJsonObject();
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

    @Override
    public List<String> eventNames() {
        return Collections.singletonList("any");
    }
}
