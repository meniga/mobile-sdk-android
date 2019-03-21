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
public class MenigaOfferAccountInfoIceland implements MenigaOfferAccountInfo, Parcelable, Serializable {
	private final String bankNumber;
	private final String ledger;
	private final String bankAccountNumber;
	private final String socialSecurityNumber;

	public MenigaOfferAccountInfoIceland(String bankNumber, String ledger, String bankAccountNumber, String socialSecurityNumber) {
		this.bankNumber = bankNumber;
		this.ledger = ledger;
		this.bankAccountNumber = bankAccountNumber;
		this.socialSecurityNumber = socialSecurityNumber;
	}

	private MenigaOfferAccountInfoIceland(Parcel in) {
		this.bankNumber = in.readString();
		this.ledger = in.readString();
		this.bankAccountNumber = in.readString();
		this.socialSecurityNumber = in.readString();
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public String getLedger() {
		return ledger;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
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

		MenigaOfferAccountInfoIceland that = (MenigaOfferAccountInfoIceland) o;

		if (bankNumber != null ? !bankNumber.equals(that.bankNumber) : that.bankNumber != null)
			return false;
		if (ledger != null ? !ledger.equals(that.ledger) : that.ledger != null)
			return false;
		if (bankAccountNumber != null ? !bankAccountNumber.equals(that.bankAccountNumber) : that.bankAccountNumber != null)
			return false;
		return socialSecurityNumber != null ? socialSecurityNumber.equals(that.socialSecurityNumber) : that.socialSecurityNumber == null;
	}

	@Override
	public int hashCode() {
		int result = bankNumber != null ? bankNumber.hashCode() : 0;
		result = 31 * result + (ledger != null ? ledger.hashCode() : 0);
		result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
		result = 31 * result + (socialSecurityNumber != null ? socialSecurityNumber.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.bankNumber);
		dest.writeString(this.ledger);
		dest.writeString(this.bankAccountNumber);
		dest.writeString(this.socialSecurityNumber);
	}

	public static final Creator<MenigaOfferAccountInfoIceland> CREATOR = new Creator<MenigaOfferAccountInfoIceland>() {
		@Override
		public MenigaOfferAccountInfoIceland createFromParcel(Parcel source) {
			return new MenigaOfferAccountInfoIceland(source);
		}

		@Override
		public MenigaOfferAccountInfoIceland[] newArray(int size) {
			return new MenigaOfferAccountInfoIceland[size];
		}
	};
}

