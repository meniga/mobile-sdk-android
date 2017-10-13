package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ActivateOfferByToken extends QueryRequestObject {

	public String validationToken;

	public ActivateOfferByToken(String validationToken) {
		this.validationToken = validationToken;
	}

	@Override
	public long getValueHash() {
		return validationToken != null ? validationToken.hashCode() : 0;
	}

}
