package com.meniga.sdk.models.transactions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum DateMatchTypeCriteria implements Serializable {
	@SerializedName("0")
	FIRST_DAYSLIMITCRITERIA_DAYS_IN_A_MONTH,
	@SerializedName("1")
	LAST_DAYSLIMITCRITERIA_DAYS_IN_A_MONTH
}
