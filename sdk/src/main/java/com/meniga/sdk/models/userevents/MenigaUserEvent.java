package com.meniga.sdk.models.userevents;

import android.os.Parcel;
import android.os.Parcelable;


import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.userevents.enums.UserEventType;
import com.meniga.sdk.models.userevents.operators.MenigaUserEventsOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUserEvent implements Serializable, Parcelable, Cloneable {
	protected static MenigaUserEventsOperations apiOperations;

	protected UserEventType userEventTypeIdentifier;
	protected List<UserEventSubscription> subscriptions;
	protected List<UserEventSetting> settings;
	protected List<MenigaUserEvent> children;

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaUserEventOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaUserEventsOperations operator) {
		MenigaUserEvent.apiOperations = operator;
	}

	public MenigaUserEvent() {
	}

	public UserEventType getUserEventTypeIdentifier() {
		return userEventTypeIdentifier;
	}

	public List<UserEventSubscription> getSubscriptions() {
		return subscriptions;
	}

	public List<UserEventSetting> getSettings() {
		return settings;
	}

	public List<MenigaUserEvent> getChildren() {
		return children;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaUserEvent that = (MenigaUserEvent) o;

		if (userEventTypeIdentifier != null ? !userEventTypeIdentifier.equals(that.userEventTypeIdentifier) : that.userEventTypeIdentifier != null)
			return false;
		if (subscriptions != null ? !subscriptions.equals(that.subscriptions) : that.subscriptions != null)
			return false;
		if (settings != null ? !settings.equals(that.settings) : that.settings != null)
			return false;
		return children != null ? children.equals(that.children) : that.children == null;

	}

	@Override
	public int hashCode() {
		int result = userEventTypeIdentifier != null ? userEventTypeIdentifier.hashCode() : 0;
		result = 31 * result + (subscriptions != null ? subscriptions.hashCode() : 0);
		result = 31 * result + (settings != null ? settings.hashCode() : 0);
		result = 31 * result + (children != null ? children.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.userEventTypeIdentifier.ordinal());
		dest.writeTypedList(this.subscriptions);
		dest.writeTypedList(this.settings);
		dest.writeTypedList(this.children);
	}


	protected MenigaUserEvent(Parcel in) {
		this.userEventTypeIdentifier = UserEventType.values()[in.readInt()];
		this.subscriptions = in.createTypedArrayList(UserEventSubscription.CREATOR);
		this.settings = in.createTypedArrayList(UserEventSetting.CREATOR);
		this.children = in.createTypedArrayList(MenigaUserEvent.CREATOR);
	}

	public static final Creator<MenigaUserEvent> CREATOR = new Creator<MenigaUserEvent>() {
		@Override
		public MenigaUserEvent createFromParcel(Parcel source) {
			return new MenigaUserEvent(source);
		}

		@Override
		public MenigaUserEvent[] newArray(int size) {
			return new MenigaUserEvent[size];
		}
	};

	/**
	 * Gets a user events subscriptions by id
	 *
	 * @return List og MenigaUserEvent subscriptions
	 */
	public static Result<List<MenigaUserEvent>> fetch() {
		return MenigaUserEvent.apiOperations.getUserEvents();
	}

	/**
	 * Set user event subscription
	 *
	 * @return Void
	 */
	public static Result<Void> subscribe(List<MenigaUserEvent> userEvents, boolean subscribed, String channel, String reason) {
		List<UserEventType> userEventTypeIdentifier = new ArrayList<>();
		for (int i = 0; i < userEvents.size(); i++) {
			for (int j = 0; j < userEvents.get(i).getSubscriptions().size(); j++) {
				UserEventSubscription sub = userEvents.get(i).getSubscriptions().get(j);
				if (sub.getChannelName().equals(channel))
					userEventTypeIdentifier.add(userEvents.get(i).getUserEventTypeIdentifier());
			}

		}
		return MenigaUserEvent.apiOperations.setSubscription(userEventTypeIdentifier, subscribed, channel, reason);
	}

	public static Result<Void> updateSettings(Map<String, String> subscriptionSettings) {
		return MenigaUserEvent.apiOperations.updateSettings(subscriptionSettings);
	}
}
