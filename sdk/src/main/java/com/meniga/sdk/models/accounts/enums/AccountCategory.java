package com.meniga.sdk.models.accounts.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum AccountCategory implements Serializable {
	@SerializedName("Wallet")
	WALLET("WALLET"),
	@SerializedName("Credit")
	CREDIT("CREDIT"),
	@SerializedName("Current")
	CURRENT("CURRENT"),
	@SerializedName("Savings")
	SAVINGS("SAVINGS"),
	@SerializedName("Manual")
	MANUAL("MANUAL"),
	@SerializedName("Loan")
	LOAN("LOAN"),
	@SerializedName("Asset")
	ASSET("ASSET"),
	@SerializedName("Unknown")
	UNKNOWN("UNKNOWN");

	private String accountCategory;

	AccountCategory(String accountType) {
		this.accountCategory = accountType.toUpperCase(Locale.getDefault());
	}

	@Override
	public String toString() {
		return this.accountCategory;
	}

	public static AccountCategory fromId(long id) {
		switch ((int) id) {
			case 1:
				return WALLET;

			case 37:
			case 46:
				return CREDIT;

			case 39:
				return CURRENT;

			case 41:
				return SAVINGS;

			case 42:
				return MANUAL;

			case 43:
				return LOAN;

			case 44:
				return ASSET;

			default:
				return UNKNOWN;
		}
	}
}
