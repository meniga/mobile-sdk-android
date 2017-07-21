package com.meniga.sdk.models.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaBalance implements Parcelable {

	MenigaDecimal amount;

	public MenigaBalance(MenigaDecimal amount) {
		this.amount = amount;
	}

	public MenigaDecimal getAmount() {
		return amount;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.amount);
	}

	protected MenigaBalance(Parcel in) {
		this.amount = (MenigaDecimal) in.readSerializable();
	}

	public static final Creator<MenigaBalance> CREATOR = new Creator<MenigaBalance>() {
		@Override
		public MenigaBalance createFromParcel(Parcel source) {
			return new MenigaBalance(source);
		}

		@Override
		public MenigaBalance[] newArray(int size) {
			return new MenigaBalance[size];
		}
	};


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaBalance that = (MenigaBalance) o;

		return amount != null ? amount.equals(that.amount) : that.amount == null;

	}

	@Override
	public int hashCode() {
		return amount != null ? amount.hashCode() : 0;
	}
}
