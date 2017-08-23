package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.meniga.sdk.annotations.MetaProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaReimbursementAccountTypePage extends ArrayList<MenigaReimbursementAccountType> implements Parcelable, Serializable {

	@Expose(deserialize = false, serialize = false)
	@MetaProperty
	private Integer totalCount;

	public MenigaReimbursementAccountTypePage() {

	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(this);
		dest.writeValue(this.totalCount);
	}

	protected MenigaReimbursementAccountTypePage(Parcel in) {
		in.readList(this, this.getClass().getClassLoader());
		this.totalCount = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<MenigaReimbursementAccountTypePage> CREATOR = new Creator<MenigaReimbursementAccountTypePage>() {
		@Override
		public MenigaReimbursementAccountTypePage createFromParcel(Parcel source) {
			return new MenigaReimbursementAccountTypePage(source);
		}

		@Override
		public MenigaReimbursementAccountTypePage[] newArray(int size) {
			return new MenigaReimbursementAccountTypePage[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		MenigaReimbursementAccountTypePage that = (MenigaReimbursementAccountTypePage) o;

		return totalCount != null ? totalCount.equals(that.totalCount) : that.totalCount == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
		return result;
	}
}
