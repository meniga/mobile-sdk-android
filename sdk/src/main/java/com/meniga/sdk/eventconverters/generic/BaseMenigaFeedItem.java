package com.meniga.sdk.eventconverters.generic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.models.feed.MenigaFeedItem;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 14.3.2018.
 */
public class BaseMenigaFeedItem implements MenigaFeedItem, Serializable, Parcelable {
    private final String data;
    private transient JsonObject element;

    BaseMenigaFeedItem(String data) {
        this.data = data;
    }

    private BaseMenigaFeedItem(Parcel in) {
        this.data = in.readString();
    }

    @Override
    public MenigaFeedItem clone() throws CloneNotSupportedException {
        return new BaseMenigaFeedItem(data);
    }

    @Override
    public DateTime getDate() {
        return DateTime.now();
    }

    @Override
    public String getEventTypeIdentifier() {
        return getElement().get("eventTypeIdentifier").getAsString();
    }

    @Override
    public String getTopicName() {
        return getElement().get("topicName").getAsString();
    }

    private JsonObject getElement() {
        if (element == null) {
            JsonParser parser = new JsonParser();
            element = parser.parse(data).getAsJsonObject();
        }

        return element;
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

        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }
        return element != null ? element.equals(that.element) : that.element == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (element != null ? element.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
    }

    public static final Creator<BaseMenigaFeedItem> CREATOR = new Creator<BaseMenigaFeedItem>() {
        @Override
        public BaseMenigaFeedItem createFromParcel(Parcel source) {
            return new BaseMenigaFeedItem(source);
        }

        @Override
        public BaseMenigaFeedItem[] newArray(int size) {
            return new BaseMenigaFeedItem[size];
        }
    };
}
