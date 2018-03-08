package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.webservices.budget.CreateBudget;
import com.meniga.sdk.webservices.budget.CreateBudgetEntries;
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
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {

    @Override
    public Result<List<MenigaBudget>> getBudgets(GetBudgets parameters) {
        return MenigaSDK.executor().getBudgets(parameters);
    }

    @Override
    public Result<MenigaBudget> getBudget(GetBudget parameters) {
        return MenigaSDK.executor().getBudget(parameters);
    }

    @Override
    public Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget parameters) {
        return MenigaSDK.executor().updateBudget(budgetId, parameters);
    }

    @Override
    public Result<List<MenigaBudgetEntry>> getBudgetEntries(GetBudgetEntries parameters) {
        return MenigaSDK.executor().getBudgetEntries(parameters);
    }

    @Override
    public Result<List<MenigaBudgetEntry>> createBudgetEntries(long budgetId, CreateBudgetEntries parameters) {
        return MenigaSDK.executor().createBudgetEntries(budgetId, parameters);
    }

    @Override
    public Result<Void> deleteBudgetEntry(long budgetId, long entryId) {
        return MenigaSDK.executor().deleteBudgetEntry(budgetId, entryId);
    }

    @Override
    public Result<MenigaBudgetEntry> getBudgetEntry(GetBudgetEntryById getBudgetEntryById) {
        return MenigaSDK.executor().getBudgetEntry(getBudgetEntryById);
    }

    @Override
    public Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, long entryId, UpdateBudgetEntry updateBudgetEntry) {
        return MenigaSDK.executor().updateBudgetEntry(budgetId, entryId, updateBudgetEntry);
    }

    @Override
    public Result<MenigaBudget> createBudget(CreateBudget createBudget) {
        return MenigaSDK.executor().createBudget(createBudget);
    }

    @Override
    public Result<Void> updateBudgetRules(long budgetId, UpdateBudgetRules parameters) {
        return MenigaSDK.executor().updateBudgetRules(budgetId, parameters);
    }

    @Override
    public Result<Void> deleteBudget(long budgetId) {
        return MenigaSDK.executor().deleteBudget(budgetId);
    }

    @Override
    public Result<Void> resetBudget(long budgetId) {
        return MenigaSDK.executor().resetBudget(budgetId);
    }
}
