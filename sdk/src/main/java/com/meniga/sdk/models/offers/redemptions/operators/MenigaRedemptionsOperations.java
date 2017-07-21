package com.meniga.sdk.models.offers.redemptions.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaRedemptionsOperations {
	Result<MenigaRedemptions> getRedemptions(Integer skip, Integer take, DateTime dateFrom, DateTime dateTo);
}
