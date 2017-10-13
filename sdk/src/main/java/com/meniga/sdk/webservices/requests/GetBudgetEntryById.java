package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgetEntryById extends QueryRequestObject {

	public long budgetId;
	public long entryId;

	@Override
	public long getValueHash() {
		int result = (int) (budgetId ^ (budgetId >>> 32));
		result = 31 * result + (int) (entryId ^ (entryId >>> 32));
		return result;
	}
}
