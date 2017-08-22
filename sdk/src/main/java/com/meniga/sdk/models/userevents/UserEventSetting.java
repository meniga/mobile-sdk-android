package com.meniga.sdk.models.userevents;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class UserEventSetting implements Parcelable {

	protected String identifier;
	protected String value;
	protected String dataType;

	public UserEventSetting() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getValue() {
		return value;
	}

	public String getDataType() {
		return dataType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserEventSetting that = (UserEventSetting) o;

		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null)
			return false;
		if (value != null ? !value.equals(that.value) : that.value != null) return false;
		return dataType != null ? dataType.equals(that.dataType) : that.dataType == null;

	}

	@Override
	public int hashCode() {
		int result = identifier != null ? identifier.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.identifier);
		dest.writeString(this.value);
		dest.writeString(this.dataType);
	}

	protected UserEventSetting(Parcel in) {
		this.identifier = in.readString();
		this.value = in.readString();
		this.dataType = in.readString();
	}

	public static final Creator<UserEventSetting> CREATOR = new Creator<UserEventSetting>() {
		@Override
		public UserEventSetting createFromParcel(Parcel source) {
			return new UserEventSetting(source);
		}

		@Override
		public UserEventSetting[] newArray(int size) {
			return new UserEventSetting[size];
		}
	};
}
