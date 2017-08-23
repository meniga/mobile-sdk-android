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


	Result<List<MenigaBudget>> getBudgets(List<Long> ids, List<Long> accountIds, BudgetType budgetType);

	Result<MenigaBudget> createBudget(BudgetType type, String name, String description, List<Long> accountIds, boolean isDefault, BudgetPeriod period, int offset);

	Result<Void> deleteBudget(long id);

	Result<MenigaBudget> updateBudget(long id, String name, String description, List<Long> accountIds, boolean isDefault, int offset);

	Result<Void> reset(long id);

	Result<List<MenigaBudgetEntry>> getBudgetEntries(long id, BudgetFilter filter);

	Result<List<MenigaBudgetEntry>> createBudgetEntries(long id, List<MenigaBudgetEntry> entries);

	Result<MenigaBudget> getBudgetById(Long id, BudgetFilter filter);

	Result<MenigaBudgetEntry> updateEntry(long budgetId, long id, MenigaDecimal targetAmount, DateTime startDate, DateTime endDate, int generationType, List<Long> categoryIds);

	Result<Void> deleteBudgetEntry(long id, long entryId);
}
