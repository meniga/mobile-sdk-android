package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetAccountMetadataKeyVal extends QueryRequestObject {

	public long id;
	public String name;

	@Override
	public long getValueHash() {
		return this.id;
	}
}
