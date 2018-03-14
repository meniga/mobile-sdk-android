package com.meniga.sdk.eventconverters.generic;

import com.google.gson.JsonObject;
import com.meniga.sdk.models.feed.MenigaFeedItem;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 14.3.2018.
 */
public class BaseMenigaFeedItem implements MenigaFeedItem  {
    private final JsonObject element;

    BaseMenigaFeedItem(JsonObject element) {
        this.element = element;
    }

    @Override
    public MenigaFeedItem clone() throws CloneNotSupportedException {
        return new BaseMenigaFeedItem(element);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseMenigaFeedItem that = (BaseMenigaFeedItem) o;

        return element != null ? element.equals(that.element) : that.element == null;
    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }
}
