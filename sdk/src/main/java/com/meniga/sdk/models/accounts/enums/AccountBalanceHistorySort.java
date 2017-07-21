package com.meniga.sdk.models.accounts.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum AccountBalanceHistorySort implements Serializable {
	@SerializedName("BalanceDate")
	DATE_ASCENDING,
	@SerializedName("-BalanceDate")
	DATE_DESCENDING,
	@SerializedName("Balance")
	BALANCE_ASCENDING,
	@SerializedName("-Balance")
	BALANCE_DESCENDING;

	@Override
	public String toString() {
		switch (this) {
			case DATE_ASCENDING:
				return "BalanceDate";
			case DATE_DESCENDING:
				return "-BalanceDate";
			case BALANCE_DESCENDING:
				return "-Balance";
			case BALANCE_ASCENDING:
			default:
				return "Balance";
		}
	}
}
