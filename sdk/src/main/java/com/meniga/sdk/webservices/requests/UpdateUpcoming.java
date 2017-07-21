package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.upcoming.MenigaUpcomingRecurringPattern;
import com.meniga.sdk.models.upcoming.enums.PaymentStatus;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateUpcoming extends QueryRequestObject {
	public transient long id;
	public String text;
	public MenigaDecimal amountInCurrency;
	public String currencyCode;
	public DateTime date;
	public PaymentStatus paymentStatus;
	public Long accountId;
	public Long categoryId;
	public Long transactionId;
	public Boolean isFlagged;
	public Boolean isWatched;
	public MenigaUpcomingRecurringPattern recurringPattern;

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		if (recurringPattern != null && recurringPattern.getId() != 0) {
			query.put("recurringPatternId", Long.toString(recurringPattern.getId()));
		}
		return query;
	}

	@Override
	public long getValueHash() {
		int result = text != null ? text.hashCode() : 0;
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
		result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
		result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
		result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (isWatched != null ? isWatched.hashCode() : 0);
		result = 31 * result + (recurringPattern != null ? recurringPattern.hashCode() : 0);
		return result;
	}
}
