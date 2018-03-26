package com.meniga.sdk.models.networth;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.networth.enums.NetWorthType;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a net worth account in the Meniga system. A net worth account are things like mortgages,
 * savings accounts, stock accounts etc.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaNetWorth implements Serializable, Parcelable, Cloneable {
	public static final Parcelable.Creator<MenigaNetWorth> CREATOR = new Parcelable.Creator<MenigaNetWorth>() {
		@Override
		public MenigaNetWorth createFromParcel(Parcel source) {
			return new MenigaNetWorth(source);
		}

		@Override
		public MenigaNetWorth[] newArray(int size) {
			return new MenigaNetWorth[size];
		}
	};

	protected static MenigaNetWorthOperations apiOperator;

	protected long accountId;
	protected Long realmAccountTypeId;
	protected String accountName;
	protected Boolean isImport;
	protected Boolean isManual;
	protected Boolean isExcluded;
	protected NetWorthType netWorthType;
	protected MenigaDecimal currentBalance;
	protected List<MenigaNetWorthBalance> history = new ArrayList<>();
	protected MenigaNetWorthAccountType accountType;

	protected MenigaNetWorth() {
	}

	protected MenigaNetWorth(Parcel in) {
		this.accountId = in.readLong();
		long tmprealmAccountTypeId = in.readLong();
		if (tmprealmAccountTypeId > -1) {
			this.realmAccountTypeId = tmprealmAccountTypeId;
		}
		this.accountName = in.readString();
		int tmpisImport = in.readInt();
		if (tmpisImport > -1) {
			this.isImport = tmpisImport == 1;
		}
		int tmpisManual = in.readInt();
		if (tmpisManual > -1) {
			this.isManual = tmpisManual == 1;
		}
		int tmpisExcluded = in.readInt();
		if (tmpisExcluded > -1) {
			this.isExcluded = tmpisExcluded == 1;
		}
		int tmpNetWorthType = in.readInt();
		this.netWorthType = tmpNetWorthType == -1 ? null : NetWorthType.values()[tmpNetWorthType];
		boolean currBalaIsNull = in.readByte() == (byte) 1;
		if (!currBalaIsNull) {
			this.currentBalance = new MenigaDecimal(in.readDouble());
		}
		this.accountType = in.readParcelable(MenigaNetWorthAccountType.class.getClassLoader());

		this.history = new ArrayList<>();
		Parcelable[] arr = in.readParcelableArray(MenigaNetWorthBalance.class.getClassLoader());
		for (int i = 0; i < arr.length; i++) {
			this.history.add((MenigaNetWorthBalance) arr[i]);
		}
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaNetWorthOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaNetWorthOperations operator) {
		MenigaNetWorth.apiOperator = operator;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.accountId);
		dest.writeLong(this.realmAccountTypeId == null ? -1 : this.realmAccountTypeId);
		dest.writeString(this.accountName);
		dest.writeInt(this.isImport == null ? -1 : this.isImport ? 1 : 0);
		dest.writeInt(this.isManual == null ? -1 : this.isManual ? 1 : 0);
		dest.writeInt(this.isExcluded == null ? -1 : this.isExcluded ? 1 : 0);
		dest.writeInt(this.netWorthType == null ? -1 : this.netWorthType.ordinal());
		dest.writeByte(this.currentBalance == null ? (byte) 1 : (byte) 0);
		if (this.currentBalance != null) {
			dest.writeDouble(this.currentBalance.doubleValue());
		}
		dest.writeParcelable(this.accountType, flags);

		Parcelable[] arr = new Parcelable[this.history.size()];
		for (int i = 0; i < this.history.size(); i++) {
			arr[i] = this.history.get(i);
		}
		dest.writeParcelableArray(arr, flags);
	}

	@Override
	public MenigaNetWorth clone() throws CloneNotSupportedException {
		return (MenigaNetWorth) super.clone();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * @return The accountId for the NetWorth object.
	 */
	public long getId() {
		return this.accountId;
	}

	/**
	 * @return Name of the NetWorth account object.
	 */
	public String getName() {
		return this.accountName;
	}

	/**
	 * @param name Set the name for the NetWorth account.
	 */
	public void setName(String name) {
		this.accountName = name;
	}

	/**
	 * @return The ID for realm account type.
	 */
	public Long getRealmAccountTypeId() {
		return realmAccountTypeId;
	}

	/**
	 * @return True if the NetWorth is an import.
	 */
	public Boolean getIsImport() {
		return isImport;
	}

	/**
	 * @return True if the net worth is manually created.
	 */
	public Boolean getIsManual() {
		if (this.isManual == null) {
			return false;
		}
		return this.isManual;
	}

	/**
	 * @return True if the NetWorth is excluded.
	 */
	public boolean getIsExcluded() {
		if (this.isExcluded == null) {
			return false;
		}
		return this.isExcluded;
	}

	/**
	 * @param isExcluded Set if the NetWorth should be excluded or not.
	 */
	public void setIsExcluded(Boolean isExcluded) {
		this.isExcluded = isExcluded;
	}

	/**
	 * @return The type of the NetWorth. This can be either asset or liability.
	 */
	public NetWorthType getNetWorthType() {
		return netWorthType;
	}

	/**
	 * @return The current balance.
	 */
	public MenigaDecimal getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance Sets the current balance of this NetWorth. Note that you can not update the balance on the server
	 *                       this way, only updating the most recent net worth balance will do that.
	 */
	public void setCurrentBalance(MenigaDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

    /*
    API Calls below
     */

	/**
	 * @return The type of the NetWorth account.
	 */
	public MenigaNetWorthAccountType getAccountType() {
		return accountType;
	}

	/**
	 * @return The balance history for this NetWorth account.
	 */
	public List<MenigaNetWorthBalance> getHistory() {
		return history;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		MenigaNetWorth that = (MenigaNetWorth) o;

		if (this.accountId != that.accountId) {
			return false;
		}
		if (this.realmAccountTypeId != null ? !this.realmAccountTypeId.equals(that.realmAccountTypeId) : that.realmAccountTypeId != null) {
			return false;
		}
		if (this.accountName != null ? !this.accountName.equals(that.accountName) : that.accountName != null) {
			return false;
		}
		if (this.isImport != null ? !this.isImport.equals(that.isImport) : that.isImport != null) {
			return false;
		}
		if (this.isManual != null ? !this.isManual.equals(that.isManual) : that.isManual != null) {
			return false;
		}
		if (this.isExcluded != null ? !this.isExcluded.equals(that.isExcluded) : that.isExcluded != null) {
			return false;
		}
		if (this.netWorthType != that.netWorthType) {
			return false;
		}
		if (this.currentBalance != null ? !this.currentBalance.equals(that.currentBalance) : that.currentBalance != null) {
			return false;
		}
		if (this.history != null ? !this.history.equals(that.history) : that.history != null) {
			return false;
		}
		return this.accountType != null ? this.accountType.equals(that.accountType) : that.accountType == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (this.accountId ^ (this.accountId >>> 32));
		result = 31 * result + (this.realmAccountTypeId != null ? this.realmAccountTypeId.hashCode() : 0);
		result = 31 * result + (this.accountName != null ? this.accountName.hashCode() : 0);
		result = 31 * result + (this.isImport != null ? this.isImport.hashCode() : 0);
		result = 31 * result + (this.isManual != null ? this.isManual.hashCode() : 0);
		result = 31 * result + (this.isExcluded != null ? this.isExcluded.hashCode() : 0);
		result = 31 * result + (this.netWorthType != null ? this.netWorthType.hashCode() : 0);
		result = 31 * result + (this.currentBalance != null ? this.currentBalance.hashCode() : 0);
		result = 31 * result + (this.history != null ? this.history.hashCode() : 0);
		result = 31 * result + (this.accountType != null ? this.accountType.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return this.accountName;
	}

	/*
	 * API operations below
	 */

	/**
	 * Deletes this NetWorth account
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return MenigaNetWorth.apiOperator.deleteNetWorthAccount(this.accountId);
	}

	/**
	 * Updates this net worth object and persists changes made using setters.
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> update() {
		return MenigaNetWorth.apiOperator.updateNetWorthAccount(this.accountId, this.isExcluded, this.accountName);
	}

	/**
	 * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
	 *
	 * @return A Task of type void, the task will have an error and be marked as failed if it is not successful
	 */
	public Result<List<MenigaNetWorth>> refresh() {
		Result<List<MenigaNetWorth>> task = MenigaNetWorth.fetch(DateTime.now().minusYears(100), DateTime.now(), 0, -1);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaNetWorth>>() {
			@Override
			public void onFinished(List<MenigaNetWorth> result, boolean failed) {
				if (!failed) {
					for (MenigaNetWorth netWorth : result) {
						if (netWorth.getId() == MenigaNetWorth.this.getId()) {
							try {
								Merge.merge(MenigaNetWorth.this, netWorth);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});
	}

	public static Result<MenigaNetWorth> fetch(long id) {
		return MenigaNetWorth.apiOperator.getNetWorth(id);
	}

	/**
	 * Get net worth. Unknown account types are ignored.
	 *
	 * @param startDate The start date and time
	 * @param endDate   The end date and time
	 * @param skip      How many accounts should be skipped
	 * @param take      How many accounts should be fetched. Default is 20.
	 * @return A list of all net worth objects that match the input parameters criteria
	 */
	public static Result<List<MenigaNetWorth>> fetch(DateTime startDate, DateTime endDate, int skip, int take) {
		return MenigaNetWorth.apiOperator.getNetWorth(startDate, endDate, false, skip, take);
	}

	/**
	 * Get net worth summary. Unknown account types are ignored.
	 *
	 * @param startDate The start date and time
	 * @param endDate   The end date and time
	 * @param skip      How many accounts should be skipped. Default is 0.
	 * @param take      How many accounts should be fetched. Default is 20.
	 * @return A net worth summary object
	 */
	public static Result<List<MenigaNetWorth>> fetchSummary(DateTime startDate, DateTime endDate, int skip, int take) {
		return MenigaNetWorth.apiOperator.getNetWorth(startDate, endDate, true, skip, take);
	}

	/**
	 * Create manual net worth account.
	 *
	 * @param initialBalance     The initial balance of the account. Must be a positive number. The service changes it according to the net worth type.
	 * @param currentBalance     Current balance of the account. Must be a positive number. The service changes it according to the net worth type.
	 * @param accountIdentifier  Identifier for the account set by the originating bank. This identifier is used when getting Account statements.
	 * @param name               The display name of the account.
	 * @param initialBalanceDate The initial balance date of the account.
	 * @return The newly created net worth account
	 */
	public static Result<MenigaNetWorth> create(
			MenigaDecimal initialBalance,
			final MenigaDecimal currentBalance,
			String accountIdentifier,
			final String name,
			final NetWorthType networthType,
			DateTime initialBalanceDate) {
		return MenigaNetWorth.apiOperator.createNetWorthAccount(
				initialBalance,
				currentBalance,
				accountIdentifier,
				name,
				networthType.toString(),
				initialBalanceDate);
	}

	/**
	 * Get the first account balance history entry date. Useful e.g. for creating graphs
	 *
	 * @param excludeAccountsExcludedFromNetWorth Indicates if excluded accounts should be included
	 * @return the date of the first entry.
	 */
	public static Result<MenigaNetWorthBalance> fetchFirstBalanceEntry(boolean excludeAccountsExcludedFromNetWorth) {
		return MenigaNetWorth.apiOperator.fetchFirstBalanceEntry(excludeAccountsExcludedFromNetWorth);
	}

	/**
	 * Retrieves all NetWorth types
	 *
	 * @return A Task containing a list of all the NetWorth types
	 */
	public static Result<List<KeyVal<Long, String>>> fetchNetWorthTypes() {
		return MenigaNetWorth.apiOperator.getNetWorthTypes();
	}
}
