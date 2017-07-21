package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.transactions.Options;
import com.meniga.sdk.models.transactions.SeriesSelector;
import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaTransactionSeriesOperations {
	Result<List<MenigaTransactionSeries>> getTransactionSeries(TransactionsFilter tf, Options options, List<SeriesSelector> ss);
}
