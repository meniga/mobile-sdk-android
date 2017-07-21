package com.meniga.sdk.models.transactions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum TimeResolution implements Serializable {
	@SerializedName("None")
	NONE,
	@SerializedName("Day")
	DAY,
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
			case NONE:
				return "NONE";
			case DAY:
				return "Day";
			case WEEK:
				return "Week";
			case MONTH:
				return "Month";
			case QUARTER:
				return "Quarter";
			case YEAR:
				return "Year";
			default:
				return "NONE";
		}
	}
}
