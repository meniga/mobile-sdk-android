package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTag;
import com.meniga.sdk.webservices.requests.GetTag;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public final class MenigaTagOperationsImp implements MenigaTagOperations {

	@Override
	public Result<List<MenigaTag>> getTags() {
		return MenigaSDK.executor().getTags();
	}

	@Override
	public Result<MenigaTag> getTag(long id) {
		GetTag req = new GetTag();
		req.id = id;

		return MenigaSDK.executor().getTag(req);
	}
}
