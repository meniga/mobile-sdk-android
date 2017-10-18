package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTerm extends QueryRequestObject {

	public long typeId;
	public transient String culture;

	@Override
	public long getValueHash() {
		int result = (int) (typeId ^ (typeId >>> 32));
		result = 31 * result + (culture != null ? culture.hashCode() : 0);
		return result;
	}
}
