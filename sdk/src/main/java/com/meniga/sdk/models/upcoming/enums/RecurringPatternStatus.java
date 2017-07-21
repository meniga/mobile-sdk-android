package com.meniga.sdk.models.upcoming.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum RecurringPatternStatus {
	@SerializedName("Unknown")
	UNKNOWN,
	@SerializedName("Suggested")
	SUGGESTED,
	@SerializedName("Accepted")
	ACCEPTED,
	@SerializedName("Rejected")
	RECJECTED
}
