package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Represents a key-value pari that contains extra bit of information about an account.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ParsedData implements Serializable, Parcelable {
	public static final Creator<ParsedData> CREATOR = new Creator<ParsedData>() {
		@Override
		public ParsedData createFromParcel(Parcel source) {
			return new ParsedData(source);
		}

		@Override
		public ParsedData[] newArray(int size) {
			return new ParsedData[size];
		}
	};

	protected String key;
	protected String value;

	protected ParsedData() {
	}

	protected ParsedData(Parcel in) {
		this.key = in.readString();
		this.value = in.readString();
	}

	public ParsedData(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.key);
		dest.writeString(this.value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ParsedData that = (ParsedData) o;

		if (key != null ? !key.equals(that.key) : that.key != null) {
			return false;
		}
		return value != null ? value.equals(that.value) : that.value == null;
	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
