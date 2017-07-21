package com.meniga.sdk.models.offers.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum RewardType implements Serializable {

	@SerializedName("Unknown")
	UNKNOWN,
	@SerializedName("Percentage")
	PERCENTAGE,
	@SerializedName("FixedAmount")
	FIXEDAMOUNT;

	@Override
	public String toString() {
		if (this == RewardType.PERCENTAGE)
			return "percentage";
		else if (this == RewardType.FIXEDAMOUNT)
			return "fixed_amount";
		else
			return "unknown";
	}

}

