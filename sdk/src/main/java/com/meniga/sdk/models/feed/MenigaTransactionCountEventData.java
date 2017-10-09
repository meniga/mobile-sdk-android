package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTransactionCountEventData implements Parcelable, Serializable {


	private long transactionId;
	private long merchantId;
	private String merchantName;
	private int totalCount;
	private int periodCount;
	private String categoryName;


	public long getTransactionId() {
		return transactionId;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPeriodCount() {
		return periodCount;
	}

	public String getCategoryName() {
		return categoryName;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaTransactionCountEventData that = (MenigaTransactionCountEventData) o;

		if (transactionId != that.transactionId) return false;
		if (merchantId != that.merchantId) return false;
		if (totalCount != that.totalCount) return false;
		if (periodCount != that.periodCount) return false;
		if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null)
			return false;
		return categoryName != null ? categoryName.equals(that.categoryName) : that.categoryName == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (transactionId ^ (transactionId >>> 32));
		result = 31 * result + (int) (merchantId ^ (merchantId >>> 32));
		result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
		result = 31 * result + totalCount;
		result = 31 * result + periodCount;
		result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.transactionId);
		dest.writeLong(this.merchantId);
		dest.writeString(this.merchantName);
		dest.writeInt(this.totalCount);
		dest.writeInt(this.periodCount);
		dest.writeString(this.categoryName);
	}

	public MenigaTransactionCountEventData() {
	}

	protected MenigaTransactionCountEventData(Parcel in) {
		this.transactionId = in.readLong();
		this.merchantId = in.readLong();
		this.merchantName = in.readString();
		this.totalCount = in.readInt();
		this.periodCount = in.readInt();
		this.categoryName = in.readString();
	}

	public static final Creator<MenigaTransactionCountEventData> CREATOR = new Creator<MenigaTransactionCountEventData>() {
		@Override
		public MenigaTransactionCountEventData createFromParcel(Parcel source) {
			return new MenigaTransactionCountEventData(source);
		}

		@Override
		public MenigaTransactionCountEventData[] newArray(int size) {
			return new MenigaTransactionCountEventData[size];
		}
	};
}
