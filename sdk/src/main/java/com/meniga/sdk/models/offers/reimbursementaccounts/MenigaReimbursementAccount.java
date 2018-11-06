package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.reimbursementaccounts.operators.MenigaReimbursementAccountOperations;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaReimbursementAccount implements Serializable, Parcelable {

	protected static MenigaReimbursementAccountOperations apiOperator;

	protected long id;
	protected boolean isActive;
	protected boolean isVerified;

	protected String name;
	protected String accountType;
	protected String accountInfo;

	protected MenigaReimbursementAccount() {
	}

	protected MenigaReimbursementAccount(Parcel in) {
		id = in.readLong();
		isActive = in.readByte() != 0;
		isVerified = in.readByte() != 0;
		name = in.readString();
		accountType = in.readString();
		accountInfo = in.readString();
	}

	public static void setOperator(MenigaReimbursementAccountOperations apiOperator) {
		MenigaReimbursementAccount.apiOperator = apiOperator;
		MenigaReimbursementAccountType.setOperator(apiOperator);
	}

	public long getId() {
		return id;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public MenigaOfferAccountInfo getAccountInfo() {
		Gson gson = new Gson();
		return gson.fromJson(accountInfo, MenigaOfferAccountInfo.class);
	}

	public void setAccountInfo(MenigaOfferAccountInfo accountInfo) {
		Gson gson = new Gson();
		this.accountInfo = gson.toJson(accountInfo);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isVerified ? (byte) 1 : (byte) 0);
		dest.writeString(this.name);
		dest.writeString(this.accountType);
		dest.writeString(this.accountInfo);
	}

	public static final Creator<MenigaReimbursementAccount> CREATOR = new Creator<MenigaReimbursementAccount>() {
		@Override
		public MenigaReimbursementAccount createFromParcel(Parcel source) {
			return new MenigaReimbursementAccount(source);
		}

		@Override
		public MenigaReimbursementAccount[] newArray(int size) {
			return new MenigaReimbursementAccount[size];
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

		MenigaReimbursementAccount that = (MenigaReimbursementAccount) o;

		if (id != that.id) {
			return false;
		}
		if (isActive != that.isActive) {
			return false;
		}
		if (isVerified != that.isVerified) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) {
			return false;
		}
		return accountInfo != null ? accountInfo.equals(that.accountInfo) : that.accountInfo == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (isActive ? 1 : 0);
		result = 31 * result + (isVerified ? 1 : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
		result = 31 * result + (accountInfo != null ? accountInfo.hashCode() : 0);
		return result;
	}

	/*
	API methods below
	 */

	/**
	 * Get a reimbursement account by it's id
	 *
	 * @param id of the reimbursement account to be fetched
	 * @return A reimbursement account
	 */
	public static Result<MenigaReimbursementAccount> fetch(int id) {
		return MenigaReimbursementAccount.apiOperator.getReimbursementAccountById(id);
	}

	/**
	 * Get a reimbursement account with default query values
	 *
	 * @return List of matching reimbursement accounts
	 */
	public static Result<MenigaReimbursementAccountPage> fetch() {
		return MenigaReimbursementAccount.apiOperator.getReimbursementAccounts(false);
	}

	/**
	 * Get a reimbursement account by it's id
	 *
	 * @param includeInactive include inactive reimbursement accounts?
	 * @return List of matching reimbursement accounts
	 */
	public static Result<MenigaReimbursementAccountPage> fetch(Boolean includeInactive) {
		return MenigaReimbursementAccount.apiOperator.getReimbursementAccounts(includeInactive);
	}

	/**
	 * Add a reimbursement account. Only one active reimbursement account per user is allowed.
	 * Therefore, when a reimbursement account is added all existing active reimbursement accounts
	 * are deactivated.
	 *
	 * @param name        The name given to the account
	 * @param accountInfo The account information related to the account
	 * @return A reimbursement account
	 */
	public static Result<MenigaReimbursementAccount> create(String name, String accountType, MenigaOfferAccountInfo accountInfo) {
		return MenigaReimbursementAccount.apiOperator.createOfferAccount(name, accountType, accountInfo);
	}
}
