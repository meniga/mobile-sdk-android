package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.transactions.Options;
import com.meniga.sdk.models.transactions.SeriesSelector;
import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTransactionSeries extends QueryRequestObject {

	public Options options;
	public TransactionsFilter transactionFilter;
	public List<SeriesSelector> seriesSelectors;

	@Override
	public long getValueHash() {
		int result = options != null ? options.hashCode() : 0;
		result = 31 * result + (transactionFilter != null ? transactionFilter.hashCode() : 0);
		result = 31 * result + (seriesSelectors != null ? seriesSelectors.hashCode() : 0);
		return result;
	}
}
