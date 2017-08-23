package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetEntry implements Parcelable {

	protected static MenigaBudgetOperations apiOperator;

	protected long id;
	protected MenigaDecimal targetAmount;
	protected MenigaDecimal spentAmount;
	protected DateTime endDate;
	protected DateTime updatedAt;
	protected Integer generationType;
	protected List<Long> categoryIds;
	protected long budgetId;


	protected MenigaBudgetEntry() {

	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaBudgetOperations interface for carrying out api operations on this class.
	 */

	public static void setOperator(MenigaBudgetOperations operator) {
		MenigaBudget.apiOperator = operator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaBudgetEntry that = (MenigaBudgetEntry) o;

		if (id != that.id) return false;
		if (budgetId != that.budgetId) return false;
		if (targetAmount != null ? !targetAmount.equals(that.targetAmount) : that.targetAmount != null)
			return false;
		if (spentAmount != null ? !spentAmount.equals(that.spentAmount) : that.spentAmount != null)
			return false;
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
		if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null)
			return false;
		if (generationType != null ? !generationType.equals(that.generationType) : that.generationType != null)
			return false;
		return categoryIds != null ? categoryIds.equals(that.categoryIds) : that.categoryIds == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
		result = 31 * result + (generationType != null ? generationType.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
		return result;
	}

	public long getId() {
		return id;
	}

	public MenigaDecimal getTargetAmount() {
		return targetAmount;
	}

	public MenigaDecimal getSpentAmount() {
		return spentAmount;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public Integer getGenerationType() {
		return generationType;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public long getBudgetId() {
		return budgetId;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeSerializable(this.targetAmount);
		dest.writeSerializable(this.spentAmount);
		dest.writeSerializable(this.endDate);
		dest.writeSerializable(this.updatedAt);
		dest.writeValue(this.generationType);
		dest.writeList(this.categoryIds);
		dest.writeLong(this.budgetId);
	}

	protected MenigaBudgetEntry(Parcel in) {
		this.id = in.readLong();
		this.targetAmount = (MenigaDecimal) in.readSerializable();
		this.spentAmount = (MenigaDecimal) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.updatedAt = (DateTime) in.readSerializable();
		this.generationType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.categoryIds = new ArrayList<Long>();
		in.readList(this.categoryIds, Long.class.getClassLoader());
		this.budgetId = in.readLong();
	}

	public static final Creator<MenigaBudgetEntry> CREATOR = new Creator<MenigaBudgetEntry>() {
		@Override
		public MenigaBudgetEntry createFromParcel(Parcel source) {
			return new MenigaBudgetEntry(source);
		}

		@Override
		public MenigaBudgetEntry[] newArray(int size) {
			return new MenigaBudgetEntry[size];
		}
	};


	/**
	 * Updates budget entry
	 *
	 * @param targetAmount   The target amount for the entry. Positive and negative numbers allowed ,
	 * @param startDate      The inclusive lower date limit for period on the budget ,
	 * @param endDate        The inclusive upper date limit for the period on the budget ,
	 * @param generationType The generation rule to use if a planning entry. If present on a GET then this is a generated entry ,
	 * @param categoryIds    An array of category Ids linked to. Categories can be of any category type
	 * @return Void
	 */
	public Result<MenigaBudgetEntry> update(MenigaDecimal targetAmount, DateTime startDate, DateTime endDate, int generationType, List<Long> categoryIds) {
		return apiOperator.updateEntry(this.budgetId, this.id, targetAmount, startDate, endDate, generationType, categoryIds);
	}

}
