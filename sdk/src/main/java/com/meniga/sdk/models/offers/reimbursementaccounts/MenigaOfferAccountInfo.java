package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferAccountInfo implements Parcelable, Serializable {

	private String bankNumber;
	private String ledger;
	private String bankAccountNumber;
	private String socialSecurityNumber;

	protected MenigaOfferAccountInfo() {
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

	public static MenigaOfferAccountInfo newInstance(String bankNumber, String ledger, String bankAccountNumber, String socialSecurityNumber) {
		MenigaOfferAccountInfo nfo = new MenigaOfferAccountInfo();
		nfo.bankNumber = bankNumber;
		nfo.ledger = ledger;
		nfo.bankAccountNumber = bankAccountNumber;
		nfo.socialSecurityNumber = socialSecurityNumber;
		return nfo;
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

	protected MenigaOfferAccountInfo(Parcel in) {
		this.bankNumber = in.readString();
		this.ledger = in.readString();
		this.bankAccountNumber = in.readString();
		this.socialSecurityNumber = in.readString();
	}

	public static final Parcelable.Creator<MenigaOfferAccountInfo> CREATOR = new Parcelable.Creator<MenigaOfferAccountInfo>() {
		@Override
		public MenigaOfferAccountInfo createFromParcel(Parcel source) {
			return new MenigaOfferAccountInfo(source);
		}

		@Override
		public MenigaOfferAccountInfo[] newArray(int size) {
			return new MenigaOfferAccountInfo[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaOfferAccountInfo that = (MenigaOfferAccountInfo) o;

		if (bankNumber != null ? !bankNumber.equals(that.bankNumber) : that.bankNumber != null) {
			return false;
		}
		if (ledger != null ? !ledger.equals(that.ledger) : that.ledger != null) {
			return false;
		}
		if (bankAccountNumber != null ? !bankAccountNumber.equals(that.bankAccountNumber) : that.bankAccountNumber != null) {
			return false;
		}
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
}
