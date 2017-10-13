package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetSync extends QueryRequestObject {

	public long syncHistoryId;

	@Override
	public long getValueHash() {
		return (int) (syncHistoryId ^ (syncHistoryId >>> 32));
	}
}
