package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.userevents.enums.UserEventType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 6.12.2017.
 */
public class MenigaChallengeEvent implements MenigaFeedItem, Parcelable, Serializable {
    private long id;
    private String actionText;
    private MenigaChallengeEventData messageData;
    private DateTime date;
    private long topicId;
    private String title;
    private String body;
    private String typeName;
    private String type;
    private UserEventType eventTypeIdentifier;
    private String topicName;

    protected MenigaChallengeEvent() {
    }

    protected MenigaChallengeEvent(Parcel in) {
        this.id = in.readLong();
        this.actionText = in.readString();
        this.messageData = in.readParcelable(MenigaChallengeEventData.class.getClassLoader());
        this.date = (DateTime) in.readSerializable();
        this.topicId = in.readLong();
        this.title = in.readString();
        this.body = in.readString();
        this.typeName = in.readString();
        this.type = in.readString();
        int tmpEventTypeIdentifier = in.readInt();
        this.eventTypeIdentifier = tmpEventTypeIdentifier == -1 ? null : UserEventType.values()[tmpEventTypeIdentifier];
        this.topicName = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getActionText() {
        return actionText;
    }

    public MenigaChallengeEventData getMessageData() {
        return messageData;
    }

    @Override
    public MenigaFeedItem clone() throws CloneNotSupportedException {
        MenigaChallengeEvent event = new MenigaChallengeEvent();
        event.id = id;
        event.actionText = actionText;
        event.messageData = messageData;
        event.date = date;
        event.topicId = topicId;
        event.title = title;
        event.body = body;
        event.typeName = typeName;
        event.type = type;
        event.eventTypeIdentifier = eventTypeIdentifier;
        event.topicName = topicName;
        return event;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public long getTopicId() {
        return topicId;
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
    public String getEventTypeIdentifier() {
        return eventTypeIdentifier.toString();
    }

    public UserEventType getEventTypeIdentifierEnum() {
        return eventTypeIdentifier;
    }

    @Override
    public String getTopicName() {
        return topicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaChallengeEvent that = (MenigaChallengeEvent) o;

        if (id != that.id) {
            return false;
        }
        if (topicId != that.topicId) {
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
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (eventTypeIdentifier != that.eventTypeIdentifier) {
            return false;
        }
        return topicName != null ? topicName.equals(that.topicName) : that.topicName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (actionText != null ? actionText.hashCode() : 0);
        result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (topicId ^ (topicId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (eventTypeIdentifier != null ? eventTypeIdentifier.hashCode() : 0);
        result = 31 * result + (topicName != null ? topicName.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.actionText);
        dest.writeParcelable(this.messageData, flags);
        dest.writeSerializable(this.date);
        dest.writeLong(this.topicId);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.typeName);
        dest.writeString(this.type);
        dest.writeInt(this.eventTypeIdentifier == null ? -1 : this.eventTypeIdentifier.ordinal());
        dest.writeString(this.topicName);
    }

    public static final Creator<MenigaChallengeEvent> CREATOR = new Creator<MenigaChallengeEvent>() {
        @Override
        public MenigaChallengeEvent createFromParcel(Parcel source) {
            return new MenigaChallengeEvent(source);
        }

        @Override
        public MenigaChallengeEvent[] newArray(int size) {
            return new MenigaChallengeEvent[size];
        }
    };

    public void setMessageData(MenigaChallengeEventData messageData) {
        this.messageData = messageData;
    }
}
