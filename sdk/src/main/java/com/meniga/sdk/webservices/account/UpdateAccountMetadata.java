package com.meniga.sdk.webservices.account;

import com.meniga.sdk.webservices.requests.QueryRequestObject;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateAccountMetadata extends QueryRequestObject {

	public transient long id;
	public String key;
	public String value;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (key != null ? key.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
