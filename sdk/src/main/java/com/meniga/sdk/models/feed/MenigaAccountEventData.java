package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.meniga.sdk.helpers.MenigaDecimal;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountEventData implements Parcelable {
	@SerializedName("AccountName")
	private String accountName;
	@SerializedName("AccountCategory")
	private String accountCategory;
	@SerializedName("ThresholdAmountTrigger")
	private MenigaDecimal thresholdAmountTrigger;

	protected MenigaAccountEventData() {
	}

	public String getAccountName() {
		return accountName;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public MenigaDecimal getThresholdAmountTrigger() {
		return thresholdAmountTrigger;
	}

	@Override
	public MenigaAccountEventData clone() throws CloneNotSupportedException {
		return (MenigaAccountEventData) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAccountEventData that = (MenigaAccountEventData) o;

		if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null) {
			return false;
		}
		if (accountCategory != null ? !accountCategory.equals(that.accountCategory) : that.accountCategory != null) {
			return false;
		}
		return thresholdAmountTrigger != null ? thresholdAmountTrigger.equals(that.thresholdAmountTrigger) : that.thresholdAmountTrigger == null;
	}

	@Override
	public int hashCode() {
		int result = accountName != null ? accountName.hashCode() : 0;
		result = 31 * result + (accountCategory != null ? accountCategory.hashCode() : 0);
		result = 31 * result + (thresholdAmountTrigger != null ? thresholdAmountTrigger.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.accountName);
		dest.writeString(this.accountCategory);
		dest.writeSerializable(this.thresholdAmountTrigger);
	}

	protected MenigaAccountEventData(Parcel in) {
		this.accountName = in.readString();
		this.accountCategory = in.readString();
		this.thresholdAmountTrigger = (MenigaDecimal) in.readSerializable();
	}

	public static final Parcelable.Creator<MenigaAccountEventData> CREATOR = new Parcelable.Creator<MenigaAccountEventData>() {
		@Override
		public MenigaAccountEventData createFromParcel(Parcel source) {
			return new MenigaAccountEventData(source);
		}

		@Override
		public MenigaAccountEventData[] newArray(int size) {
			return new MenigaAccountEventData[size];
		}
	};
}
