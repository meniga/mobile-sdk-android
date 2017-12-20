package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * A filter object used to select which set of transactions to use to generate the requested series.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class SeriesSelector implements Serializable, Parcelable {
	public static final Creator<SeriesSelector> CREATOR = new Creator<SeriesSelector>() {
		@Override
		public SeriesSelector createFromParcel(Parcel source) {
			return new SeriesSelector(source);
		}

		@Override
		public SeriesSelector[] newArray(int size) {
			return new SeriesSelector[size];
		}
	};

	protected TransactionsFilter filter;

	public SeriesSelector(TransactionsFilter filter) {
		this.filter = filter;
	}

	protected SeriesSelector(Parcel in) {
		this.filter = (TransactionsFilter) in.readSerializable();
	}

	public TransactionsFilter getFilter() {
		return filter;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SeriesSelector that = (SeriesSelector) o;

		return filter != null ? filter.equals(that.filter) : that.filter == null;
	}

	@Override
	public int hashCode() {
		return filter != null ? filter.hashCode() : 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.filter);
	}
}
