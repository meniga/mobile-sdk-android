package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTermTypes extends QueryRequestObject {

	public transient String culture;

	@Override
	public long getValueHash() {
		return culture != null ? culture.hashCode() : 0;
	}
}
