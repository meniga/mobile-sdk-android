package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.BudgetFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetBudgetEntries extends QueryRequestObject {

	public long budgetId;
	public transient BudgetFilter filter;

	@Override
	public long getValueHash() {
		int result = (int) (budgetId ^ (budgetId >>> 32));
		result = 31 * result + (filter != null ? filter.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap()
	{
		if(filter != null)
			return filter.asMap();
		return new HashMap<>();
	}
}
