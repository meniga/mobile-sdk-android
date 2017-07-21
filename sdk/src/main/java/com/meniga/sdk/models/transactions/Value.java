package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a value in a transaction serie.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class Value implements Serializable, Parcelable {
	public static final Creator<Value> CREATOR = new Creator<Value>() {
		@Override
		public Value createFromParcel(Parcel source) {
			return new Value(source);
		}

		@Override
		public Value[] newArray(int size) {
			return new Value[size];
		}
	};

	private MenigaDecimal nettoAmount;
	private MenigaDecimal totalPositive;
	private MenigaDecimal totalNegative;
	private DateTime date;
	private List<Long> transactionIds;

	protected Value(Parcel in) {
		this.nettoAmount = (MenigaDecimal) in.readSerializable();
		this.totalPositive = (MenigaDecimal) in.readSerializable();
		this.totalNegative = (MenigaDecimal) in.readSerializable();
		this.date = (DateTime) in.readSerializable();
		this.transactionIds = new ArrayList<>();
		in.readList(this.transactionIds, Long.class.getClassLoader());
	}

	/**
	 * @return The amount of the TransactionSeriesData (period depends on AggregationLevelEnum in the Options).
	 */
	public MenigaDecimal getNettoAmount() {
		return nettoAmount;
	}

	/**
	 * @return The income amount of the TransactionSeriesData (period depends on AggregationLevelEnum in the Options).
	 */
	public MenigaDecimal getTotalPositive() {
		return totalPositive;
	}

	/**
	 * @return The expenses of the TransactionSeriesData (period depends on AggregationLevelEnum in the Options).
	 */
	public MenigaDecimal getTotalNegative() {
		return totalNegative;
	}

	/**
	 * @return The objects corresponding date, always the first day of the period (None/Day/Week/Month....).
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * @return The Ids of the transactions used to generate the TransactionSeriesData.
	 */
	public List<Long> getTransactionIds() {
		return transactionIds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Value value = (Value) o;

		if (nettoAmount != null ? !nettoAmount.equals(value.nettoAmount) : value.nettoAmount != null) {
			return false;
		}
		if (totalPositive != null ? !totalPositive.equals(value.totalPositive) : value.totalPositive != null) {
			return false;
		}
		if (totalNegative != null ? !totalNegative.equals(value.totalNegative) : value.totalNegative != null) {
			return false;
		}
		if (date != null ? !date.equals(value.date) : value.date != null) {
			return false;
		}
		return transactionIds != null ? transactionIds.equals(value.transactionIds) : value.transactionIds == null;
	}

	@Override
	public int hashCode() {
		int result = nettoAmount != null ? nettoAmount.hashCode() : 0;
		result = 31 * result + (totalPositive != null ? totalPositive.hashCode() : 0);
		result = 31 * result + (totalNegative != null ? totalNegative.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (transactionIds != null ? transactionIds.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.nettoAmount);
		dest.writeSerializable(this.totalPositive);
		dest.writeSerializable(this.totalNegative);
		dest.writeSerializable(this.date);
		dest.writeList(this.transactionIds);
	}
}
