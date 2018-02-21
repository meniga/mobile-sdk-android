package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.CreateBudgetRulesParameters;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.CreateBudgetEntryParameters;
import com.meniga.sdk.webservices.requests.GetBudgetParameters;
import com.meniga.sdk.webservices.requests.GetBudgetEntries;
import com.meniga.sdk.webservices.requests.GetBudgetEntryById;
import com.meniga.sdk.webservices.requests.GetBudgetsParameters;
import com.meniga.sdk.webservices.requests.UpdateBudgetParameters;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {

    @Override
    public Result<List<MenigaBudget>> getBudgets(GetBudgetsParameters parameters) {
        return MenigaSDK.executor().getBudgets(parameters);
    }

    @Override
    public Result<MenigaBudget> getBudget(GetBudgetParameters parameters) {
        return MenigaSDK.executor().getBudget(parameters);
    }

    @Override
    public Result<MenigaBudget> updateBudget(long budgetId, UpdateBudgetParameters parameters) {
        return MenigaSDK.executor().updateBudget(budgetId, parameters);
    }

    @Override
    public Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, DateTime start, DateTime end, List<Long> categoryIds, boolean allowIntersect) {
        GetBudgetEntries req = new GetBudgetEntries();
        req.setId(budgetId);
        req.setStartDate(start);
        req.setEndDate(end);
        req.setCategoryIds(categoryIds);
        req.setAllowOverlappingEntries(allowIntersect);

        return MenigaSDK.executor().getBudgetEntries(req);
    }

    @Override
    public Result<List<MenigaBudgetEntry>> createBudgetEntry(long budgetId, CreateBudgetEntryParameters parameters) {
        return MenigaSDK.executor().createBudgetEntry(budgetId, parameters);
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
    public Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, long entryId, UpdateBudgetEntryParameters updateBudgetEntry) {
        return MenigaSDK.executor().updateBudgetEntry(budgetId, entryId, updateBudgetEntry);
    }

    @Override
    public Result<MenigaBudget> createBudget(BudgetType type, String name, String description, List<Long> accountIds, BudgetPeriod period, Integer periodOffset) {
        CreateBudgetParameters req = new CreateBudgetParameters();
        req.type = type;
        req.name = name;
        req.description = description;
        req.accountIds = accountIds;
        req.period = period.toString();
        req.offset = periodOffset;

        return MenigaSDK.executor().createBudget(req);
    }

    @Override
    public Result<Void> updateBudgetRules(long budgetId, CreateBudgetRulesParameters parameters) {
        return MenigaSDK.executor().updateBudgetRules(budgetId, parameters.toUpdateBudgetRules());
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
