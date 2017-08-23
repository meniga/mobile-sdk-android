package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteNetWorthBalance extends QueryRequestObject {

	public long accountId;
	public long id;

	@Override
	public long getValueHash() {
		return this.id;
	}
}
