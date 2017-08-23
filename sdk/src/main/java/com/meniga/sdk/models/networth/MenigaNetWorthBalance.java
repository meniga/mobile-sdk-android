package com.meniga.sdk.models.networth;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthBalanceOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a balance on a net worth account at a certain point in time.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaNetWorthBalance extends StateObject implements Parcelable, Serializable, Cloneable, Comparable<MenigaNetWorthBalance> {

	public static final Parcelable.Creator<MenigaNetWorthBalance> CREATOR = new Parcelable.Creator<MenigaNetWorthBalance>() {
		public MenigaNetWorthBalance createFromParcel(Parcel in) {
			return new MenigaNetWorthBalance(in);
		}

		public MenigaNetWorthBalance[] newArray(int size) {
			return new MenigaNetWorthBalance[size];
		}
	};

	protected static MenigaNetWorthBalanceOperations apiOperator;

	protected long id;
	protected Long accountId;
	protected MenigaDecimal balance;
	protected DateTime balanceDate;
	protected Boolean isDefault;

	protected MenigaNetWorthBalance() {
	}

	public MenigaNetWorthBalance(Parcel in) {
		this.id = in.readLong();
		long tmpAccId = in.readLong();
		if (tmpAccId > -1) {
			this.accountId = tmpAccId;
		}
		boolean balanceIsNull = in.readByte() == (byte) 1;
		if (!balanceIsNull) {
			this.balance = new MenigaDecimal(in.readDouble());
		}
		long ms = in.readLong();
		if (ms > -1) {
			this.balanceDate = new DateTime(ms);
		}
		Byte isDefaultRaw = in.readByte();
		this.isDefault = (isDefaultRaw == -1 ? null : isDefaultRaw != 0);
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaNetWorthBalanceOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaNetWorthBalanceOperations operator) {
		MenigaNetWorthBalance.apiOperator = operator;
	}

	/**
	 * @return The id of this balance history item
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The balance of the net worth account this history item is associated with at a certain point in time.
	 */
	public MenigaDecimal getBalance() {
		if (this.balance == null) {
			return new MenigaDecimal(0);
		}
		return this.balance;
	}

	/**
	 * Set a new balance at this point in time.
	 *
	 * @param balance The new balance at the point in time represented by this history entry
	 */
	public void setBalance(MenigaDecimal balance) {
		if (this.hasChanged(this.balance, balance)) {
			return;
		}
		this.changed();
		this.balance = balance;
	}

	/**
	 * @return The id of the net worth account this history entry is associated with
	 */
	public Long getNetWorthId() {
		return accountId;
	}

	/**
	 * @return The date of this balance history entry
	 */
	public DateTime getBalanceDate() {
		return balanceDate;
	}

	/**
	 * Set a new date for this history balance entry
	 *
	 * @param date The new date of the balance for the net worth account associated with this history balance item
	 */
	public void setBalanceDate(DateTime date) {
		if (this.hasChanged(this.balanceDate, date)) {
			return;
		}
		this.changed();
		this.balanceDate = date;
	}

	/**
	 * @return Indicates if the entry has been generated with default values. This happens when there is missing months (in the database) between the start and end date ranges sent in by the client.
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * @return The id of the net worth account this balance history entry belongs to
	 */
	public long getAccountId() {
		return this.accountId;
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaNetWorthBalance)) {
			return;
		}
		MenigaNetWorthBalance other = (MenigaNetWorthBalance) lastRevision;

		this.balanceDate = other.balanceDate;
		this.balance = other.balance;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.accountId == null ? -1 : this.accountId);
		dest.writeByte(this.balance == null ? (byte) 1 : (byte) 0);
		if (this.balance != null) {
			dest.writeDouble(this.balance.doubleValue());
		}
		dest.writeLong(this.balanceDate == null ? -1 : this.balanceDate.getMillis());
		dest.writeByte(this.isDefault == null ? -1 : this.isDefault ? (byte) 1 : (byte) 0);
	}

	@Override
	public MenigaNetWorthBalance clone() throws CloneNotSupportedException {
		return (MenigaNetWorthBalance) super.clone();
	}

	@Override
	public String toString() {
		if (this.balance == null) {
			return "null";
		}
		return Double.toString(this.balance.doubleValue()) + ", " + this.balanceDate == null ? "" : this.balanceDate.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaNetWorthBalance that = (MenigaNetWorthBalance) o;

		if (this.id != that.id) {
			return false;
		}
		if (this.accountId != null ? !this.accountId.equals(that.accountId) : that.accountId != null) {
			return false;
		}
		if (this.balance != null ? !this.balance.equals(that.balance) : that.balance != null) {
			return false;
		}
		if (this.balanceDate != null ? !this.balanceDate.equals(that.balanceDate) : that.balanceDate != null) {
			return false;
		}
		return this.isDefault != null ? this.isDefault.equals(that.isDefault) : that.isDefault == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (this.id ^ (this.id >>> 32));
		result = 31 * result + (this.accountId != null ? this.accountId.hashCode() : 0);
		result = 31 * result + (this.balance != null ? this.balance.hashCode() : 0);
		result = 31 * result + (this.balanceDate != null ? this.balanceDate.hashCode() : 0);
		result = 31 * result + (this.isDefault != null ? this.isDefault.hashCode() : 0);
		return result;
	}

    /*
    API Calls below
     */

	@Override
	public int compareTo(MenigaNetWorthBalance another) {
		if (another == null || another.balanceDate == null) {
			return 0;
		}
		return this.balanceDate.compareTo(another.balanceDate);
	}

	/*
	 * API operations below
	 */

	/**
	 * Updates an account balance history entry of a manual account.
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> update() {
		return MenigaNetWorthBalance.apiOperator.updateBalance(this.accountId, this.id, this.balance, this.balanceDate);
	}

	/**
	 * Deletes and account balance history entry of a manual account.
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return MenigaNetWorthBalance.apiOperator.deleteBalance(this.accountId, this.id);
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
						if (netWorth.getId() == MenigaNetWorthBalance.this.getAccountId()) {
							for (MenigaNetWorthBalance bala : netWorth.getHistory()) {
								if (bala.getId() == MenigaNetWorthBalance.this.getId()) {
									try {
										Merge.merge(MenigaNetWorthBalance.this, bala);
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}
									return;
								}
							}
						}
					}
				}
			}
		});
	}

	/**
	 * Creates an account balance history entry of a manual account.
	 *
	 * @param balance     The balance of the of the account balance history entry.
	 * @param balanceDate The balance date of the account balance history entry.
	 * @return A new net worth balance object
	 */
	public static Result<MenigaNetWorthBalance> create(final long netWorthId, MenigaDecimal balance, DateTime balanceDate) {
		Result<MenigaNetWorthBalance> task = MenigaNetWorthBalance.apiOperator.createNetWorthBalanceHistory(netWorthId, balance, balanceDate);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaNetWorthBalance>() {
			@Override
			public void onFinished(MenigaNetWorthBalance result, boolean failed) {
				if (!failed) {
					result.accountId = netWorthId;
					result.isDefault = false;
				}
			}
		});
	}
}
