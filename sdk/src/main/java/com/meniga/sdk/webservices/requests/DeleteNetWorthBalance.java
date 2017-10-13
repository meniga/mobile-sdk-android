package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteNetWorthBalance extends QueryRequestObject {

	public long accountId;
	public long id;

	@Override
	public long getValueHash() {
		int result = (int) (accountId ^ (accountId >>> 32));
		result = 31 * result + (int) (id ^ (id >>> 32));
		return result;
	}
}
