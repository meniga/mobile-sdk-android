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
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (isExcluded != null ? isExcluded.hashCode() : 0);
		result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
		return result;
	}
}
