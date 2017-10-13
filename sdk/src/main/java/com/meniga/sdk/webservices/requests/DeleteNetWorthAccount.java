package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteNetWorthAccount extends QueryRequestObject {

	public long id;

	public DeleteNetWorthAccount(long transactionId) {
		this.id = transactionId;
	}

	@Override
	public long getValueHash() {
		return (int) (id ^ (id >>> 32));
	}
}
