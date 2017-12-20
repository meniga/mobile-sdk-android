package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.io.Serializable;

/**
 * Represents aggregated information about the result set.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class Statistics implements Serializable, Parcelable {
	public static final Creator<Statistics> CREATOR = new Creator<Statistics>() {
		@Override
		public Statistics createFromParcel(Parcel source) {
			return new Statistics(source);
		}

		@Override
		public Statistics[] newArray(int size) {
			return new Statistics[size];
		}
	};

	protected MenigaDecimal currentMonthTotal;
	protected MenigaDecimal total;
	protected MenigaDecimal average;

	protected Statistics(Parcel in) {
		this.currentMonthTotal = (MenigaDecimal) in.readSerializable();
		this.total = (MenigaDecimal) in.readSerializable();
		this.average = (MenigaDecimal) in.readSerializable();
	}

	/**
	 * @return The total Amount for the current month.
	 */
	public MenigaDecimal getCurrentMonthTotal() {
		return currentMonthTotal;
	}

	/**
	 * @return The total amount for the whole transaction series.
	 */
	public MenigaDecimal getTotal() {
		return total;
	}

	/**
	 * @return The average amount of a transaction serie data object.
	 */
	public MenigaDecimal getAverage() {
		return average;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Statistics that = (Statistics) o;

		if (currentMonthTotal != null ? !currentMonthTotal.equals(that.currentMonthTotal) : that.currentMonthTotal != null) {
			return false;
		}
		if (total != null ? !total.equals(that.total) : that.total != null) {
			return false;
		}
		return average != null ? average.equals(that.average) : that.average == null;
	}

	@Override
	public int hashCode() {
		int result = currentMonthTotal != null ? currentMonthTotal.hashCode() : 0;
		result = 31 * result + (total != null ? total.hashCode() : 0);
		result = 31 * result + (average != null ? average.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.currentMonthTotal);
		dest.writeSerializable(this.total);
		dest.writeSerializable(this.average);
	}
}
