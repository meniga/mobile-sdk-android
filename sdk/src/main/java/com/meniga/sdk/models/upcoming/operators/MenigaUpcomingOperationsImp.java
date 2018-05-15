package com.meniga.sdk.models.upcoming.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.upcoming.MenigaUpcoming;
import com.meniga.sdk.models.upcoming.MenigaUpcomingRecurringPattern;
import com.meniga.sdk.webservices.requests.CreateUpcoming;
import com.meniga.sdk.webservices.requests.DeleteUpcoming;
import com.meniga.sdk.webservices.requests.DeleteUpcomingSeries;
import com.meniga.sdk.webservices.requests.GetUpcoming;
import com.meniga.sdk.webservices.requests.GetUpcomingById;
import com.meniga.sdk.webservices.requests.UpdateUpcoming;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingOperationsImp implements MenigaUpcomingOperations {

	@Override
	public Result<List<MenigaUpcoming>> getUpcoming(DateTime from, DateTime to) {
		GetUpcoming req = new GetUpcoming();
		req.from = from;
		req.to = to;

		return MenigaSDK.executor().getUpcoming(req);
	}

	@Override
	public Result<MenigaUpcoming> getUpcoming(long id) {
		GetUpcomingById req = new GetUpcomingById();
		req.id = id;

		return MenigaSDK.executor().getUpcoming(req);
	}

	@Override
	public Result<List<MenigaUpcoming>> createUpcoming(String text, MenigaDecimal amountInCurrency,
	                                                   String currencyCode, DateTime date, Long accountId,
	                                                   Long categoryId, Boolean isFlagged, Boolean isWatched,
	                                                   MenigaUpcomingRecurringPattern recurringPattern) {
		CreateUpcoming req = new CreateUpcoming();
		req.text = text;
		req.amountInCurrency = amountInCurrency;
		req.currencyCode = currencyCode;
		req.date = date;
		req.accountId = accountId;
		req.categoryId = categoryId;
		req.isFlagged = isFlagged;
		req.isWatched = isWatched;
		req.recurringPattern = recurringPattern;

		return MenigaSDK.executor().createUpcoming(req);
	}

	@Override
	public Result<Void> updateUpcoming(MenigaUpcoming update, boolean updateWholeSeries) {
		UpdateUpcoming req = new UpdateUpcoming();
		req.id = update.getId();
		req.text = update.getText();
		req.amountInCurrency = update.getAmountInCurrency();
		req.currencyCode = update.getCurrencyCode();
		req.date = update.getDate();
		req.paymentStatus = update.getPaymentStatus();
		req.accountId = update.getAccountId();
		req.categoryId = update.getCategoryId();
		req.transactionId = update.getTransactionId();
		req.isFlagged = update.getFlagged();
		req.isWatched = update.getWatched();
		if (updateWholeSeries) {
			req.recurringPattern = update.getRecurringPattern();
		}
		if (update.getRecurringPattern() != null) {
			req.repeatUntil = update.getRecurringPattern().getRepeatUntil();
			req.status = update.getRecurringPattern().getStatus();
		}

		return MenigaSDK.executor().updateUpcoming(req);
	}

	@Override
	public Result<Void> deleteUpcoming(MenigaUpcoming item, boolean deleteSeries) {
		if (deleteSeries) {
			DeleteUpcomingSeries req = new DeleteUpcomingSeries();
			req.id = item.getRecurringPattern().getId();
			return MenigaSDK.executor().deleteUpcomingSeries(req);
		} else {
			DeleteUpcoming req = new DeleteUpcoming();
			req.id = item.getId();
			return MenigaSDK.executor().deleteUpcoming(req);
		}
	}
}
