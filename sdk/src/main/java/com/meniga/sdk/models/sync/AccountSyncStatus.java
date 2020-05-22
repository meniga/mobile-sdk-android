package com.meniga.sdk.models.sync;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Contains further details about the sync status of specific accounts.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AccountSyncStatus implements Serializable, Parcelable {
	protected long accountId;
	protected Integer transactionsProcessed;
	protected MenigaDecimal balance;
	protected MenigaDecimal limit;
	protected Integer totalTransactions;
	protected DateTime startDate;
	protected DateTime endDate;
	protected String accountStatus;
	protected AccountSyncResult status;

	protected AccountSyncStatus() {
	}

	protected AccountSyncStatus(Parcel in) {
		this.accountId = in.readLong();
		this.transactionsProcessed = (Integer) in.readValue(Integer.class.getClassLoader());
		this.balance = (MenigaDecimal) in.readSerializable();
		this.limit = (MenigaDecimal) in.readSerializable();
		this.totalTransactions = (Integer) in.readValue(Integer.class.getClassLoader());
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.accountStatus = in.readString();
		int tmpStatus = in.readInt();
		this.status = tmpStatus == -1 ? null : AccountSyncResult.values()[tmpStatus];
	}

	/**
	 * @return Total number of transactions that has already been processed during this synchronization.
	 */
	public Integer getTransactionsProcessed() {
		return this.transactionsProcessed;
	}

	/**
	 * @return Balance of the account.
	 */
	public MenigaDecimal getBalance() {
		return balance;
	}

	/**
	 * @return Limit or overdraft of the account.
	 */
	public MenigaDecimal getLimit() {
		return limit;
	}

	/**
	 * @return Total number of transactions to process during this synchronization session, or null if the number of transactions is still unknown.
	 */
	public Integer getTotalTransactions() {
		return totalTransactions;
	}

	/**
	 * @return The timestamp when synchronization of this account started, or null if the synchronization of this account has not yet started ,
	 */
	public DateTime getStartDate() {
		return startDate;
	}

	/**
	 * @return The timestamp when synchronization of this account completed, or null if the synchronization is not yet completed. ,
	 */
	public DateTime getEndDate() {
		return endDate;
	}

	/**
	 * @return External status of account this can be different between each implementation of the backend.
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	public long getAccountId() {
		return accountId;
	}

	/**
	 * @return The status of the account synchronization.
	 */
	public AccountSyncResult getStatus() {
		return status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AccountSyncStatus that = (AccountSyncStatus) o;

		if (accountId != that.accountId) return false;
		if (transactionsProcessed != null ? !transactionsProcessed.equals(that.transactionsProcessed) : that.transactionsProcessed != null) return false;
		if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
		if (limit != null ? !limit.equals(that.limit) : that.limit != null) return false;
		if (totalTransactions != null ? !totalTransactions.equals(that.totalTransactions) : that.totalTransactions != null) return false;
		if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
		if (accountStatus != null ? !accountStatus.equals(that.accountStatus) : that.accountStatus != null) return false;
		return status == that.status;
	}

	@Override
	public int hashCode() {
		int result = (int) (accountId ^ (accountId >>> 32));
		result = 31 * result + (transactionsProcessed != null ? transactionsProcessed.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (limit != null ? limit.hashCode() : 0);
		result = 31 * result + (totalTransactions != null ? totalTransactions.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.accountId);
		dest.writeValue(this.transactionsProcessed);
		dest.writeSerializable(this.balance);
		dest.writeSerializable(this.limit);
		dest.writeValue(this.totalTransactions);
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
		dest.writeString(this.accountStatus);
		dest.writeInt(this.status == null ? -1 : this.status.ordinal());
	}

	public static final Creator<AccountSyncStatus> CREATOR = new Creator<AccountSyncStatus>() {
		@Override
		public AccountSyncStatus createFromParcel(Parcel source) {
			return new AccountSyncStatus(source);
		}

		@Override
		public AccountSyncStatus[] newArray(int size) {
			return new AccountSyncStatus[size];
		}
	};
}
