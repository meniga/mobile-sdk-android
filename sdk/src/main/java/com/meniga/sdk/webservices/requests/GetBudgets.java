package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.BudgetFilter;

import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgets extends QueryRequestObject {

	public transient BudgetFilter filter;
	public transient Map<String, String> query;

	@Override
	public long getValueHash() {
		return filter.hashCode();
	}

	@Override
	public Map<String, String> toQueryMap() {
		return query;
	}
}
