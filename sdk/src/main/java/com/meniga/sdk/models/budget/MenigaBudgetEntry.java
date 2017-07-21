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
    protected DateTime updatedAt;
    protected DateTime startDate;
    protected DateTime endDate;
    protected List<Long> categoryIds;
    // For a planning entry
    protected int autoFillType;

    protected MenigaBudgetEntry() {

    }

	protected MenigaBudgetEntry(Parcel in) {
		this.id = in.readLong();
		this.targetAmount = (MenigaDecimal) in.readSerializable();
		this.spentAmount = (MenigaDecimal) in.readSerializable();
		this.updatedAt = (DateTime) in.readSerializable();
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.categoryIds = new ArrayList<>();
		in.readList(this.categoryIds, Long.class.getClassLoader());
	}

    public MenigaBudgetEntry(MenigaDecimal targetAmount, DateTime startDate, DateTime endDate, List<Long> categoryIds) {
        this.id = -1; // Should not be used
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.categoryIds = new ArrayList<>(categoryIds);
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
        if (targetAmount != null ? !targetAmount.equals(that.targetAmount) : that.targetAmount != null) {
	        return false;
        }
        if (spentAmount != null ? !spentAmount.equals(that.spentAmount) : that.spentAmount != null) {
	        return false;
        }
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
	        return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
	        return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
	        return false;
        }
        return categoryIds != null ? categoryIds.equals(that.categoryIds) : that.categoryIds == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
        result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public MenigaDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(MenigaDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public int getAutoFillType() {
        return autoFillType;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
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
        dest.writeSerializable(this.updatedAt);
        dest.writeSerializable(this.startDate);
        dest.writeSerializable(this.endDate);
        dest.writeList(this.categoryIds);
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

	/*
	 * API operations below
	 */

	/**
	 * Retrieves list of budget entries for a given budget
	 * @param budgetId id of budget for which to fetch entries
	 * @param filter contraints to apply to list of retrieved entries ({@link BudgetFilter#categoryIds}, {@link BudgetFilter#startDate}, {@link BudgetFilter#endDate})
	 * @return Meniga task containing the list of all budgets that meet the filter constraints
	 */

	public static Result<List<MenigaBudgetEntry>> fetch(long budgetId, BudgetFilter filter) {
		return MenigaBudget.apiOperator.getBudgetEntries(budgetId, filter);
	}

	/**
	 * Retrieves a specify budget entry
	 * @param budgetId id of budget for which to fetch the entry
	 * @param entryId id of the entry to fetch
	 * @return Meniga task containing the budget entry
	 */

	public static Result<MenigaBudgetEntry> fetch(long budgetId, long entryId) {
		return MenigaBudget.apiOperator.getBudgetEntryById(budgetId, entryId);
	}

	/**
	 * Retrieves all planning related budget entries that satisfy filter
	 * @param budgetId id of budget for which to fetch the entry
	 * @param filter contraints to apply to list of retrieved entries ({@link BudgetFilter#categoryIds}, {@link BudgetFilter#startDate}, {@link BudgetFilter#endDate}, {@link BudgetFilter#autoFill})
	 * @return Meniga task containing the list of all budget entries that meet the filter constraints
	 */

	public static Result<List<MenigaBudgetEntry>> fetchPlanningEntries(long budgetId, BudgetFilter filter) {
		return apiOperator.getPlanningBudgetEntries(budgetId, filter);
	}

	/**
	 * Create budget entries for a budget
	 * @param budgetId id of budget for which to create the entries
	 * @param entries list of budget entries to create
	 * @param isRecurring are entries recurring entries
	 * @return Meniga task containing the list of the created budget entries
	 */

	public static Result<List<MenigaBudgetEntry>> create(long budgetId, List<MenigaBudgetEntry> entries, boolean isRecurring) {
		return MenigaBudget.apiOperator.createBudgetEntries(budgetId, entries, isRecurring);
	}

	/**
	 * Update a budget entry
	 * @param budgetId id of budget of the entry
	 * @param entry the budget entry to update
	 * @return Meniga task containing the update budget entry
	 */

	// This is static since the request needs additional information that is mandatory
	public static Result<MenigaBudgetEntry> update(long budgetId, MenigaBudgetEntry entry) {
		return MenigaBudget.apiOperator.updateBudgetEntry(budgetId, entry);
	}

	/**
	 * Delete a budget entry
	 * @param budgetId id of budget of the entry
	 * @param entryId id of the entry to delete
	 * @return Meniga task
	 */

	// This is static since the request needs additional information that is mandatory
	public static Result<Void> deleteById(long budgetId, long entryId) {
		return MenigaBudget.apiOperator.deleteBudgetEntry(budgetId, entryId);
	}
}
