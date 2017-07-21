package com.meniga.sdk.models.offers.redemptions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.webservices.requests.GetRedemptions;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaRedemptionsOperationsImp implements MenigaRedemptionsOperations {
	@Override
	public Result<MenigaRedemptions> getRedemptions(Integer skip, Integer take, DateTime dateFrom, DateTime dateTo) {
		GetRedemptions req = new GetRedemptions();
		req.skip = skip;
		req.take = take;
		req.dateFrom = dateFrom;
		req.dateTo = dateTo;
		return MenigaSDK.executor().getRedemptions(req);
	}
}
