package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdatedNetWorthAccount extends QueryRequestObject {

	public transient long id;
	public Boolean isExcluded;
	public String accountName;

	@Override
	public long getValueHash() {
		return this.id;
	}
}
