package com.meniga.sdk.models.categories;

import android.os.Parcel;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.categories.enums.CategoryType;

import java.io.Serializable;

/**
 * Represents a user created category, directly inherits from MenigaCategory.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUserCategory extends MenigaCategory implements Serializable {

	public static final Creator<MenigaUserCategory> CREATOR = new Creator<MenigaUserCategory>() {
		public MenigaUserCategory createFromParcel(Parcel source) {
			return new MenigaUserCategory(source);
		}

		public MenigaUserCategory[] newArray(int size) {
			return new MenigaUserCategory[size];
		}
	};

	public MenigaUserCategory(MenigaCategory toCopy) {
		super();
		this.id = toCopy.id;
		this.name = toCopy.name;
		this.otherCategoryName = toCopy.otherCategoryName;
		this.categoryDisplay = toCopy.categoryDisplay;
		this.parentCategoryId = toCopy.parentCategoryId;
		this.isPublic = true;
		this.isFixedExpenses = toCopy.isFixedExpenses;
		this.categoryType = toCopy.categoryType;
		this.categoryRank = toCopy.categoryRank;
		this.budgetGenerationType = toCopy.budgetGenerationType;
		this.children = toCopy.children;
		this.parent = toCopy.parent;
		this.categoryContextId = toCopy.categoryContextId;
		this.orderId = toCopy.orderId;
		this.displayData = toCopy.displayData;
		this.fixedIcon = toCopy.fixedIcon;
	}

	protected MenigaUserCategory(Parcel in) {
		super(in);
	}

	/**
	 * Sets a new name for this user category
	 *
	 * @param name The new name of this user category
	 */
	public void setName(String name) {
		if (this.hasChanged(super.name, name)) {
			return;
		}
		this.changed();
		super.name = name;
	}

	/**
	 * Sets if this user category is fixed expenses or not
	 *
	 * @param isFixedExpenses Wheather this user category is fixed expenses or not
	 */
	public void setIsFixedExpenses(boolean isFixedExpenses) {
		if (this.hasChanged(super.isFixedExpenses, isFixedExpenses)) {
			return;
		}
		this.changed();
		super.isFixedExpenses = isFixedExpenses;
	}

	/**
	 * Sets the type of this user category (EXPENSES, INCOME, SAVINGS, EXCLUDED)
	 *
	 * @param categoryType The new type of this user category
	 */
	public void setCategoryType(CategoryType categoryType) {
		if (!this.hasChanged(super.categoryType, categoryType)) {
			return;
		}
		this.changed();
		super.categoryType = categoryType;
	}

    /*
    * API Calls
     */

	/**
	 * Set a new parent category id for this child user category
	 *
	 * @param parentCategoryId The new parent category id of this child user category
	 */
	public void setParentCategoryId(long parentCategoryId) {
		if (this.hasChanged(super.parentCategoryId, parentCategoryId)) {
			return;
		}
		this.changed();
		super.parentCategoryId = parentCategoryId;
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaCategory)) {
			return;
		}

		// Revert all settable fields to last revision
		MenigaCategory prevRevision = (MenigaCategory) lastRevision;
		super.name = prevRevision.name;
		super.isFixedExpenses = prevRevision.isFixedExpenses;
		super.categoryType = prevRevision.categoryType;
		super.parentCategoryId = prevRevision.parentCategoryId;
	}

	/*
	 * API operations below
	 */

	/**
	 * Delets this user created category
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return apiOperator.deleteUserCategory(this.id);
	}

	/**
	 * Updates this user category, fields that have setters can be updated
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> update() {
		return apiOperator.updateUserCategory(this.id, this.name, this.isFixedExpenses, this.categoryType, this.parentCategoryId);
	}

	/**
	 * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
	 *
	 * @return A Task of type void, the task will have an error and be marked as failed if it is not successful
	 */
	public Result<MenigaCategory> refresh() {
		Result<MenigaCategory> task = fetch(this.id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaCategory>() {
			@Override
			public void onFinished(MenigaCategory result, boolean failed) {
				if (!failed) {
					try {
						Merge.merge(MenigaUserCategory.this, result);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Creates a new category for a user under another category
	 *
	 * @param name            The name of the new user category
	 * @param isFixedExpenses Whether the new user category is a fixed expense or not
	 * @param parentId        The id of the parent category under which this user category will be positioned
	 * @return The new user category that is a child category of another root category
	 */
	public static Result<MenigaUserCategory> create(String name, boolean isFixedExpenses, long parentId) {
		return apiOperator.createUserCategory(name, isFixedExpenses, CategoryType.EXPENSES, parentId);
	}

	/**
	 * Creates a new category for a user that is a root category
	 *
	 * @param name            The name of the new user category
	 * @param isFixedExpenses Whether the new user category is a fixed expense or not
	 * @param type            The type of the new category, income, expenses, savings or excluded
	 * @return The new user category that is a root category
	 */
	public static Result<MenigaUserCategory> create(String name, boolean isFixedExpenses, CategoryType type) {
		return apiOperator.createUserCategory(name, isFixedExpenses, type, null);
	}
}
