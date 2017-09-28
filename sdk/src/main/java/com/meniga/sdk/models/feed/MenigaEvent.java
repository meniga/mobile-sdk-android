package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.userevents.enums.UserEventType;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaEvent implements MenigaFeedItem, Serializable, Cloneable, Parcelable {

	private long id;
	private long topicId;
	private String title;
	private DateTime date;
	private String body;
	private UserEventType eventTypeIdentifier;
	private Map<String, String> messageData;

	protected MenigaEvent() {
	}

	public long getId() {
		return this.id;
	}

	public long getTopicId() {
		return this.topicId;
	}

	public String getTitle() {
		return this.title;
	}

	public DateTime getDate() {
		return this.date;
	}

	@Override
	public MenigaFeedItem clone() throws CloneNotSupportedException {
		return (MenigaEvent) super.clone();
	}

	@Override
	public DateTime getOriginalDate() {
		return date;
	}

	public String getBody() {
		return this.body;
	}

	public UserEventType getEventTypeIdentifier() {
		if (this.eventTypeIdentifier == null) {
			return UserEventType.UNKNOWN;
		}
		return this.eventTypeIdentifier;
	}

	public Map<String, String> getMessageData() {
		return this.messageData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaEvent that = (MenigaEvent) o;

		if (id != that.id) return false;
		if (topicId != that.topicId) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (date != null ? !date.equals(that.date) : that.date != null) return false;
		if (body != null ? !body.equals(that.body) : that.body != null) return false;
		if (eventTypeIdentifier != that.eventTypeIdentifier) return false;
		return messageData != null ? messageData.equals(that.messageData) : that.messageData == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (topicId ^ (topicId >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		result = 31 * result + (eventTypeIdentifier != null ? eventTypeIdentifier.hashCode() : 0);
		result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return Long.toString(this.topicId) + ": " + this.eventTypeIdentifier;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.topicId);
		dest.writeString(this.title);
		dest.writeSerializable(this.date);
		dest.writeString(this.body);
		dest.writeInt(this.eventTypeIdentifier == null ? -1 : this.eventTypeIdentifier.ordinal());
		dest.writeInt(this.messageData.size());
		for (Map.Entry<String, String> entry : this.messageData.entrySet()) {
			dest.writeString(entry.getKey());
			dest.writeString(entry.getValue());
		}
	}


	protected MenigaEvent(Parcel in) {
		this.id = in.readLong();
		this.topicId = in.readLong();
		this.title = in.readString();
		this.date = (DateTime) in.readSerializable();
		this.body = in.readString();
		int tmpEventTypeIdentifier = in.readInt();
		this.eventTypeIdentifier = tmpEventTypeIdentifier == -1 ? null : UserEventType.values()[tmpEventTypeIdentifier];
		int messageDataSize = in.readInt();
		this.messageData = new HashMap<>(messageDataSize);
		for (int i = 0; i < messageDataSize; i++) {
			String key = in.readString();
			String value = in.readString();
			this.messageData.put(key, value);
		}
	}

}
