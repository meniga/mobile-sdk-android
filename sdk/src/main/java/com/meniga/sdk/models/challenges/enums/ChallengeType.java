package com.meniga.sdk.models.challenges.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum ChallengeType {
	@SerializedName("GlobalSpending")
	METER,
	@SerializedName("SuggestedSpending")
	SPENDING,
	@SerializedName("SuggestedSaving")
	SAVING,
	@SerializedName("CustomSpending")
	CUSTOM_SPENDING,
	@SerializedName("CustomSaving")
	CUSTOM_SAVING
}
