package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.upcoming.MenigaUpcomingRecurringPattern;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateUpcoming extends QueryRequestObject {
	public String text;
	public MenigaDecimal amountInCurrency;
	public String currencyCode;
	public DateTime date;
	public Long accountId;
	public Long categoryId;
	public Boolean isFlagged;
	public Boolean isWatched;
	public MenigaUpcomingRecurringPattern recurringPattern;

	@Override
	public long getValueHash() {
		int result = text != null ? text.hashCode() : 0;
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
		result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (isWatched != null ? isWatched.hashCode() : 0);
		result = 31 * result + (recurringPattern != null ? recurringPattern.hashCode() : 0);
		return result;
	}
}
