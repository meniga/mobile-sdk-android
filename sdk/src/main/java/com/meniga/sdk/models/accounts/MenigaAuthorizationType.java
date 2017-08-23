package com.meniga.sdk.models.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Indicates the type of account authorization during account aggregation = ['0', '1', '2', '3'].
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAuthorizationType implements Parcelable, Serializable {

	public static final Creator<MenigaAuthorizationType> CREATOR = new Creator<MenigaAuthorizationType>() {
		@Override
		public MenigaAuthorizationType createFromParcel(Parcel source) {
			return new MenigaAuthorizationType(source);
		}

		@Override
		public MenigaAuthorizationType[] newArray(int size) {
			return new MenigaAuthorizationType[size];
		}
	};

	private long id;
	private String name;

	protected MenigaAuthorizationType(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
	}

	/**
	 * @return The ID of the authorization type.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The name of the authorization type.
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAuthorizationType that = (MenigaAuthorizationType) o;

		if (id != that.id) {
			return false;
		}
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
