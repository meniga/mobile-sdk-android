package com.meniga.sdk.models.upcoming.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.upcoming.MenigaUpcoming;
import com.meniga.sdk.models.upcoming.MenigaUpcomingRecurringPattern;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaUpcomingOperations {

	Result<List<MenigaUpcoming>> getUpcoming(DateTime from, DateTime to);

	Result<MenigaUpcoming> getUpcoming(long id);

	Result<List<MenigaUpcoming>> createUpcoming(String text, MenigaDecimal amountInCurrency, String currencyCode,
	                                            DateTime date, Long accountId, Long categoryId, Boolean isFlagged,
	                                            Boolean isWatched, MenigaUpcomingRecurringPattern recurringPattern);

	Result<Void> updateUpcoming(MenigaUpcoming update, boolean updateWholeSeries);

	Result<Void> deleteUpcoming(MenigaUpcoming item, boolean deleteSeries);
}
