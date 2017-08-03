package com.meniga.sdk.models.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Represents further data on the type of account.
 */
public class MenigaAccountType implements Parcelable, Serializable {
	public static final Creator<MenigaAccountType> CREATOR = new Creator<MenigaAccountType>() {
		@Override
		public MenigaAccountType createFromParcel(Parcel source) {
			return new MenigaAccountType(source);
		}

		@Override
		public MenigaAccountType[] newArray(int size) {
			return new MenigaAccountType[size];
		}
	};

	private long id;
	private String name;
	private long parentId;
	private String parentName;

	protected MenigaAccountType(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.parentId = in.readLong();
		this.parentName = in.readString();
	}

	/**
	 * @return The ID of the account type.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The name of the account type.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The parent ID. Not used.
	 */
	public long getParentId() {
		return this.parentId;
	}

	/**
	 * @return The parent name. Not used.
	 */
	public String getParentName() {
		return this.parentName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeLong(this.parentId);
		dest.writeString(this.parentName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAccountType that = (MenigaAccountType) o;

		if (id != that.id) {
			return false;
		}
		if (parentId != that.parentId) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		return parentName != null ? parentName.equals(that.parentName) : that.parentName == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (int) (parentId ^ (parentId >>> 32));
		result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
		return result;
	}
}
