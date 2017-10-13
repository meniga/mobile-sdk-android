package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeclineOffer extends QueryRequestObject {

	public long id;

	public DeclineOffer(long id) {
		this.id = id;
	}

	@Override
	public long getValueHash() {
		return (int) (id ^ (id >>> 32));
	}
}
