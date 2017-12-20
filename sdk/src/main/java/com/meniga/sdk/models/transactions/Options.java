package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.transactions.enums.TimeResolution;

import java.io.Serializable;

/**
 * Represents options for how the series should be constructed.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class Options implements Serializable, Parcelable {
	public static final Creator<Options> CREATOR = new Creator<Options>() {
		@Override
		public Options createFromParcel(Parcel source) {
			return new Options(source);
		}

		@Override
		public Options[] newArray(int size) {
			return new Options[size];
		}
	};

	protected TimeResolution timeResolution;
	protected boolean overTime = true;
	protected Boolean includeTransactions;
	protected Boolean includeTransactionIds;

	public Options(TimeResolution tr, boolean overTime) {
		this(tr, overTime, null, null);
	}

	public Options(TimeResolution tr, boolean overTime, Boolean includeTransactions) {
		this(tr, overTime, includeTransactions, null);
	}

	public Options(TimeResolution tr, boolean overTime, Boolean includeTransactions, Boolean includeTransactionIds) {
		this.timeResolution = tr;
		this.overTime = overTime;
		this.includeTransactions = includeTransactions;
		this.includeTransactionIds = includeTransactionIds;
	}

	protected Options(Parcel in) {
		int tmpTimeResolution = in.readInt();
		this.timeResolution = tmpTimeResolution == -1 ? null : TimeResolution.values()[tmpTimeResolution];
		this.overTime = in.readByte() != 0;
		this.includeTransactions = in.readByte() != 0;
		this.includeTransactionIds = in.readByte() != 0;
	}

	/**
	 * @return Controls the aggregation level over time. Currently only None and Month are supported = ['0', '1', '2', '3', '4', '5'].
	 */
	public TimeResolution getTimeResolution() {
		return timeResolution;
	}

	/**
	 * @return Whether or not the series should be over time or just a list containing a single object with the whole period aggregated.
	 */
	public boolean isOverTime() {
		return overTime;
	}

	/**
	 * @return Whether or not the tranactions used to generate the series should be returned.
	 */
	public boolean isIncludeTransactions() {
		return includeTransactions;
	}

	/**
	 * @return Whether or not the transactionIds of the transactions used to create the series are returned.
	 */
	public boolean isIncludeTransactionIds() {
		return includeTransactionIds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Options options = (Options) o;

		if (overTime != options.overTime) {
			return false;
		}
		if (includeTransactions != options.includeTransactions) {
			return false;
		}
		if (includeTransactionIds != options.includeTransactionIds) {
			return false;
		}
		return timeResolution == options.timeResolution;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.timeResolution == null ? -1 : this.timeResolution.ordinal());
		dest.writeByte(overTime ? (byte) 1 : (byte) 0);
		dest.writeByte(includeTransactions ? (byte) 1 : (byte) 0);
		dest.writeByte(includeTransactionIds ? (byte) 1 : (byte) 0);
	}

	@Override
	public int hashCode() {
		int result = timeResolution != null ? timeResolution.hashCode() : 0;
		result = 31 * result + (overTime ? 1 : 0);
		result = 31 * result + (includeTransactions != null ? includeTransactions.hashCode() : 0);
		result = 31 * result + (includeTransactionIds != null ? includeTransactionIds.hashCode() : 0);
		return result;
	}
}
