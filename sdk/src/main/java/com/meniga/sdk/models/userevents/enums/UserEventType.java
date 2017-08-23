package com.meniga.sdk.models.userevents.enums;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum UserEventType {
	@SerializedName("accounts")
	ACCOUNTS,
	@SerializedName("transactions")
	TRANSACTIONS,
	@SerializedName("scheduled_user_event")
	SCHEDULED_USER_EVENT,
	@SerializedName("cashback_offer")
	CASHBACK_OFFER,
	@SerializedName("dialog")
	DIALOG,
	@SerializedName("challenge")
	CHALLENGE,
	@SerializedName("accounts_available_amount")
	ACCOUNTS_AVAILABLE_AMOUNT,
	@SerializedName("scheduled_monthly_transaction_report")
	SCHEDULED_MONTHLY_TRANSACTION_REPORT,
	@SerializedName("scheduled_weekly_transaction_report")
	SCHEDULED_WEEKLY_TRANSACTION_REPORT,
	@SerializedName("transactions_merchant_count")
	TRANSACTIONS_MERCHANT_COUNT,
	@SerializedName("transactions_category_budget_watch")
	TRANSACTIONS_CATEGORY_BUDGET_WATCH,
	@SerializedName("transactions_threshold_deposit")
	TRANSACTIONS_THRESHOLD_DEPOSIT,
	@SerializedName("transactions_threshold_expenses")
	TRANSACTIONS_THRESHOLD_EXPENSES,
	@SerializedName("transactions_unusual_spending")
	TRANSACTIONS_UNUSUAL_SPENDING,
	@SerializedName("cashback_offer_redemption")
	CASHBACK_OFFER_REDEMPTION,
	@SerializedName("challenge_started")
	CHALLENGE_STARTED,
	@SerializedName("challenge_progress")
	CHALLENGE_PROGRESS,
	@SerializedName("challenge_completed")
	CHALLENGE_COMPLETED,
	@SerializedName("cashback_offer_available")
	CASHBACK_OFFER_AVAILABLE,
	@SerializedName("cashback_offer_days_left")
	CASHBACK_OFFER_DAYS_LEFT,
	@SerializedName("cashback_repayment")
	CASHBACK_REPAYMENT,
	@SerializedName("dialog_message")
	DIALOG_MESSAGE,
	UNKNOWN;

	@Override
	public String toString() {
		return super.toString().toLowerCase(Locale.getDefault());
	}
}
