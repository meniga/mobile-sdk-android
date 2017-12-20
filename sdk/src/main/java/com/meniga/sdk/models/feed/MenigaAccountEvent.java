package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.meniga.sdk.models.userevents.enums.UserEventType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountEvent implements MenigaFeedItem, Parcelable, Serializable {
	protected long id;
	@SerializedName("topicId")
	protected long accountId;
	protected String actionText;
	protected transient MenigaAccountEventData messageData;
	protected DateTime date;
	protected String title;
	protected String body;

	protected UserEventType eventTypeIdentifier;
	protected String topicName;

	protected MenigaAccountEvent() {
	}

	public long getId() {
		return id;
	}

	public String getActionText() {
		return actionText;
	}

	public MenigaAccountEventData getMessageData() {
		return messageData;
	}

	public DateTime getDate() {
		return date;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier.toString();
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setMessageData(MenigaAccountEventData data) {
		messageData = data;
	}

	@Override
	public MenigaFeedItem clone() throws CloneNotSupportedException {
		return (MenigaFeedItem) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAccountEvent that = (MenigaAccountEvent) o;

		if (id != that.id) {
			return false;
		}
		if (accountId != that.accountId) {
			return false;
		}
		if (actionText != null ? !actionText.equals(that.actionText) : that.actionText != null) {
			return false;
		}
		if (messageData != null ? !messageData.equals(that.messageData) : that.messageData != null) {
			return false;
		}
		if (date != null ? !date.equals(that.date) : that.date != null) {
			return false;
		}
		if (title != null ? !title.equals(that.title) : that.title != null) {
			return false;
		}
		return body != null ? body.equals(that.body) : that.body == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (accountId ^ (id >>> 32));
		result = 31 * result + (actionText != null ? actionText.hashCode() : 0);
		result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.accountId);
		dest.writeString(this.actionText);
		dest.writeParcelable(this.messageData, flags);
		dest.writeSerializable(this.date);
		dest.writeString(this.title);
		dest.writeString(this.body);
	}

	protected MenigaAccountEvent(Parcel in) {
		this.id = in.readLong();
		this.accountId = in.readLong();
		this.actionText = in.readString();
		this.messageData = in.readParcelable(MenigaAccountEventData.class.getClassLoader());
		this.date = (DateTime) in.readSerializable();
		this.title = in.readString();
		this.body = in.readString();
	}

	public static final Creator<MenigaAccountEvent> CREATOR = new Creator<MenigaAccountEvent>() {
		@Override
		public MenigaAccountEvent createFromParcel(Parcel source) {
			return new MenigaAccountEvent(source);
		}

		@Override
		public MenigaAccountEvent[] newArray(int size) {
			return new MenigaAccountEvent[size];
		}
	};
}
