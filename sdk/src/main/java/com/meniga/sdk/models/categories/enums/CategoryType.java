package com.meniga.sdk.models.categories.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Enum that represents if a category is a income or an expense.
 */
public enum CategoryType implements Serializable {
	@SerializedName("Expenses")
	EXPENSES,
	@SerializedName("Income")
	INCOME,
	@SerializedName("Savings")
	SAVINGS,
	@SerializedName("Excluded")
	EXCLUDED;

	@Override
	public String toString() {
		switch (this) {
			case EXCLUDED:
				return "Excluded";
			case INCOME:
				return "Income";
			case SAVINGS:
				return "Savings";
			default:
				return "Expenses";
		}
	}
}
