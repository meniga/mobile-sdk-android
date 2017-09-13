package com.meniga.sdk.models.terms.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.terms.MenigaTermType;
import com.meniga.sdk.models.terms.MenigaTerms;
import com.meniga.sdk.webservices.requests.AcceptTerms;
import com.meniga.sdk.webservices.requests.DeclineTerms;
import com.meniga.sdk.webservices.requests.GetTermTypes;
import com.meniga.sdk.webservices.requests.GetTerms;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTermsOperationsImp implements MenigaTermsOperations {
	@Override
	public Result<List<MenigaTerms>> getTerms(String culture) {
		GetTerms req = new GetTerms();
		req.culture = culture;
		return MenigaSDK.executor().getTerms(req);
	}

	@Override
	public Result<List<MenigaTermType>> getTermTypes(String culture) {
		GetTermTypes req = new GetTermTypes();
		req.culture = culture;
		return MenigaSDK.executor().getTermTypes(req);
	}

	@Override
	public Result<Void> acceptTerms(long typeId) {
		AcceptTerms req = new AcceptTerms();
		req.typeId = typeId;
		return MenigaSDK.executor().acceptTerms(req);
	}

	@Override
	public Result<Void> declineTerms(long typeId) {
		DeclineTerms req = new DeclineTerms();
		req.typeId = typeId;
		return MenigaSDK.executor().declineTerms(req);
	}
}
