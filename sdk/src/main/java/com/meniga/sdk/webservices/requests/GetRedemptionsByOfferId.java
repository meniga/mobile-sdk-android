package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetRedemptionsByOfferId extends QueryRequestObject {

	public long offerId;

	public GetRedemptionsByOfferId(long offerId) {
		this.offerId = offerId;
	}

	@Override
	public long getValueHash() {
		return (int) (offerId ^ (offerId >>> 32));
	}
}
