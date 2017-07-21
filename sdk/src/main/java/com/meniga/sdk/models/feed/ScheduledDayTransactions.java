package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ScheduledDayTransactions implements Serializable, Cloneable, Parcelable {
	public static final Creator<ScheduledDayTransactions> CREATOR = new Creator<ScheduledDayTransactions>() {
		@Override
		public ScheduledDayTransactions createFromParcel(Parcel source) {
			return new ScheduledDayTransactions(source);
		}

		@Override
		public ScheduledDayTransactions[] newArray(int size) {
			return new ScheduledDayTransactions[size];
		}
	};

	private DateTime date;
	private MenigaDecimal income;
	private MenigaDecimal expenses;
	private List<Long> merchantIds;
	private Map<Long, MenigaDecimal> expensesPerCategory;
	private Map<Long, MenigaDecimal> incomePerCategory;

	protected ScheduledDayTransactions() {
	}

	protected ScheduledDayTransactions(Parcel in) {
		this.date = (DateTime) in.readSerializable();
		this.income = (MenigaDecimal) in.readSerializable();
		this.expenses = (MenigaDecimal) in.readSerializable();
		this.merchantIds = new ArrayList<>();
		in.readList(this.merchantIds, Long.class.getClassLoader());
		int expensesPerCategorySize = in.readInt();
		this.expensesPerCategory = new HashMap<>(expensesPerCategorySize);
		for (int i = 0; i < expensesPerCategorySize; i++) {
			Long key = (Long) in.readValue(Long.class.getClassLoader());
			MenigaDecimal value = (MenigaDecimal) in.readSerializable();
			this.expensesPerCategory.put(key, value);
		}
		int incomePerCategorySize = in.readInt();
		this.incomePerCategory = new HashMap<>(incomePerCategorySize);
		for (int i = 0; i < incomePerCategorySize; i++) {
			Long key = (Long) in.readValue(Long.class.getClassLoader());
			MenigaDecimal value = (MenigaDecimal) in.readSerializable();
			this.incomePerCategory.put(key, value);
		}
	}

	public DateTime getDate() {
		return this.date;
	}

	public MenigaDecimal getIncome() {
		return this.income;
	}

	public MenigaDecimal getExpenses() {
		return this.expenses;
	}

	public List<Long> getMerchantIds() {
		return this.merchantIds;
	}

	public Map<Long, MenigaDecimal> getExpensesPerCategory() {
		return this.expensesPerCategory;
	}

	public Map<Long, MenigaDecimal> getIncomePerCategory() {
		return this.incomePerCategory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ScheduledDayTransactions that = (ScheduledDayTransactions) o;

		if (this.date != null ? !this.date.equals(that.date) : that.date != null) {
			return false;
		}
		if (this.income != null ? !this.income.equals(that.income) : that.income != null) {
			return false;
		}
		if (this.expenses != null ? !this.expenses.equals(that.expenses) : that.expenses != null) {
			return false;
		}
		if (this.merchantIds != null ? !this.merchantIds.equals(that.merchantIds) : that.merchantIds != null) {
			return false;
		}
		if (this.expensesPerCategory != null ? !this.expensesPerCategory.equals(that.expensesPerCategory) : that.expensesPerCategory != null) {
			return false;
		}
		return this.incomePerCategory != null ? this.incomePerCategory.equals(that.incomePerCategory) : that.incomePerCategory == null;
	}

	@Override
	public int hashCode() {
		int result = this.date != null ? this.date.hashCode() : 0;
		result = 31 * result + (this.income != null ? this.income.hashCode() : 0);
		result = 31 * result + (this.expenses != null ? this.expenses.hashCode() : 0);
		result = 31 * result + (this.merchantIds != null ? this.merchantIds.hashCode() : 0);
		result = 31 * result + (this.expensesPerCategory != null ? this.expensesPerCategory.hashCode() : 0);
		result = 31 * result + (this.incomePerCategory != null ? this.incomePerCategory.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.date);
		dest.writeSerializable(this.income);
		dest.writeSerializable(this.expenses);
		dest.writeList(this.merchantIds);
		dest.writeInt(this.expensesPerCategory.size());
		for (Map.Entry<Long, MenigaDecimal> entry : this.expensesPerCategory.entrySet()) {
			dest.writeValue(entry.getKey());
			dest.writeSerializable(entry.getValue());
		}
		dest.writeInt(this.incomePerCategory.size());
		for (Map.Entry<Long, MenigaDecimal> entry : this.incomePerCategory.entrySet()) {
			dest.writeValue(entry.getKey());
			dest.writeSerializable(entry.getValue());
		}
	}
}
