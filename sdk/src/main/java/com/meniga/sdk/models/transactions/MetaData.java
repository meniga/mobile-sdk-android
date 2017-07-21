package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MetaData implements Parcelable {

	public static final Parcelable.Creator<MetaData> CREATOR = new Parcelable.Creator<MetaData>() {
		@Override
		public MetaData createFromParcel(Parcel source) {
			return new MetaData(source);
		}

		@Override
		public MetaData[] newArray(int size) {
			return new MetaData[size];
		}
	};
	private String name;
	private String value;

	public MetaData() {
	}

	protected MetaData(Parcel in) {
		this.name = in.readString();
		this.value = in.readString();
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MetaData metaData = (MetaData) o;

		if (name != null ? !name.equals(metaData.name) : metaData.name != null) return false;
		return value != null ? value.equals(metaData.value) : metaData.value == null;

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.value);
	}
}
