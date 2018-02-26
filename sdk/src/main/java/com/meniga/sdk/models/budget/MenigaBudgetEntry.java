package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.enums.GenerationType;
import com.meniga.sdk.models.budget.enums.GenerationTypeValue;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.webservices.budget.GetBudgetEntryById;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetEntry implements Parcelable, Serializable {
	protected static MenigaBudgetOperations apiOperator;

	protected long id;
	protected MenigaDecimal targetAmount;
	protected DateTime startDate;
	protected DateTime endDate;
	protected DateTime updatedAt;
	protected long budgetId;
	protected int generationType;
	protected MenigaDecimal spentAmount;
	protected List<Long> categoryIds;

	protected MenigaBudgetEntry() {
	}

	public static void setOperator(MenigaBudgetOperations operator) {
		MenigaBudgetEntry.apiOperator = operator;
	}

	public long getId() {
		return id;
	}

	public MenigaDecimal getTargetAmount() {
		return targetAmount == null ? MenigaDecimal.ZERO : targetAmount;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public long getBudgetId() {
		return budgetId;
	}

	public GenerationTypeValue getGenerationType() {
		return new GenerationTypeValue(generationType);
	}

	public MenigaDecimal getSpentAmount() {
		return spentAmount;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaBudgetEntry that = (MenigaBudgetEntry) o;

		if (id != that.id) {
			return false;
		}
		if (budgetId != that.budgetId) {
			return false;
		}
		if (generationType != that.generationType) {
			return false;
		}
		if (targetAmount != null ? !targetAmount.equals(that.targetAmount) : that.targetAmount != null) {
			return false;
		}
		if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
			return false;
		}
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
			return false;
		}
		if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
			return false;
		}
		if (spentAmount != null ? !spentAmount.equals(that.spentAmount) : that.spentAmount != null) {
			return false;
		}
		return categoryIds != null ? categoryIds.equals(that.categoryIds) : that.categoryIds == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
		result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
		result = 31 * result + generationType;
		result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeSerializable(this.targetAmount);
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
		dest.writeSerializable(this.updatedAt);
		dest.writeLong(this.budgetId);
		dest.writeInt(this.generationType);
		dest.writeSerializable(this.spentAmount);
		dest.writeList(this.categoryIds);
	}

	protected MenigaBudgetEntry(Parcel in) {
		this.id = in.readLong();
		this.targetAmount = (MenigaDecimal) in.readSerializable();
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.updatedAt = (DateTime) in.readSerializable();
		this.budgetId = in.readLong();
		this.generationType = in.readInt();
		this.spentAmount = (MenigaDecimal) in.readSerializable();
		this.categoryIds = new ArrayList<>();
		in.readList(this.categoryIds, Long.class.getClassLoader());
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

	@Override
	public String toString() {
		if (categoryIds.size() > 0) {
			return categoryIds.get(0) + ": " + startDate.toString("dd.MM.YYYY") + " - " + endDate.toString("dd.MM.YYYY");
		}
		return super.toString();
	}

	/*
	* API Calls
	*/

	public static Result<List<MenigaBudgetEntry>> fetch(long budgetId) {
		return fetch(
				budgetId,
				DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay(),
				DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay().plusMonths(1).minusDays(1));
	}

	public static Result<List<MenigaBudgetEntry>> fetch(long budgetId, DateTime month) {
		return fetch(
				budgetId,
				month.withDayOfMonth(1).withTimeAtStartOfDay(),
				month.withDayOfMonth(1).withTimeAtStartOfDay().plusMonths(1).minusDays(1));
	}

	public static Result<List<MenigaBudgetEntry>> fetch(long budgetId, DateTime from, DateTime to) {
		return fetch(budgetId, from, to, null);
	}

	public static Result<List<MenigaBudgetEntry>> fetch(long budgetId, DateTime from, DateTime to, List<Long> categoryIds) {
		FetchBudgetEntriesFilter filter = new FetchBudgetEntriesFilter(budgetId);
		filter.setStartDate(from);
		filter.setEndDate(to);
		filter.setCategoryIds(categoryIds);
		filter.setAllowOverlappingEntries(true);
		return fetch(filter);
	}

	public static Result<List<MenigaBudgetEntry>> fetch(FetchBudgetEntriesFilter filter) {
		return apiOperator.getBudgetEntries(FetchBudgetEntriesFilterExtensions.toGetBudgetEntries(filter));
	}

	public static Result<MenigaBudgetEntry> fetch(long budgetId, long entryId) {
		return apiOperator.getBudgetEntry(new GetBudgetEntryById(budgetId, entryId));
	}

	/**
	 * Use {@link #update(long, BudgetRulesUpdate)} instead.
	 */
	@Deprecated
	public static Result<Void> update(
			long budgetId,
			MenigaDecimal targetAmount,
			DateTime startDate,
			DateTime endDate,
			MenigaCategory category,
			GenerationType generationType,
			int generationValue,
			DateTime wasNotUsed) {
		GenerationTypeValue generationTypeValue = new GenerationTypeValue(generationValue);
		generationTypeValue.setType(generationType);

		BudgetRulesUpdate parameters = new BudgetRulesUpdate(startDate, category.getId());
		parameters.setTargetAmount(targetAmount);
		parameters.setEndDate(endDate);
		parameters.setGenerationTypeValue(generationTypeValue);

		return update(budgetId, parameters);
	}

	public static Result<Void> update(long budgetId, BudgetRulesUpdate parameters) {
		return apiOperator.updateBudgetRules(budgetId, BudgetRulesUpdateExtensions.toUpdateBudgetRules(parameters));
	}

	public static Result<List<MenigaBudgetEntry>> create(long budgetId, NewBudgetEntry parameters) {
		return apiOperator.createBudgetEntry(budgetId, CreateBudgetEntryExtensions.toCreateBudgetEntry(parameters));
	}

	public Result<MenigaBudgetEntry> update(BudgetEntryUpdate parameters) {
		return apiOperator.updateBudgetEntry(budgetId, id, BudgetEntryUpdateExtensions.toUpdateBudgetEntry(parameters));
	}

	public Result<Void> delete() {
		return apiOperator.deleteBudgetEntry(budgetId, id);
	}
}
