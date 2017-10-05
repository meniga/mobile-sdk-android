package com.meniga.sdk.models.budget.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum BudgetType implements Serializable {
	@SerializedName("Planning")
	PLANNING,
	@SerializedName("Budget")
	BUDGET;

	@Override
	public String toString() {
		switch (this) {
			case BUDGET:
				return "Budget";

			case PLANNING:
				return "Planning";

			default:
				return "Budget";
		}
	}
}
