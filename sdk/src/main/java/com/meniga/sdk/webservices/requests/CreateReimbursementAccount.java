package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateReimbursementAccount extends QueryRequestObject {

	public String name;
	public String accountType;
	public String accountInfo;

	@Override
	public long getValueHash() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
		result = 31 * result + (accountInfo != null ? accountInfo.hashCode() : 0);
		return result;
	}
}
