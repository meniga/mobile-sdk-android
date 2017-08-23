package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetRealmAccounts extends QueryRequestObject {

	public long realmUserId;

	@Override
	public long getValueHash() {
		return realmUserId;
	}
}
