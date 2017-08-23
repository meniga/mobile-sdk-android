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

	public String key;
	public String val;

	protected ParsedData() {
	}

	protected ParsedData(Parcel in) {
		this.key = in.readString();
		this.val = in.readString();
	}

	public ParsedData(String key, String val) {
		this.key = key;
		this.val = val;
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

		if (!(this.key == null && that.key == null)) {
			if (this.key == null && that.key != null || this.key != null && that.key == null) {
				return false;
			}
			if (!this.key.equals(that.key)) {
				return false;
			}
		}
		if (!(this.val == null && that.val == null)) {
			if (this.val == null && that.val != null || this.val != null && that.val == null) {
				return false;
			}
			if (!this.val.equals(that.val)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (val != null ? val.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.key);
		dest.writeString(this.val);
	}
}
