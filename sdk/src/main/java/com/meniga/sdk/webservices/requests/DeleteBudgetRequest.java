package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class DeleteBudgetRequest extends QueryRequestObject {
    public long budgetId;

    @Override
    public long getValueHash() {
        return (int) (budgetId ^ (budgetId >>> 32));
    }
}
