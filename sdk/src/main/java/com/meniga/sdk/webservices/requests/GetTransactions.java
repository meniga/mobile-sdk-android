package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.util.HashMap;
import java.util.Map;

/**
 *  Copyright 2017 Meniga Iceland Inc.
 */
public class GetTransactions extends QueryRequestObject {
	public TransactionsFilter filter;

	@Override
	public long getValueHash() {
		long valueHash = this.filter.getValueHash();
		return String.valueOf(valueHash).hashCode();
	}

	@Override
	public Map<String, String> toQueryMap() {
		if (this.filter == null) {
			return new HashMap<>();
		}
		return this.filter.toQueryMap();
	}
}
