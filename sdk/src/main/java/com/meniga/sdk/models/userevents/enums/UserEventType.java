package com.meniga.sdk.models.userevents.enums;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum UserEventType {
	@SerializedName("accounts")
	ACCOUNTS("accounts"),
	@SerializedName("transactions")
	TRANSACTIONS("transactions"),
	@SerializedName("scheduled_user_event")
	SCHEDULED_USER_EVENT("scheduled_user_event"),
	@SerializedName("cashback_offer")
	CASHBACK_OFFER("cashback_offer"),
	@SerializedName("dialog")
	DIALOG("dialog"),
	@SerializedName("challenge")
	CHALLENGE("challenge"),
	@SerializedName("accounts_available_amount")
	ACCOUNTS_AVAILABLE_AMOUNT("accounts_available_amount"),
	@SerializedName("scheduled_monthly_transaction_report")
	SCHEDULED_MONTHLY_TRANSACTION_REPORT("scheduled_monthly_transaction_report"),
	@SerializedName("scheduled_weekly_transaction_report")
	SCHEDULED_WEEKLY_TRANSACTION_REPORT("scheduled_weekly_transaction_report"),
	@SerializedName("transactions_merchant_count")
	TRANSACTIONS_MERCHANT_COUNT("transactions_merchant_count"),
	@SerializedName("transactions_category_budget_watch")
	TRANSACTIONS_CATEGORY_BUDGET_WATCH("transactions_category_budget_watch"),
	@SerializedName("transactions_threshold_deposit")
	TRANSACTIONS_THRESHOLD_DEPOSIT("transactions_threshold_deposit"),
	@SerializedName("transactions_threshold_expenses")
	TRANSACTIONS_THRESHOLD_EXPENSES("transactions_threshold_expenses"),
	@SerializedName("transactions_unusual_spending")
	TRANSACTIONS_UNUSUAL_SPENDING("transactions_unusual_spending"),
	@SerializedName("cashback_offer_redemption")
	CASHBACK_OFFER_REDEMPTION("cashback_offer_redemption"),
	@SerializedName("challenge_started")
	CHALLENGE_STARTED("challenge_started"),
	@SerializedName("challenge_progress")
	CHALLENGE_PROGRESS("challenge_progress"),
	@SerializedName("challenge_completed")
	CHALLENGE_COMPLETED("challenge_completed"),
	@SerializedName("cashback_offer_available")
	CASHBACK_OFFER_AVAILABLE("cashback_offer_available"),
	@SerializedName("cashback_offer_days_left")
	CASHBACK_OFFER_DAYS_LEFT("cashback_offer_days_left"),
	@SerializedName("cashback_repayment")
	CASHBACK_REPAYMENT("cashback_repayment"),
	@SerializedName("dialog_message")
	DIALOG_MESSAGE("dialog_message"),
	@SerializedName("life_goal_reached")
	LIFE_GOAL_REACHED("life_goal_reached"),
	@SerializedName("life_goal_progress")
	LIFE_GOAL_PROGRESS("life_goal_progress"),
	UNKNOWN("unknown");

	private String tag;

	UserEventType(String tagIn) {
		tag = tagIn;
	}

	@Override
	public String toString() {
		return tag;
	}

	public static UserEventType parse(String userEventTypeIdentifier) {
		for (UserEventType type : UserEventType.values()) {
			if (type.tag.toLowerCase().equals(userEventTypeIdentifier.toLowerCase())) {
				return type;
			}
		}
		UserEventType unknownType = UNKNOWN;
		unknownType.tag = userEventTypeIdentifier;
		return unknownType;
	}
}
