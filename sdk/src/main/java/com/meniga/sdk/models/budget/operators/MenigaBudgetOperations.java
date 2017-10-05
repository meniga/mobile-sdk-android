package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.BudgetFilter;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaBudgetOperations {

    Result<List<MenigaBudget>> getBudgets(BudgetType planning);

    Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, DateTime start, DateTime end, List<Long> categoryIds, boolean allowIntersect);

    Result<Void> createBudget(String name, String description, List<Long> accountIds);
}
