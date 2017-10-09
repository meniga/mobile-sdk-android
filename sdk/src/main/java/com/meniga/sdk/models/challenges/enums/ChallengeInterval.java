package com.meniga.sdk.models.challenges.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public enum ChallengeInterval implements Serializable {
	@SerializedName("Weekly")
	WEEKLY,
	@SerializedName("Monthly")
	MONTHLY,
	@SerializedName("Yearly")
	YEARLY
}
