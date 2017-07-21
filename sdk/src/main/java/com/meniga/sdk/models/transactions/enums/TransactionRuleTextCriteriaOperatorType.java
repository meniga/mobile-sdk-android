package com.meniga.sdk.models.transactions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum TransactionRuleTextCriteriaOperatorType implements Serializable {
	@SerializedName("0")
	EQUAL_TO,
	@SerializedName("1")
	CONTAINS,
	@SerializedName("2")
	STARTS_WITH,
	@SerializedName("3")
	ENDS_WITH
}
