package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.webservices.requests.QueryRequestObject;

public class DeleteBudgetEntry extends QueryRequestObject {
    public long budgetId;
    public long entryId;

    @Override
    public long getValueHash() {
        int result = (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (int) (entryId ^ (entryId >>> 32));
        return result;
    }
}
