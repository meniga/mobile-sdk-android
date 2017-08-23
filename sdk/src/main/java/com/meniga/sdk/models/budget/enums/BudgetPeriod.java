package com.meniga.sdk.models.budget.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public enum BudgetPeriod {
	@SerializedName("week")
	WEEK,
	@SerializedName("month")
	MONTH,
	@SerializedName("quarter")
	QUARTER,
	@SerializedName("year")
	YEAR;

	@Override
	public String toString() {
		switch (this) {

			case WEEK:
				return "week";

			case MONTH:
				return "month";

			case QUARTER:
				return "quarter";

			default:
				return "budget";
		}
	}
}
