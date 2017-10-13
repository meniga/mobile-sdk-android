package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetSimilarBrandSpendingDetails extends QueryRequestObject {

	public long id;

	public GetSimilarBrandSpendingDetails(long id) {
		this.id = id;
	}

	@Override
	public long getValueHash() {
		return (int) (id ^ (id >>> 32));
	}
}
