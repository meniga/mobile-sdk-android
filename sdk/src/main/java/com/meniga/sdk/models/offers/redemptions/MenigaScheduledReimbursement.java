package com.meniga.sdk.models.offers.redemptions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaScheduledReimbursement implements Parcelable, Serializable {

	protected DateTime date;
	protected String account;
	protected MenigaDecimal amount;

	protected MenigaScheduledReimbursement() {
	}

	public DateTime getDate() {
		return date;
	}

	public String getAccount() {
		return account;
	}

	public MenigaDecimal getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaScheduledReimbursement that = (MenigaScheduledReimbursement) o;

		if (date != null ? !date.equals(that.date) : that.date != null) {
			return false;
		}
		if (account != null ? !account.equals(that.account) : that.account != null) {
			return false;
		}
		return amount != null ? amount.equals(that.amount) : that.amount == null;
	}

	@Override
	public int hashCode() {
		int result = date != null ? date.hashCode() : 0;
		result = 31 * result + (account != null ? account.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.date);
		dest.writeString(this.account);
		dest.writeSerializable(this.amount);
	}

	protected MenigaScheduledReimbursement(Parcel in) {
		this.date = (DateTime) in.readSerializable();
		this.account = in.readString();
		this.amount = (MenigaDecimal) in.readSerializable();
	}

	public static final Creator<MenigaScheduledReimbursement> CREATOR = new Creator<MenigaScheduledReimbursement>() {
		@Override
		public MenigaScheduledReimbursement createFromParcel(Parcel source) {
			return new MenigaScheduledReimbursement(source);
		}

		@Override
		public MenigaScheduledReimbursement[] newArray(int size) {
			return new MenigaScheduledReimbursement[size];
		}
	};
}
