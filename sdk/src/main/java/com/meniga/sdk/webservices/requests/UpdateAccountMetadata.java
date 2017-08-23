package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateAccountMetadata extends QueryRequestObject {

	public transient long id;
	public String key;
	public String value;

	@Override
	public long getValueHash() {
		return -1;
	}
}
