package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.enums.TimeResolution;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionSeriesOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an aggregation of transaction data/series from more complex/complete queries into the
 * Meniga system about transactions.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionSeries implements Serializable, Parcelable, Cloneable {
	public static final Parcelable.Creator<MenigaTransactionSeries> CREATOR = new Parcelable.Creator<MenigaTransactionSeries>() {
		@Override
		public MenigaTransactionSeries createFromParcel(Parcel source) {
			return new MenigaTransactionSeries(source);
		}

		@Override
		public MenigaTransactionSeries[] newArray(int size) {
			return new MenigaTransactionSeries[size];
		}
	};

	protected static MenigaTransactionSeriesOperations apiOperator;

	protected TimeResolution timeResolution;
	protected Statistics statistics;
	protected List<Value> values;
	protected List<MenigaTransaction> transactions;
	protected List<Long> transactionIds;

	protected MenigaTransactionSeries() {
	}

	protected MenigaTransactionSeries(Parcel in) {
		int tmpTimeResolution = in.readInt();
		this.timeResolution = tmpTimeResolution == -1 ? null : TimeResolution.values()[tmpTimeResolution];
		this.statistics = in.readParcelable(Statistics.class.getClassLoader());
		this.values = in.createTypedArrayList(Value.CREATOR);
		this.transactions = in.createTypedArrayList(MenigaTransaction.CREATOR);
		this.transactionIds = new ArrayList<>();
		in.readList(this.transactionIds, Long.class.getClassLoader());
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTransactionSeriesOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTransactionSeriesOperations operator) {
		MenigaTransactionSeries.apiOperator = operator;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	protected MenigaTransactionSeries clone() throws CloneNotSupportedException {
		return (MenigaTransactionSeries) super.clone();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.timeResolution == null ? -1 : this.timeResolution.ordinal());
		dest.writeParcelable(this.statistics, flags);
		dest.writeTypedList(values);
		dest.writeTypedList(transactions);
		dest.writeList(this.transactionIds);
	}

	/**
	 * @return The total value for the transaction in the list.
	 */
	public MenigaDecimal getSumNettoValues() {
		MenigaDecimal sum = new MenigaDecimal(0);
		for (Value val : this.values) {
			sum = sum.add(val.getNettoAmount());
		}
		return sum;
	}

	/**
	 * @return The time resolution of a time serie (None/Day/Week/Month...) - if not over time then default None = ['0', '1', '2', '3', '4', '5'].
	 */
	public TimeResolution getTimeResolution() {
		return timeResolution;
	}

	/**
	 * @return Statistics for series over time - Only returned if in the is set to true.
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * @return The values for the TransactionSeries - If in the is set to false the list will only contain one object (Aggregated as whole).
	 */
	public List<Value> getValues() {
		return values;
	}

	/**
	 * @return The set of transactions used to create the series. Only returned if in the is set to true.
	 */
	public List<MenigaTransaction> getTransactions() {
		return transactions;
	}

	/**
	 * @return The ids of the set of transactions used to create the series. Only returned if in the is set to true.
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

		MenigaTransactionSeries that = (MenigaTransactionSeries) o;

		if (timeResolution != that.timeResolution) {
			return false;
		}
		if (statistics != null ? !statistics.equals(that.statistics) : that.statistics != null) {
			return false;
		}
		if (values != null ? !values.equals(that.values) : that.values != null) {
			return false;
		}
		if (transactions != null ? !transactions.equals(that.transactions) : that.transactions != null) {
			return false;
		}
		return transactionIds != null ? transactionIds.equals(that.transactionIds) : that.transactionIds == null;
	}

	@Override
	public int hashCode() {
		int result = timeResolution != null ? timeResolution.hashCode() : 0;
		result = 31 * result + (statistics != null ? statistics.hashCode() : 0);
		result = 31 * result + (values != null ? values.hashCode() : 0);
		result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
		result = 31 * result + (transactionIds != null ? transactionIds.hashCode() : 0);
		return result;
	}


	/**
	 * Retrieves a series of transactions aggregated over time.
	 *
	 * @param filter          A Filter used to filter out the set of transactions which the series are
	 *                        generated from. Note: Each has its own filter which allows for selecting a
	 *                        subset of the transactions queried using this filter. Note : The PeriodFrom
	 *                        and PeriodTo properties always need to be populated.
	 * @param options         Option object for the Transaction Series List.
	 * @param seriesSelectors A list of SeriesRequests each containing its own filter in order to
	 *                        select a subset of the "main" set.
	 * @return A list of transaction series objects that match the given criteria
	 */
	public static Result<List<MenigaTransactionSeries>> fetch(TransactionsFilter filter, Options options, List<SeriesSelector> seriesSelectors) {
		return MenigaTransactionSeries.apiOperator.getTransactionSeries(filter, options, seriesSelectors);
	}
}
