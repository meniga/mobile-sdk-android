package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.budget.CreateBudget;
import com.meniga.sdk.webservices.budget.CreateBudgetEntry;
import com.meniga.sdk.webservices.budget.GetBudget;
import com.meniga.sdk.webservices.budget.GetBudgetEntries;
import com.meniga.sdk.webservices.budget.GetBudgetEntryById;
import com.meniga.sdk.webservices.budget.GetBudgets;
import com.meniga.sdk.webservices.budget.UpdateBudget;
import com.meniga.sdk.webservices.budget.UpdateBudgetEntry;
import com.meniga.sdk.webservices.budget.UpdateBudgetRules;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaBudgetOperations {

    Result<List<MenigaBudget>> getBudgets(GetBudgets parameters);

    Result<MenigaBudget> getBudget(GetBudget parameters);

    Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget parameters);

    Result<List<MenigaBudgetEntry>> getBudgetEntries(GetBudgetEntries parameters);

    Result<List<MenigaBudgetEntry>> createBudgetEntry(long budgetId, CreateBudgetEntry parameters);

    Result<Void> deleteBudgetEntry(long budgetId, long entryId);

    Result<MenigaBudgetEntry> getBudgetEntry(GetBudgetEntryById getBudgetEntryById);

    Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, long entryId, UpdateBudgetEntry updateBudgetEntry);

    Result<MenigaBudget> createBudget(CreateBudget createBudget);

    Result<Void> deleteBudget(long budgetId);

    Result<Void> resetBudget(long budgetId);

    Result<Void> updateBudgetRules(long budgetId, UpdateBudgetRules parameters);
}
