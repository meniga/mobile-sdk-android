package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class StartSync extends QueryRequestObject {

	public long waitForCompleteMilliseconds;

	@Override
	public long getValueHash() {
		// Does not apply to this object
		return -1;
	}
}
