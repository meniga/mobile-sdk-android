package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTags extends QueryRequestObject {

	@Override
	public long getValueHash() {
		return this.getClass().toString().hashCode();
	}
}
