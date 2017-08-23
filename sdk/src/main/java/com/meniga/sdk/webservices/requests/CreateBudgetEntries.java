package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.MenigaBudgetEntry;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class CreateBudgetEntries extends QueryRequestObject {

    public long budgetId;
    public List<MenigaBudgetEntry> entries;

    @Override
    public long getValueHash() {
        int result = (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }
}
