package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.BudgetFilter;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaBudgetOperations {
	Result<List<MenigaBudget>> getBudgets(BudgetFilter filter);

	Result<MenigaBudget> getBudgetById(long id, BudgetFilter filter);

	Result<MenigaBudget> createBudget(String name, String description, List<Long> accountIds, List<MenigaBudgetEntry> entries);

	Result<Void> deleteBudget(long id);

	Result<Void> resetBudget(long id, List<Long> categoryIds, boolean resetManualEntries);

	Result<MenigaBudget> updateBudget(MenigaBudget budget);

	Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, BudgetFilter filter);

	Result<List<MenigaBudgetEntry>> getPlanningBudgetEntries(long budgetId, BudgetFilter filter);

	Result<MenigaBudgetEntry> getBudgetEntryById(long budgetId, long entryId);

	Result<List<MenigaBudgetEntry>> createBudgetEntries(long budgetId, List<MenigaBudgetEntry> entries, boolean isRecurring);

	Result<Void> deleteBudgetEntry(long budgetId, long entryId);

	Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, MenigaBudgetEntry entry);
}
