package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetReimbursementAccountById extends QueryRequestObject {

	public GetReimbursementAccountById(int id) {
		this.id = id;
	}

	public int id;

	@Override
	public long getValueHash() {
		return id;
	}
}
