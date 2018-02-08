package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.CreateBudgetEntry;
import com.meniga.sdk.webservices.requests.GetBudget;
import com.meniga.sdk.webservices.requests.GetBudgets;
import com.meniga.sdk.webservices.requests.UpdateBudget;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaBudgetOperations {

    Result<List<MenigaBudget>> getBudgets(GetBudgets getBudgets);

    Result<MenigaBudget> getBudget(GetBudget getBudget);

    Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget updateBudget);

    Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, DateTime start, DateTime end,
                                                     List<Long> categoryIds, boolean allowIntersect);

    Result<List<MenigaBudgetEntry>> createBudgetEntry(long budgetId, CreateBudgetEntry createBudgetEntry);

    Result<MenigaBudget> createBudget(BudgetType type, String name, String description, List<Long> accountIds, BudgetPeriod period, Integer periodOffset);

    Result<Void> updateBudgetRules(long budgetId, MenigaDecimal targetAmount, DateTime startDate, DateTime endDate,
                                   List<Long> catIds, int generationTypeValue, DateTime repeatUntil);

    Result<Void> deleteBudget(long budgetId);

    Result<Void> resetBudget(long budgetId);

}
