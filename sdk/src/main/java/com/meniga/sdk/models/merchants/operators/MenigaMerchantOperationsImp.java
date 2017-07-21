package com.meniga.sdk.models.merchants.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.merchants.MenigaMerchant;
import com.meniga.sdk.webservices.requests.GetMerchant;
import com.meniga.sdk.webservices.requests.GetMerchants;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaMerchantOperationsImp implements MenigaMerchantOperations {
	@Override
	public Result<MenigaMerchant> getMerchant(long id) {
		GetMerchant req = new GetMerchant();
		req.id = id;
		return MenigaSDK.executor().getMerchant(req);
	}

	@Override
	public Result<List<MenigaMerchant>> getMerchants(List<Long> ids) {
		GetMerchants req = new GetMerchants();
		req.ids = ids;
		return MenigaSDK.executor().getMerchants(req);
	}
}
