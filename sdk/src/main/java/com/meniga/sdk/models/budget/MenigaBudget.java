package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudget implements Parcelable, Serializable {

	protected static MenigaBudgetOperations apiOperator;
	protected long id;
	protected BudgetType type;
	protected String name;
	protected String description;
	protected List<Long> accountIds;
	protected DateTime created;
	protected List<MenigaBudgetEntry> entries;

	protected MenigaBudget() {
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaBudgetOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaBudgetOperations operator) {
		MenigaBudget.apiOperator = operator;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public DateTime getCreated() {
		return created;
	}

	public List<MenigaBudgetEntry> getEntries() {
		return entries;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAccountIds(List<Long> accountIds) {
		this.accountIds = accountIds;
	}

	public void setEntries(List<MenigaBudgetEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeInt(this.type == null ? -1 : this.type.ordinal());
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeList(this.accountIds);
		dest.writeSerializable(this.created);
		dest.writeTypedList(this.entries);
	}

	protected MenigaBudget(Parcel in) {
		this.id = in.readLong();
		int tmpType = in.readInt();
		this.type = tmpType == -1 ? null : BudgetType.values()[tmpType];
		this.name = in.readString();
		this.description = in.readString();
		this.accountIds = new ArrayList<>();
		in.readList(this.accountIds, Long.class.getClassLoader());
		this.created = (DateTime) in.readSerializable();
		this.entries = in.createTypedArrayList(MenigaBudgetEntry.CREATOR);
	}

	public static final Creator<MenigaBudget> CREATOR = new Creator<MenigaBudget>() {
		@Override
		public MenigaBudget createFromParcel(Parcel source) {
			return new MenigaBudget(source);
		}

		@Override
		public MenigaBudget[] newArray(int size) {
			return new MenigaBudget[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaBudget that = (MenigaBudget) o;

		if (id != that.id) {
			return false;
		}
		if (type != that.type) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (description != null ? !description.equals(that.description) : that.description != null) {
			return false;
		}
		if (accountIds != null ? !accountIds.equals(that.accountIds) : that.accountIds != null) {
			return false;
		}
		if (created != null ? !created.equals(that.created) : that.created != null) {
			return false;
		}
		return entries != null ? entries.equals(that.entries) : that.entries == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (created != null ? created.hashCode() : 0);
		result = 31 * result + (entries != null ? entries.hashCode() : 0);
		return result;
	}

	/*
	* API Calls
	*/

	/**
	 * Retrieves a filtered list of all budgets for the authenticated user
	 * @param filter constraints to filter the list of retrieved budgets and their data on ({@link BudgetFilter#categoryIds}, {@link BudgetFilter#startDate}, {@link BudgetFilter#endDate}, {@link BudgetFilter#allowOverlappingDates}, {@link BudgetFilter#type})
	 * @return Meniga task containing the list of all budgets that meet the filter constraints
	 */
	public static Result<List<MenigaBudget>> fetch(BudgetFilter filter) {
		return apiOperator.getBudgets(filter);
	}

	/**
	 * Retrieves a specific budget by id
	 * @param id budget id
	 * @param filter constraints to limit the data retrieved for the budget ({@link BudgetFilter#categoryIds}, {@link BudgetFilter#startDate}, {@link BudgetFilter#endDate}, {@link BudgetFilter#allowOverlappingDates}, {@link BudgetFilter#type}, {@link BudgetFilter#includeEntries})
	 * @return Meniga task containing the list of all budgets that meet the filter constraints
	 */
	public static Result<MenigaBudget> fetch(long id, BudgetFilter filter) {
		return apiOperator.getBudgetById(id, filter);
	}

	/**
	 * Create a budget
	 * @param name name of budget
	 * @param description description for budget
	 * @param accountIds accountId's for the accounts the budget applies to
	 * @param entries budget entries to set for the created budget
	 * @return Meniga task returning the created budget object
	 */
	public static Result<MenigaBudget> create(String name, String description, List<Long> accountIds, List<MenigaBudgetEntry> entries) {
		return apiOperator.createBudget(name, description, accountIds, entries);
	}

	/**
	 * Delete a budget. This will delete the budget corresponding to this budget instance
	 * @return Meniga task
	 */
	public Result<Void> delete() {
		return apiOperator.deleteBudget(this.id);
	}

	/**
	 * Update this budget instance. A new name, description, accountIds and entries can be set for a budget.
	 * @return Meniga task returning the updated budget object, the current instance will also be updated
	 */
	public Result<MenigaBudget> update() {
		Result<MenigaBudget> task = apiOperator.updateBudget(this);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaBudget>() {
			@Override
			public void onFinished(MenigaBudget result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				setName(result.getName());
				setDescription(result.getDescription());
				setAccountIds(result.getAccountIds());
				setEntries(result.getEntries());
			}
		});
	}

	// TODO: This should really return an update budget, or at least update the instance

	/**
	 * Reset this budget
	 * @param categoryIds categories to reset
	 * @param resetManualEntries will manually made entries also be reset?
	 * @return Meniga taskd
	 */
	public Result<Void> reset(List<Long> categoryIds, boolean resetManualEntries) {
		return apiOperator.resetBudget(this.id, categoryIds, resetManualEntries);
	}

	/**
	 * Delete a budget by id
	 * @param id budget id of the budget to delete
	 * @return Meniga task
	 */
	public static Result<Void> deleteById(long id) {
		return apiOperator.deleteBudget(id);
	}

	// TODO: This should really return an update budget, or at least update the instance

	/**
	 * Reset a budget by id
	 * @param id of the budget to reset
	 * @param categoryIds categories to reset
	 * @param resetManualEntries will manually made entries also be reset?
	 * @return Meniga task
	 */

	public static Result<Void> resetById(long id, List<Long> categoryIds, boolean resetManualEntries) {
		return apiOperator.resetBudget(id, categoryIds, resetManualEntries);
	}
}
