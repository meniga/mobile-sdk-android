package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.MenigaBudgetEntry;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class CreateBudgetEntries extends QueryRequestObject {
    public long budgetId;
    public List<MenigaBudgetEntry> entries;
    public boolean isRecurring;
    @Override
    public long getValueHash() {
        return 0;
    }
}
