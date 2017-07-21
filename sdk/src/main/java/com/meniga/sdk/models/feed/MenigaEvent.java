package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.userevents.enums.UserEventType;

import org.joda.time.DateTime;

import java.io.Serializable;

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
	private String messageData;

	protected MenigaEvent() {
	}

	protected MenigaEvent(Parcel in) {
		this.id = in.readLong();
		this.title = in.readString();
		this.date = (DateTime) in.readSerializable();
		this.body = in.readString();
		int tmpUserEventTypeIdentifier = in.readInt();
		this.eventTypeIdentifier = tmpUserEventTypeIdentifier == -1 ? null : UserEventType.values()[tmpUserEventTypeIdentifier];
		this.messageData = in.readString();
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

	public String getBody() {
		return this.body;
	}

	public UserEventType getEventTypeIdentifier() {
		if (this.eventTypeIdentifier == null) {
			return UserEventType.UNKNOWN;
		}
		return this.eventTypeIdentifier;
	}

	public String getMessageData() {
		return this.messageData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaEvent that = (MenigaEvent) o;

		if (this.id != that.id) {
			return false;
		}
		if (this.title != null ? !this.title.equals(that.title) : that.title != null) {
			return false;
		}
		if (this.date != null ? !this.date.equals(that.date) : that.date != null) {
			return false;
		}
		if (this.body != null ? !this.body.equals(that.body) : that.body != null) {
			return false;
		}
		if (this.eventTypeIdentifier != that.eventTypeIdentifier) {
			return false;
		}
		return this.messageData != null ? this.messageData.equals(that.messageData) : that.messageData == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (this.id ^ (this.id >>> 32));
		result = 31 * result + (this.title != null ? this.title.hashCode() : 0);
		result = 31 * result + (this.date != null ? this.date.hashCode() : 0);
		result = 31 * result + (this.body != null ? this.body.hashCode() : 0);
		result = 31 * result + (this.eventTypeIdentifier != null ? this.eventTypeIdentifier.hashCode() : 0);
		result = 31 * result + (this.messageData != null ? this.messageData.hashCode() : 0);
		return result;
	}

	@Override
	public MenigaEvent clone() throws CloneNotSupportedException {
		return (MenigaEvent) super.clone();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.title);
		dest.writeSerializable(this.date);
		dest.writeString(this.body);
		dest.writeInt(this.eventTypeIdentifier == null ? -1 : this.eventTypeIdentifier.ordinal());
		dest.writeString(this.messageData);
	}

	@Override
	public String toString() {
		return Long.toString(this.topicId) + ": " + this.eventTypeIdentifier;
	}
}
