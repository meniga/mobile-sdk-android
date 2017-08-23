package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
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
	protected boolean isDefault;
	protected BudgetPeriod period;
	protected int offset;
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

	/*
	* API Calls
	*/

	/**
	 * Fetches a list of budgets available to the user
	 *
	 * @param ids        list of budget ids
	 * @param accountIds list of account ids
	 * @param budgetType type of budget
	 * @return list of budgets without entries
	 */
	public static Result<List<MenigaBudget>> fetch(List<Long> ids, List<Long> accountIds, BudgetType budgetType) {
		return apiOperator.getBudgets(ids, accountIds, budgetType);
	}

	/**
	 * Fetches a list of budgets available to the user
	 *
	 * @param id     list of budget ids
	 * @param filter BudgetFilter
	 * @return list of budgets without entries
	 */
	public static Result<MenigaBudget> fetch(Long id, BudgetFilter filter) {
		return apiOperator.getBudgetById(id, filter);
	}

	/**
	 * Create a single instance of budget
	 *
	 * @param type        type of budget
	 * @param name        name of budget
	 * @param description description of budget
	 * @param accountIds  lisdt of account ids relevant to the budget
	 * @param isDefault   if this instance is the default budget for the user
	 * @param period      what kinds of period this budget applies to
	 * @param offset      integer offset on how the period of this budget is calculated
	 * @return a instance of budget
	 */
	public static Result<MenigaBudget> create(BudgetType type, String name, String description, List<Long> accountIds, boolean isDefault, BudgetPeriod period, int offset) {
		return apiOperator.createBudget(type, name, description, accountIds, isDefault, period, offset);
	}

	/**
	 * Deletes the budget
	 *
	 * @return Void
	 */
	public static Result<Void> delete(long id) {
		return apiOperator.deleteBudget(id);
	}

	/**
	 * Deletes the budget
	 *
	 * @return Void
	 */
	public Result<Void> delete() {
		return apiOperator.deleteBudget(this.id);
	}

	/**
	 * Updates the budgets name, description, accountids, default and offset
	 *
	 * @return Void
	 */
	public Result<MenigaBudget> update() {
		return apiOperator.updateBudget(this.id, this.name, this.description, this.accountIds, this.isDefault, this.offset);
	}

	/**
	 * Causes the budget to reset (remove all entries and fall back to default generated entries)
	 *
	 * @return Void
	 */
	public static Result<Void> reset(long id) {
		return apiOperator.reset(id);
	}

	/**
	 * Causes the budget to reset (remove all entries and fall back to default generated entries)
	 *
	 * @return Void
	 */
	public Result<Void> reset() {
		return apiOperator.reset(this.id);
	}

	/**
	 * Fetches the entries for this budget
	 *
	 * @return list of budget entries
	 */
	public Result<List<MenigaBudgetEntry>> fetchEntries(BudgetFilter filter) {
		return apiOperator.getBudgetEntries(this.id, filter);
	}

	/**
	 * Create entries applies to this budget
	 *
	 * @param entries list of budget entries
	 * @return Void
	 */
	public Result<List<MenigaBudgetEntry>> createEntries(List<MenigaBudgetEntry> entries) {
		return apiOperator.createBudgetEntries(this.id, entries);
	}


	/**
	 * Deletes entry
	 *
	 * @param entryId id of entry
	 * @return Void
	 */
	public Result<Void> deleteEntry(long entryId) {
		return apiOperator.deleteBudgetEntry(this.id, entryId);
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
		dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
		dest.writeInt(this.period == null ? -1 : this.period.ordinal());
		dest.writeInt(this.offset);
		dest.writeTypedList(this.entries);
	}

	protected MenigaBudget(Parcel in) {
		this.id = in.readLong();
		int tmpType = in.readInt();
		this.type = tmpType == -1 ? null : BudgetType.values()[tmpType];
		this.name = in.readString();
		this.description = in.readString();
		this.accountIds = new ArrayList<Long>();
		in.readList(this.accountIds, Long.class.getClassLoader());
		this.created = (DateTime) in.readSerializable();
		this.isDefault = in.readByte() != 0;
		int tmpPeriod = in.readInt();
		this.period = tmpPeriod == -1 ? null : BudgetPeriod.values()[tmpPeriod];
		this.offset = in.readInt();
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
}
