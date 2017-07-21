package com.meniga.sdk.models.merchants.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.merchants.MenigaTopMerchant;
import com.meniga.sdk.models.merchants.TopMerchantOptions;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.webservices.requests.GetTopMerchants;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTopMerchantOperationsImp implements MenigaTopMerchantOperations {

	@Override
	public Result<List<MenigaTopMerchant>> getTopMerchants(TransactionsFilter filter, TopMerchantOptions options) {
		GetTopMerchants req = new GetTopMerchants();
		req.transactionFilter = filter;
		req.options = options;
		return MenigaSDK.executor().getTopMerchants(req);
	}
}
