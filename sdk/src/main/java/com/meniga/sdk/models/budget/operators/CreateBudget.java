package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateBudget extends QueryRequestObject {
    public BudgetType type;
    public String name;
    public String description;
    public List<Long> accountIds;
    public String period;
    public Integer offset;

    @Override
    public long getValueHash() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (offset != null ? offset.hashCode() : 0);
        return result;
    }
}
