package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.BudgetFilter;

import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgetById extends QueryRequestObject {

	public transient long id;
	public transient BudgetFilter filter;
	public transient Map<String, String> query;

	public GetBudgetById() {
	}

	@Override
	public long getValueHash() {
		return this.id;
	}
}
