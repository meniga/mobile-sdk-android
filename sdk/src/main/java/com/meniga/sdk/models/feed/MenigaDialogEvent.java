package com.meniga.sdk.models.feed;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.meniga.sdk.models.userevents.enums.UserEventType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDialogEvent implements MenigaFeedItem, Parcelable, Serializable {
	protected long id;
	protected UserEventType eventTypeIdentifier;
	protected String actionText;
	protected MenigaDialogEventData messageData;
	protected DateTime date;
	protected long topicId;
	protected String topicName;
	protected String title;
	protected String body;
	protected String typeName;
	protected String type;

	protected MenigaDialogEvent() {
	}

	protected MenigaDialogEvent(Parcel in) {
		this.id = in.readLong();
		int tmpEventTypeIdentifier = in.readInt();
		this.eventTypeIdentifier = tmpEventTypeIdentifier == -1 ? null : UserEventType.values()[tmpEventTypeIdentifier];
		this.actionText = in.readString();
		this.messageData = in.readParcelable(MenigaDialogEventData.class.getClassLoader());
		this.date = (DateTime) in.readSerializable();
		this.topicId = in.readLong();
		this.topicName = in.readString();
		this.title = in.readString();
		this.body = in.readString();
		this.typeName = in.readString();
		this.type = in.readString();
	}

	public long getId() {
		return id;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier.toString();
	}

	public UserEventType getEventTypeIdentifierEnum() {
		return eventTypeIdentifier;
	}

	public String getActionText() {
		return actionText;
	}

	public MenigaDialogEventData getMessageData() {
		return messageData;
	}

	@Override
	public MenigaFeedItem clone() throws CloneNotSupportedException {
		MenigaDialogEvent item = new MenigaDialogEvent();
		item.id = id;
		item.eventTypeIdentifier = eventTypeIdentifier;
		item.actionText = actionText;
		item.messageData = messageData;
		item.date = date;
		item.topicId = topicId;
		item.topicName = topicName;
		item.title = title;
		item.body = body;
		item.typeName = typeName;
		item.type = type;
		return item;
	}

	@Override
	public DateTime getDate() {
		return date;
	}

	public long getTopicId() {
		return topicId;
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

	public String getTypeName() {
		return typeName;
	}

	public String getType() {
		return type;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeInt(this.eventTypeIdentifier == null ? -1 : this.eventTypeIdentifier.ordinal());
		dest.writeString(this.actionText);
		dest.writeParcelable(this.messageData, flags);
		dest.writeSerializable(this.date);
		dest.writeLong(this.topicId);
		dest.writeString(this.topicName);
		dest.writeString(this.title);
		dest.writeString(this.body);
		dest.writeString(this.typeName);
		dest.writeString(this.type);
	}

	public static final Creator<MenigaDialogEvent> CREATOR = new Creator<MenigaDialogEvent>() {
		@Override
		public MenigaDialogEvent createFromParcel(Parcel source) {
			return new MenigaDialogEvent(source);
		}

		@Override
		public MenigaDialogEvent[] newArray(int size) {
			return new MenigaDialogEvent[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaDialogEvent that = (MenigaDialogEvent) o;

		if (id != that.id) {
			return false;
		}
		if (topicId != that.topicId) {
			return false;
		}
		if (eventTypeIdentifier != that.eventTypeIdentifier) {
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
		if (topicName != null ? !topicName.equals(that.topicName) : that.topicName != null)
			return false;
		if (title != null ? !title.equals(that.title) : that.title != null) {
			return false;
		}
		if (body != null ? !body.equals(that.body) : that.body != null) {
			return false;
		}
		if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) {
			return false;
		}
		return type != null ? type.equals(that.type) : that.type == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (eventTypeIdentifier != null ? eventTypeIdentifier.hashCode() : 0);
		result = 31 * result + (actionText != null ? actionText.hashCode() : 0);
		result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (int) (topicId ^ (topicId >>> 32));
		result = 31 * result + (topicName != null ? topicName.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	public void setMessageData(MenigaDialogEventData messageData) {
		this.messageData = messageData;
	}
}
