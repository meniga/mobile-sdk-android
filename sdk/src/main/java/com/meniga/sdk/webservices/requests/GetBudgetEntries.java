package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.BudgetFilter;

import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetBudgetEntries extends QueryRequestObject {
    public long budgetId;
    public BudgetFilter filter;
    public Map<String, String> query;
    public boolean isPlanning;
    @Override
    public long getValueHash() {
        return budgetId * 31 + (isPlanning ? 1 : 0);
    }
}
