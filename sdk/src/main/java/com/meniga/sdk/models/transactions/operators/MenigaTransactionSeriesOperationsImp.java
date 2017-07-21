package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.transactions.Options;
import com.meniga.sdk.models.transactions.SeriesSelector;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.webservices.requests.GetTransactionSeries;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionSeriesOperationsImp implements MenigaTransactionSeriesOperations {
	@Override
	public Result<List<MenigaTransactionSeries>> getTransactionSeries(TransactionsFilter tf, Options options, List<SeriesSelector> ss) {
		GetTransactionSeries request = new GetTransactionSeries();
		request.seriesSelectors = ss;
		request.options = options;
		request.transactionFilter = tf;

		return MenigaSDK.executor().getTransactionSeries(request);
	}
}
