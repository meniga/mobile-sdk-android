package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBrandSpending implements Parcelable, Serializable {

	public static final Creator<MenigaBrandSpending> CREATOR = new Creator<MenigaBrandSpending>() {
		@Override
		public MenigaBrandSpending createFromParcel(Parcel in) {
			return new MenigaBrandSpending(in);
		}

		@Override
		public MenigaBrandSpending[] newArray(int size) {
			return new MenigaBrandSpending[size];
		}
	};

	protected String brandName;
	protected int brandId;
	protected MenigaDecimal spentAmount;

	protected MenigaBrandSpending() {

	}

	protected MenigaBrandSpending(Parcel in) {
		brandName = in.readString();
		brandId = in.readInt();
		spentAmount = (MenigaDecimal) in.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(brandName);
		dest.writeInt(brandId);
		dest.writeSerializable(spentAmount);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
