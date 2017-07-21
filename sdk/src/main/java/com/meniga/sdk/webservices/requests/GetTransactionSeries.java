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
		int optionsHash = options != null ? options.hashCode() : 0;
		int transactionFilterHash = transactionFilter != null ? transactionFilter.hashCode() : 0;
		int seriesSelectorsHash = 0;
		for (SeriesSelector selector : seriesSelectors) {
			seriesSelectorsHash += selector.hashCode();
		}

		int result = optionsHash;
		result = 31 * result + (transactionFilterHash);
		result = 31 * result + (seriesSelectorsHash);
		return result;
	}
}
