package com.meniga.sdk.models.accounts.enums;

import com.meniga.sdk.models.accounts.MenigaAccount;

import java.io.Serializable;
import java.util.Locale;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum AccountCategory implements Serializable {
	WALLET("WALLET"),
	CREDIT("CREDIT"),
	CURRENT("CURRENT"),
	SAVINGS("SAVINGS"),
	MANUAL("MANUAL"),
	LOAN("LOAN"),
	ASSET("ASSET"),
	UNKNOWN("UNKNOWN");

	private String accountCategory;

	AccountCategory(String accountType) {
		this.accountCategory = accountType.toUpperCase(Locale.getDefault());
	}

	@Override
	public String toString() {
		return this.accountCategory;
	}

	/**
	 * This method may not be reliable. Use {@link MenigaAccount#fetchAccountTypes()} and then call {@link com.meniga.sdk.models.accounts.MenigaAccountType#accountCategory}.
	 *
	 * @deprecated Will be removed in 1.2
	 */
	@Deprecated
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
