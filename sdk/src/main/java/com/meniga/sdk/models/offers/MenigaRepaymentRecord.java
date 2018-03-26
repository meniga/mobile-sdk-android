package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.feed.MenigaTransactionEvent;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRepaymentRecord implements Parcelable, Serializable, Cloneable {
	public static final Parcelable.Creator<MenigaRepaymentRecord> CREATOR = new Parcelable.Creator<MenigaRepaymentRecord>() {
		public MenigaRepaymentRecord createFromParcel(Parcel source) {
			return new MenigaRepaymentRecord(source);
		}

		public MenigaRepaymentRecord[] newArray(int size) {
			return new MenigaRepaymentRecord[size];
		}
	};

	protected String account;
	protected MenigaDecimal amount;
	protected DateTime date;

	protected MenigaRepaymentRecord() {
	}

	protected MenigaRepaymentRecord(Parcel in) {
		this.account = in.readString();
		this.amount = (MenigaDecimal) in.readSerializable();
		this.date = (DateTime) in.readSerializable();
	}

	public String getAccount() {
		return account;
	}

	public MenigaDecimal getAmount() {
		return amount;
	}

	public DateTime getDate() {
		return date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.account);
		dest.writeSerializable(this.amount);
		dest.writeSerializable(this.date);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o == null || !(o instanceof MenigaTransactionEvent)) {
			return false;
		}

		MenigaRepaymentRecord other = (MenigaRepaymentRecord) o;

		if (this.account == null && other.account != null || this.account != null && other.account == null) {
			return false;
		}
		if (this.account != null) {
			if (!this.account.equals(other.account)) {
				return false;
			}
		}
		if (this.amount == null && other.amount != null || this.amount != null && other.amount == null) {
			return false;
		}
		if (this.amount != null) {
			if (!this.amount.equals(other.amount)) {
				return false;
			}
		}

		if (this.date == null && other.date != null || this.date != null && other.date == null) {
			return false;
		}
		if (this.date != null) {
			if (this.date.getMillis() != other.date.getMillis()) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected MenigaRepaymentRecord clone() throws CloneNotSupportedException {
		return (MenigaRepaymentRecord) super.clone();
	}
}
