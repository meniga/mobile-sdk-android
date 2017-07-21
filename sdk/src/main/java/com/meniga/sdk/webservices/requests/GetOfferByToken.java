package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetOfferByToken extends QueryRequestObject {

	public String token;

	public GetOfferByToken(String token) {
		this.token = token;
	}

	@Override
	public long getValueHash() {
		return token.hashCode();
	}
}
