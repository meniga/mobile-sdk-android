package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.organizations.MenigaRealmAccount;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AddRealmAccountsToMeniga extends QueryRequestObject {

	public transient long realmUserId;
	public List<MenigaRealmAccount> realmAccounts;

	@Override
	public long getValueHash() {
		return 0;
	}
}
