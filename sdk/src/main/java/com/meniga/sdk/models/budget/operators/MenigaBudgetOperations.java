package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.MenigaBudgetRule;
import com.meniga.sdk.webservices.budget.CreateBudget;
import com.meniga.sdk.webservices.budget.CreateBudgetEntries;
import com.meniga.sdk.webservices.budget.GetBudget;
import com.meniga.sdk.webservices.budget.GetBudgetEntries;
import com.meniga.sdk.webservices.budget.GetBudgetEntryById;
import com.meniga.sdk.webservices.budget.GetBudgetRules;
import com.meniga.sdk.webservices.budget.GetBudgets;
import com.meniga.sdk.webservices.budget.UpdateBudget;
import com.meniga.sdk.webservices.budget.UpdateBudgetEntry;
import com.meniga.sdk.webservices.budget.CreateBudgetRules;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaBudgetOperations {

    Result<List<MenigaBudget>> getBudgets(GetBudgets parameters);

    Result<MenigaBudget> getBudget(GetBudget parameters);

    Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget parameters);

    Result<List<MenigaBudgetEntry>> getBudgetEntries(GetBudgetEntries parameters);

    Result<List<MenigaBudgetEntry>> createBudgetEntries(long budgetId, CreateBudgetEntries parameters);

    Result<Void> deleteBudgetEntry(long budgetId, long entryId);

    Result<MenigaBudgetEntry> getBudgetEntry(GetBudgetEntryById getBudgetEntryById);

    Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, long entryId, UpdateBudgetEntry updateBudgetEntry);

    Result<MenigaBudget> createBudget(CreateBudget createBudget);

    Result<Void> deleteBudget(long budgetId);

    Result<Void> resetBudget(long budgetId);

    Result<List<MenigaBudgetRule>> getBudgetRules(GetBudgetRules filter);

    Result<List<MenigaBudgetRule>> createBudgetRules(long budgetId, CreateBudgetRules parameters);

	Result<Void> deleteBudgetRule(long budgetId, long ruleId);
}
