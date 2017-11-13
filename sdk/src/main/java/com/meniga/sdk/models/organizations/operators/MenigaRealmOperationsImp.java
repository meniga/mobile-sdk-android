package com.meniga.sdk.models.organizations.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.organizations.MenigaRealmAccount;
import com.meniga.sdk.models.organizations.MenigaRealmAuthResponse;
import com.meniga.sdk.models.organizations.MenigaRealmAuthParameter;
import com.meniga.sdk.webservices.requests.AddRealmAccountsToMeniga;
import com.meniga.sdk.webservices.requests.GetRealmAccounts;
import com.meniga.sdk.webservices.requests.GetRealmAuthMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRealmOperationsImp implements MenigaRealmOperations {

	@Override
	public Result<MenigaRealmAuthResponse> performBankAuthenticationStep(long id, List<MenigaRealmAuthParameter> authPars, String userId, String sessionToken) {
		GetRealmAuthMethod req = new GetRealmAuthMethod();
		req.id = id;
		if (authPars != null && authPars.size() > 0) {
			List<MenigaRealmAuthParameter.SimpleAuthParameter> pars = new ArrayList<>();
			for (MenigaRealmAuthParameter par : authPars) {
				pars.add(par.getSimpleParameter());
			}
			req.parameters = pars;
			req.saveDetails = true;
		}
		req.realmUserIdentifier = userId;
		req.sessionToken = sessionToken;
		return MenigaSDK.executor().performBankAuthenticationStep(req);
	}

	@Override
	public Result<List<MenigaRealmAccount>> getRealmAccounts(long realmUserId, String sessionToken) {
		GetRealmAccounts req = new GetRealmAccounts();
		req.realmUserId = realmUserId;
		req.sessionToken = sessionToken;

		return MenigaSDK.executor().getRealmAccounts(req);
	}

	@Override
	public Result<List<MenigaRealmAccount>> addRealmAccountsToMeniga(long realmUserId, List<MenigaRealmAccount> accounts, String sessionToken) {
		AddRealmAccountsToMeniga req = new AddRealmAccountsToMeniga();
		req.realmUserId = realmUserId;
		req.realmAccounts = accounts;
		req.sessionToken = sessionToken;

		return MenigaSDK.executor().addRealmAccountsToMeniga(req);
	}
}
