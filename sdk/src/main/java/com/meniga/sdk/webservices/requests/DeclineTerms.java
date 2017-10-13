package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeclineTerms extends QueryRequestObject {

	public long typeId;

	@Override
	public long getValueHash() {
		return (int) (typeId ^ (typeId >>> 32));
	}
}
