package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTerms extends QueryRequestObject {
	public transient String culture;

    @Override
	public long getValueHash() {
		return this.getClass().toString().hashCode();
	}
}
