package com.meniga.sdk.models.userevents;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class UserEventSubscription implements Parcelable, Serializable {
	protected String channelName;
	protected Boolean isSubscribed;
	protected Boolean canUpdateSubscription;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.channelName);
		dest.writeValue(this.isSubscribed);
		dest.writeValue(this.canUpdateSubscription);
	}

	protected UserEventSubscription() {
	}

	protected UserEventSubscription(Parcel in) {
		this.channelName = in.readString();
		this.isSubscribed = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.canUpdateSubscription = (Boolean) in.readValue(Boolean.class.getClassLoader());
	}

	public static final Creator<UserEventSubscription> CREATOR = new Creator<UserEventSubscription>() {
		@Override
		public UserEventSubscription createFromParcel(Parcel source) {
			return new UserEventSubscription(source);
		}

		@Override
		public UserEventSubscription[] newArray(int size) {
			return new UserEventSubscription[size];
		}
	};


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserEventSubscription that = (UserEventSubscription) o;

		if (channelName != null ? !channelName.equals(that.channelName) : that.channelName != null)
			return false;
		if (isSubscribed != null ? !isSubscribed.equals(that.isSubscribed) : that.isSubscribed != null)
			return false;
		return canUpdateSubscription != null ? canUpdateSubscription.equals(that.canUpdateSubscription) : that.canUpdateSubscription == null;

	}

	@Override
	public int hashCode() {
		int result = channelName != null ? channelName.hashCode() : 0;
		result = 31 * result + (isSubscribed != null ? isSubscribed.hashCode() : 0);
		result = 31 * result + (canUpdateSubscription != null ? canUpdateSubscription.hashCode() : 0);
		return result;
	}

	public String getChannelName() {
		return channelName;
	}

	public Boolean getSubscribed() {
		return isSubscribed;
	}

	public Boolean getCanUpdateSubscription() {
		return canUpdateSubscription;
	}
}
