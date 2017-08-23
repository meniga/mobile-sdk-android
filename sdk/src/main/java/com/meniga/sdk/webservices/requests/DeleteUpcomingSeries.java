package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteUpcomingSeries extends QueryRequestObject {

	public transient long id;

	@Override
	public long getValueHash() {
		return id;
	}
}
