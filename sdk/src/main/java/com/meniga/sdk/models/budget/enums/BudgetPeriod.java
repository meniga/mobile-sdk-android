package com.meniga.sdk.models.budget.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public enum BudgetPeriod {
	@SerializedName("Week")
	WEEK,
	@SerializedName("Month")
	MONTH,
	@SerializedName("Quarter")
	QUARTER,
	@SerializedName("Year")
	YEAR;

	@Override
	public String toString() {
		switch (this) {
			case WEEK:
				return "Week";

			case MONTH:
				return "Month";

			case QUARTER:
				return "Quarter";

			case YEAR:
				return "Year";

			default:
				throw new IllegalStateException("Unhandled period value: " + name());
		}
	}
}
