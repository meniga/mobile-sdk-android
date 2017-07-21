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
		return -1;
	}
}
