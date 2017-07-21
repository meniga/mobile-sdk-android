package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetSplitTransactions extends QueryRequestObject {

	public long parentId;

	@Override
	public long getValueHash() {
		return this.parentId;
	}
}
