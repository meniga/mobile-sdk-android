package com.meniga.sdk.models.networth;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Contains further details on the account type of the net worth entry.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaNetWorthAccountType implements Parcelable, Serializable {

	public static final Creator<MenigaNetWorthAccountType> CREATOR = new Creator<MenigaNetWorthAccountType>() {
		@Override
		public MenigaNetWorthAccountType createFromParcel(Parcel source) {
			return new MenigaNetWorthAccountType(source);
		}

		@Override
		public MenigaNetWorthAccountType[] newArray(int size) {
			return new MenigaNetWorthAccountType[size];
		}
	};

	protected long id;
	protected String name;
	protected Long parentId;
	protected String parentName;

	protected MenigaNetWorthAccountType(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		long tmpparentId = in.readLong();
		if (tmpparentId > -1) {
			this.parentId = tmpparentId;
		}
		this.parentName = in.readString();
	}

	/**
	 * @return Id of the account type category.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Name of the account type category.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return id of parent. Not used.
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @return name of the parent. Not used.
	 */
	public String getParentName() {
		return parentName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeLong(this.parentId == null ? -1 : this.parentId);
		dest.writeString(this.parentName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		MenigaNetWorthAccountType that = (MenigaNetWorthAccountType) o;

		if (id != that.id) {
			return false;
		}
		if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
			return false;
		}
		if (this.parentId != null ? !this.parentId.equals(that.parentId) : that.parentId != null) {
			return false;
		}
		return this.parentName != null ? this.parentName.equals(that.parentName) : that.parentName == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (this.id ^ (this.id >>> 32));
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		result = 31 * result + (this.parentId != null ? this.parentId.hashCode() : 0);
		result = 31 * result + (this.parentName != null ? this.parentName.hashCode() : 0);
		return result;
	}
}
