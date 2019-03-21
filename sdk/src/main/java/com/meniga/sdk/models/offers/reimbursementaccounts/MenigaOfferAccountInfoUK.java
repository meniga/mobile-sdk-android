package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 21.3.2019.
 */
@SuppressWarnings("unused")
public class MenigaOfferAccountInfoUK implements MenigaOfferAccountInfo, Parcelable, Serializable {
	private final String bankName;
	private final String accountName;
	private final String sortcode;
	private final String bankAccountNumber;

	public MenigaOfferAccountInfoUK(String bankName, String accountName, String sortcode, String bankAccountNumber) {
		this.bankName = bankName;
		this.accountName = accountName;
		this.sortcode = sortcode;
		this.bankAccountNumber = bankAccountNumber;
	}

	private MenigaOfferAccountInfoUK(Parcel in) {
		this.bankName = in.readString();
		this.accountName = in.readString();
		this.sortcode = in.readString();
		this.bankAccountNumber = in.readString();
	}

	public String getBankName() {
		return bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getSortcode() {
		return sortcode;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MenigaOfferAccountInfoUK that = (MenigaOfferAccountInfoUK) o;

		if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null)
			return false;
		if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null)
			return false;
		if (sortcode != null ? !sortcode.equals(that.sortcode) : that.sortcode != null)
			return false;
		return bankAccountNumber != null ? bankAccountNumber.equals(that.bankAccountNumber) : that.bankAccountNumber == null;
	}

	@Override
	public int hashCode() {
		int result = bankName != null ? bankName.hashCode() : 0;
		result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
		result = 31 * result + (sortcode != null ? sortcode.hashCode() : 0);
		result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.bankName);
		dest.writeString(this.accountName);
		dest.writeString(this.sortcode);
		dest.writeString(this.bankAccountNumber);
	}

	public static final Creator<MenigaOfferAccountInfoUK> CREATOR = new Creator<MenigaOfferAccountInfoUK>() {
		@Override
		public MenigaOfferAccountInfoUK createFromParcel(Parcel source) {
			return new MenigaOfferAccountInfoUK(source);
		}

		@Override
		public MenigaOfferAccountInfoUK[] newArray(int size) {
			return new MenigaOfferAccountInfoUK[size];
		}
	};
}

