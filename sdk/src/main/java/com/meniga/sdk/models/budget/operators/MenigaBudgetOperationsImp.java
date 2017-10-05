package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.BudgetFilter;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.GetBudgetEntries;
import com.meniga.sdk.webservices.requests.GetBudgets;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {
    @Override
    public Result<List<MenigaBudget>> getBudgets(BudgetType type) {
        GetBudgets req = new GetBudgets();
        req.type = type;

        return MenigaSDK.executor().getBudgets(req);
    }

    @Override
    public Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, DateTime start, DateTime end, List<Long> categoryIds, boolean allowIntersect) {
        GetBudgetEntries req = new GetBudgetEntries();
        req.id = budgetId;
        req.startDate = start;
        req.endDate = end;
        req.categoryIds = categoryIds;
        req.allowOverlappingEntries = allowIntersect;

        return MenigaSDK.executor().getBudgetEntries(req);
    }

    @Override
    public Result<Void> createBudget(String name, String description, List<Long> accountIds) {
        CreateBudget req = new CreateBudget();
        req.type = BudgetType.PLANNING;
        req.name = name;
        req.description = description;
        req.accountIds = accountIds;
        req.period = BudgetPeriod.MONTH.toString();
        req.offset = 0;

        return MenigaSDK.executor().createBudget(req);
    }
}
