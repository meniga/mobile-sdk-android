package com.meniga.sdk.models.transactions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum AmountLimitTypeCriteria implements Serializable {
	@SerializedName("0")
	AMOUNT_UNDER,
	@SerializedName("1")
	AMOUNT_OVER,
	@SerializedName("2")
	AMOUNT_EQUALS
}
