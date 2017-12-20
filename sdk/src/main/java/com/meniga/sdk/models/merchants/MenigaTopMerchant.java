package com.meniga.sdk.models.merchants;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.merchants.operators.MenigaTopMerchantOperations;
import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTopMerchant implements Parcelable, Serializable {
	protected long merchantId;
	protected String text;
	protected MenigaDecimal nettoAmount;
	protected int transactionCount;

	protected static MenigaTopMerchantOperations apiOperations;

	protected MenigaTopMerchant() {
	}

	protected MenigaTopMerchant(Parcel in) {
		this.merchantId = in.readLong();
		this.text = in.readString();
		this.nettoAmount = (MenigaDecimal) in.readSerializable();
		this.transactionCount = in.readInt();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTopMerchantOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTopMerchantOperations operator) {
		MenigaTopMerchant.apiOperations = operator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaTopMerchant that = (MenigaTopMerchant) o;

		if (merchantId != that.merchantId) {
			return false;
		}
		if (transactionCount != that.transactionCount) {
			return false;
		}
		if (text != null ? !text.equals(that.text) : that.text != null) {
			return false;
		}
		return nettoAmount != null ? nettoAmount.equals(that.nettoAmount) : that.nettoAmount == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (merchantId ^ (merchantId >>> 32));
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (nettoAmount != null ? nettoAmount.hashCode() : 0);
		result = 31 * result + transactionCount;
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.merchantId);
		dest.writeString(this.text);
		dest.writeSerializable(this.nettoAmount);
		dest.writeInt(this.transactionCount);
	}

	public static final Creator<MenigaTopMerchant> CREATOR = new Creator<MenigaTopMerchant>() {
		@Override
		public MenigaTopMerchant createFromParcel(Parcel source) {
			return new MenigaTopMerchant(source);
		}

		@Override
		public MenigaTopMerchant[] newArray(int size) {
			return new MenigaTopMerchant[size];
		}
	};

	public long getMerchantId() {
		return merchantId;
	}

	public String getText() {
		return text;
	}

	public MenigaDecimal getNettoAmount() {
		return nettoAmount;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	/*
	 * API operations below
	 */

	/**
	 * Gets a list of top merchants
	 *
	 * @param filter  Transaction filter
	 * @param options Top merchant filter
	 * @return The top merchant object
	 */
	public static Result<List<MenigaTopMerchant>> fetch(TransactionsFilter filter, TopMerchantOptions options) {
		return MenigaTopMerchant.apiOperations.getTopMerchants(filter, options);
	}
}
