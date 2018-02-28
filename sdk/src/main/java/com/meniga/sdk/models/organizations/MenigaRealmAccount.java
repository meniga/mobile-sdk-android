package com.meniga.sdk.models.organizations;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.accounts.enums.AccountCategory;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRealmAccount extends QueryRequestObject implements Parcelable, Serializable {
	protected String name;
	protected String accountIdentifier;
	protected long accountTypeId;
	protected boolean accountExists;

	protected MenigaRealmAccount() {
	}

	protected MenigaRealmAccount(Parcel in) {
		this.name = in.readString();
		this.accountIdentifier = in.readString();
		this.accountTypeId = in.readLong();
		this.accountExists = in.readByte() != 0;
	}

	public String getName() {
		return name;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	/**
	 * This method is not reliable. Use
	 */
	@Deprecated
	public AccountCategory getAccountTypeId() {
		return AccountCategory.fromId(accountTypeId);
	}

	public boolean isAccountAlreadyConnected() {
		return accountExists;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaRealmAccount that = (MenigaRealmAccount) o;

		if (accountTypeId != that.accountTypeId) {
			return false;
		}
		if (accountExists != that.accountExists) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		return accountIdentifier != null ? accountIdentifier.equals(that.accountIdentifier) : that.accountIdentifier == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (accountIdentifier != null ? accountIdentifier.hashCode() : 0);
		result = 31 * result + (int) (accountTypeId ^ (accountTypeId >>> 32));
		result = 31 * result + (accountExists ? 1 : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.accountIdentifier);
		dest.writeLong(this.accountTypeId);
		dest.writeByte(this.accountExists ? (byte) 1 : (byte) 0);
	}

	public static final Parcelable.Creator<MenigaRealmAccount> CREATOR = new Parcelable.Creator<MenigaRealmAccount>() {
		@Override
		public MenigaRealmAccount createFromParcel(Parcel source) {
			return new MenigaRealmAccount(source);
		}

		@Override
		public MenigaRealmAccount[] newArray(int size) {
			return new MenigaRealmAccount[size];
		}
	};

	// Horrible hack to be able to service the aggregation endpoint
	@Override
	public long getValueHash() {
		return name.hashCode() + accountIdentifier.hashCode() + accountTypeId + (accountExists ? 1 : 0);
	}
}
