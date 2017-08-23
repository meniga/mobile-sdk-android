package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class CreateBudget extends QueryRequestObject {

	public BudgetType type;
	public BudgetPeriod period;
	public String name;
	public String description;
	public List<Long> accountIds;
	public List<CreateBudgetEntry> entries;
	public boolean isDefault;
	public int offset;

	@Override
	public long getValueHash() {
		return 0;
	}
}
