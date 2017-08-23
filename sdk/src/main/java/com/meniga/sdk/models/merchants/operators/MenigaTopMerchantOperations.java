package com.meniga.sdk.models.merchants.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.merchants.MenigaTopMerchant;
import com.meniga.sdk.models.merchants.TopMerchantOptions;
import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaTopMerchantOperations {

	Result<List<MenigaTopMerchant>> getTopMerchants(TransactionsFilter filter, TopMerchantOptions options);
}
