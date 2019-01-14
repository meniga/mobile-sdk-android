package com.meniga.sdk.models.transactions.enums;

import javax.annotation.Nonnull;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 14.1.2019.
 */
public enum TransactionSortField {
	ACCOUNT_ID("AccountId"),
	ACCURACY("Accuracy"),
	AMOUNT("Amount"),
	AMOUNT_IN_CURRENCY("AmountInCurrency"),
	BALANCE("Balance"),
	BANK_ID("BankId"),
	CATEGORY_CHANGED_TIME("CategoryChangedTime"),
	CATEGORY_ID("CategoryId"),
	DATE("Date"),
	DUE_DATE("DueDate"),
	HAS_USER_CLEARED_CATEGORY_UNCERTAINTY("HasUserClearedCategoryUncertainty"),
	ID("Id"),
	IS_FLAGGED("IsFlagged"),
	IS_MERCHANT("IsMerchant"),
	IS_OWN_ACCOUNT_TRANSFER("IsOwnAccountTransfer"),
	IS_READ("IsRead"),
	IS_SPLIT_CHILD("IsSplitChild"),
	IS_UNCLEARED("IsUncleared"),
	INSERT_TIME("InsertTime"),
	MCC("Mcc"),
	MERCHANT_ID("MerchantId"),
	PARENT_IDENTIFIER("ParentIdentifier"),
	TEXT("Text"),
	TIMESTAMP("Timestamp"),
	COUNTER_PARTY_ACCOUNT_IDENTIFIER("CounterpartyAccountIdentifier"),
	CURRENCY("Currency"),
	HAS_UNCERTAIN_CATEGORIZATION("HasUncertainCategorization"),
	ORIGINAL_AMOUNT("OriginalAmount"),
	ORIGINAL_DATE("OriginalDate"),
	ORIGINAL_TEXT("OriginalText"),

	UNKNOWN("UNKNOWN");

	private final String sort;

	TransactionSortField(@Nonnull String sort) {
		this.sort = sort;
	}

	@Nonnull
	@Override
	public String toString() {
		return sort;
	}

	public static TransactionSortField parse(String field) {
		for (TransactionSortField value : TransactionSortField.values()) {
			if (field.toLowerCase().equals(value.sort.toLowerCase())) {
				return value;
			}
		}
		return UNKNOWN;
	}
}
