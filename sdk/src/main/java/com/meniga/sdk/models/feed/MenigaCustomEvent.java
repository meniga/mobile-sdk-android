package com.meniga.sdk.models.feed;


import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCustomEvent implements MenigaFeedItem, Parcelable, Serializable {

	private DateTime date;
	private long id;
	private String body;
	private String title;

	private String eventTypeIdentifier;
	private String topicName;

	protected MenigaCustomEvent() {
	}

	public long getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public MenigaFeedItem clone() throws CloneNotSupportedException {
		return (MenigaFeedItem) super.clone();
	}

	public DateTime getDate() {
		return this.date;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier;
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.date);
		dest.writeLong(this.id);
		dest.writeString(this.body);
		dest.writeString(this.title);
	}

	protected MenigaCustomEvent(Parcel in) {
		this.date = (DateTime) in.readSerializable();
		this.id = in.readLong();
		this.body = in.readString();
		this.title = in.readString();
	}

	public static final Creator<MenigaCustomEvent> CREATOR = new Creator<MenigaCustomEvent>() {
		@Override
		public MenigaCustomEvent createFromParcel(Parcel source) {
			return new MenigaCustomEvent(source);
		}

		@Override
		public MenigaCustomEvent[] newArray(int size) {
			return new MenigaCustomEvent[size];
		}
	};
}
