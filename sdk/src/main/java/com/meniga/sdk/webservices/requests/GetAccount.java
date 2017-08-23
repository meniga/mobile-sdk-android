package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetAccount extends QueryRequestObject {

	public long accountId;

	@Override
	public long getValueHash() {
		return this.accountId;
	}
}
