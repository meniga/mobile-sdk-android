package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.merchants.TopMerchantOptions;
import com.meniga.sdk.models.transactions.TransactionsFilter;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetTopMerchants extends QueryRequestObject  {

	public TransactionsFilter transactionFilter;
	public TopMerchantOptions options;

	@Override
	public long getValueHash() {
		int result = transactionFilter != null ? transactionFilter.hashCode() : 0;
		result = 31 * result + (options != null ? options.hashCode() : 0);
		return result;
	}
}
