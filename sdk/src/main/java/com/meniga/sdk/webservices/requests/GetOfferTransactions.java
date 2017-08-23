package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetOfferTransactions extends QueryRequestObject {

	public long id;

	public GetOfferTransactions(long id) {
		this.id = id;
	}

	@Override
	public long getValueHash() {
		return id;
	}
}
