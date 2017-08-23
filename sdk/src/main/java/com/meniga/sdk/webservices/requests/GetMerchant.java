package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetMerchant extends QueryRequestObject {

	public long id;

	@Override
	public long getValueHash() {
		return this.id;
	}
}
