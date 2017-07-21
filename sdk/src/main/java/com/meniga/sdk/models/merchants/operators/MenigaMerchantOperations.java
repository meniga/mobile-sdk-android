package com.meniga.sdk.models.merchants.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.merchants.MenigaMerchant;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaMerchantOperations {
	Result<MenigaMerchant> getMerchant(long id);

	Result<List<MenigaMerchant>> getMerchants(List<Long> id);
}
