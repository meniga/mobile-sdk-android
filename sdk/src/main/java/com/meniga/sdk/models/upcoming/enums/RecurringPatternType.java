package com.meniga.sdk.models.upcoming.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum RecurringPatternType {
	@SerializedName("Unknown")
	UNKNOWN,
	@SerializedName("Detected")
	DETECTED,
	@SerializedName("Manual")
	MANUAL
}
