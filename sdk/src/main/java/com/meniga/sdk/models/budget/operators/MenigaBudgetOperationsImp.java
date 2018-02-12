package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.CreateBudgetEntry;
import com.meniga.sdk.webservices.requests.DeleteBudgetRequest;
import com.meniga.sdk.webservices.requests.GetBudget;
import com.meniga.sdk.webservices.requests.GetBudgetEntries;
import com.meniga.sdk.webservices.requests.GetBudgetEntryById;
import com.meniga.sdk.webservices.requests.GetBudgets;
import com.meniga.sdk.webservices.requests.ResetBudgetRequest;
import com.meniga.sdk.webservices.requests.UpdateBudget;
import com.meniga.sdk.webservices.requests.UpdateBudgetRules;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {

    @Override
    public Result<List<MenigaBudget>> getBudgets(GetBudgets getBudgets) {
        return MenigaSDK.executor().getBudgets(getBudgets);
    }

    @Override
    public Result<MenigaBudget> getBudget(GetBudget getBudget) {
        return MenigaSDK.executor().getBudget(getBudget);
    }

    @Override
    public Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget updateBudget) {
        return MenigaSDK.executor().updateBudget(budgetId, updateBudget);
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
    public Result<List<MenigaBudgetEntry>> createBudgetEntry(long budgetId, CreateBudgetEntry createBudgetEntry) {
        return MenigaSDK.executor().createBudgetEntry(budgetId, createBudgetEntry);
    }

    @Override
    public Result<Void> deleteBudgetEntry(long budgetId, long entryId) {
        DeleteBudgetEntry deleteBudgetEntry = new DeleteBudgetEntry();
        deleteBudgetEntry.budgetId = budgetId;
        deleteBudgetEntry.entryId = entryId;
        return MenigaSDK.executor().deleteBudgetEntry(deleteBudgetEntry);
    }

    @Override
    public Result<MenigaBudgetEntry> getBudgetEntry(GetBudgetEntryById getBudgetEntryById) {
        return MenigaSDK.executor().getBudgetEntry(getBudgetEntryById);
    }

    @Override
    public Result<MenigaBudget> createBudget(BudgetType type, String name, String description, List<Long> accountIds, BudgetPeriod period, Integer periodOffset) {
        CreateBudget req = new CreateBudget();
        req.type = type;
        req.name = name;
        req.description = description;
        req.accountIds = accountIds;
        req.period = period.toString();
        req.offset = periodOffset;

        return MenigaSDK.executor().createBudget(req);
    }

    @Override
    public Result<Void> updateBudgetRules(long budgetId, MenigaDecimal targetAmount, DateTime startDate,
                                          DateTime endDate, List<Long> catIds, int generationTypeValue,
                                          DateTime repeatUntil) {
        UpdateBudgetRules req = new UpdateBudgetRules();
        req.budgetId = budgetId;

        UpdateBudgetRules.UpdateBudgetData data = new UpdateBudgetRules.UpdateBudgetData();
        data.targetAmount = targetAmount;
        data.startDate = startDate;
        data.endDate = endDate;
        data.categoryIds = catIds;
        data.generationType = generationTypeValue;

        if (endDate == null/*endDate.getYear() == DateTime.now().getYear() && endDate.getMonthOfYear() == DateTime.now().getMonthOfYear()*/) {
            data.recurringPattern = null;
            data.repeatUntil = null;
        } else {
            data.recurringPattern = new UpdateBudgetRules.RecurringPattern();
            data.recurringPattern.monthInterval = Months.monthsBetween(endDate, startDate).getMonths();;
            data.repeatUntil = endDate;
        }

        req.rules = new ArrayList<>();
        req.rules.add(data);
        return MenigaSDK.executor().updateBudgetRules(req);
    }

    @Override
    public Result<Void> deleteBudget(long budgetId) {
        DeleteBudgetRequest request = new DeleteBudgetRequest();
        request.budgetId = budgetId;
        return MenigaSDK.executor().deleteBudget(request);
    }

    @Override
    public Result<Void> resetBudget(long budgetId) {
        ResetBudgetRequest request = new ResetBudgetRequest();
        request.budgetId = budgetId;
        return MenigaSDK.executor().resetBudget(request);
    }
}
