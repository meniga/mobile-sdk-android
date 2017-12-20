package com.meniga.sdk.models.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * A balance entry for a specific account at a certain point in time.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountBalanceHistory implements Parcelable, Serializable {
	public static final Creator<MenigaAccountBalanceHistory> CREATOR = new Creator<MenigaAccountBalanceHistory>() {
		@Override
		public MenigaAccountBalanceHistory createFromParcel(Parcel source) {
			return new MenigaAccountBalanceHistory(source);
		}

		@Override
		public MenigaAccountBalanceHistory[] newArray(int size) {
			return new MenigaAccountBalanceHistory[size];
		}
	};

	protected long id;
	protected long accountId;
	protected MenigaDecimal balance;
	protected DateTime balanceDate;
	protected boolean isDefault;

	protected MenigaAccountBalanceHistory(Parcel in) {
		this.id = in.readLong();
		this.accountId = in.readLong();
		this.balance = (MenigaDecimal) in.readSerializable();
		this.balanceDate = (DateTime) in.readSerializable();
		this.isDefault = in.readByte() != 0;
	}

	/**
	 * @return The ID of the account balance history.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The Meniga accountID of the account which this balance belongs to.
	 */
	public long getAccountId() {
		return this.accountId;
	}

	/**
	 * @return Balance at the time when it was updated.
	 */
	public MenigaDecimal getBalance() {
		return this.balance;
	}

	/**
	 * @return The time at which the balance was recorded.
	 */
	public DateTime getBalanceDate() {
		return this.balanceDate;
	}

	/**
	 * @return Indicates if the entry has been generated with default values. This happens when there is
	 * missing months (in the database) between the start and end date ranges sent in by the client.
	 */
	public boolean getIsDefault() {
		return this.isDefault;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.accountId);
		dest.writeSerializable(this.balance);
		dest.writeSerializable(this.balanceDate);
		dest.writeByte(isDefault ? (byte) 1 : (byte) 0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAccountBalanceHistory that = (MenigaAccountBalanceHistory) o;

		if (id != that.id) {
			return false;
		}
		if (accountId != that.accountId) {
			return false;
		}
		if (isDefault != that.isDefault) {
			return false;
		}
		if (balance != null ? !balance.equals(that.balance) : that.balance != null) {
			return false;
		}
		return balanceDate != null ? balanceDate.equals(that.balanceDate) : that.balanceDate == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (accountId ^ (accountId >>> 32));
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (balanceDate != null ? balanceDate.hashCode() : 0);
		result = 31 * result + (isDefault ? 1 : 0);
		return result;
	}
}
