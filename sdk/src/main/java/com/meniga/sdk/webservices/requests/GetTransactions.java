package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.transactions.TransactionsFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTransactions extends QueryRequestObject {

	public TransactionsFilter filter;

	@Override
	public long getValueHash() {
		return filter != null ? filter.hashCode() : 0;
	}

	@Override
	public Map<String, String> toQueryMap() {
		if (this.filter == null) {
			return new HashMap<>();
		}
		return filter.toQueryMap();
	}
}
