package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetOfferById extends QueryRequestObject {

	public long id;

	public GetOfferById(long id) {
		this.id = id;
	}

	@Override
	public long getValueHash() {
		return this.id;
	}
}
