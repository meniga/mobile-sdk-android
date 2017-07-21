package com.meniga.sdk.models.terms.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.terms.MenigaTerms;
import com.meniga.sdk.webservices.requests.GetTerms;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTermsOperationsImp implements MenigaTermsOperations {

	@Override
	public Result<List<MenigaTerms>> getTerms() {
		return MenigaSDK.executor().getTerms(new GetTerms());
	}

}
