package com.meniga.sdk.models.transactions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum AmountLimitSignCriteria implements Serializable {
	@SerializedName("0")
	AMOUNT_INCOME_OR_EXPENSE,
	@SerializedName("1")
	AMOUNT_EXPENSE,
	@SerializedName("2")
	AMOUNT_INCOME


}
