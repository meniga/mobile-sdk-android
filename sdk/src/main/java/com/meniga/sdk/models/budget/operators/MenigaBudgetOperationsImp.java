package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.BudgetFilter;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.webservices.requests.CreateBudget;
import com.meniga.sdk.webservices.requests.CreateBudgetEntries;
import com.meniga.sdk.webservices.requests.CreateBudgetEntry;
import com.meniga.sdk.webservices.requests.DeleteBudget;
import com.meniga.sdk.webservices.requests.DeleteBudgetEntry;
import com.meniga.sdk.webservices.requests.GetBudgetById;
import com.meniga.sdk.webservices.requests.GetBudgetEntries;
import com.meniga.sdk.webservices.requests.GetBudgetEntryById;
import com.meniga.sdk.webservices.requests.GetBudgets;
import com.meniga.sdk.webservices.requests.ResetBudget;
import com.meniga.sdk.webservices.requests.UpdateBudget;
import com.meniga.sdk.webservices.requests.UpdateBudgetEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {

	@Override
	public Result<List<MenigaBudget>> getBudgets(BudgetFilter filter) {
		GetBudgets req = new GetBudgets();
		req.filter = filter;
		req.query = filter.asMap();

		return MenigaSDK.executor().getBudgets(req);
	}

	@Override
	public Result<MenigaBudget> getBudgetById(long id, BudgetFilter filter) {
		GetBudgetById req = new GetBudgetById();
		req.id = id;
		req.filter = filter;
		req.query = filter.asMap();
		return MenigaSDK.executor().getBudgetById(req);
	}

	@Override
	public Result<MenigaBudget> updateBudget(MenigaBudget budget) {
		UpdateBudget req = new UpdateBudget();
		req.name = budget.getName();
		req.description = budget.getDescription();
		req.accountIds = budget.getAccountIds();
		req.entries = new ArrayList<>();
		for(MenigaBudgetEntry entry : budget.getEntries()) {
			UpdateBudgetEntry created = new UpdateBudgetEntry();
			created.autoFillType = 0;
			created.categoryIds = entry.getCategoryIds();
			created.endDate = entry.getEndDate();
			created.startDate = entry.getStartDate();
			created.targetAmount = entry.getTargetAmount();
			req.entries.add(created);
		}
		return MenigaSDK.executor().updateBudgetEntry(budget.getId(), req);
	}

	@Override
	public Result<List<MenigaBudgetEntry>> getBudgetEntries(long budgetId, BudgetFilter filter) {
		GetBudgetEntries req = new GetBudgetEntries();
		req.budgetId = budgetId;
		req.filter = filter;
		req.query = filter.asMap();
		return MenigaSDK.executor().getBudgetEntries(req);
	}

	@Override
	public Result<List<MenigaBudgetEntry>> getPlanningBudgetEntries(long budgetId, BudgetFilter filter) {
		GetBudgetEntries req = new GetBudgetEntries();
		req.budgetId = budgetId;
		req.filter = filter;
		req.query = filter.asMap();
		return MenigaSDK.executor().getPlanningBudgetEntries(req);
	}

	@Override
	public Result<MenigaBudgetEntry> getBudgetEntryById(long budgetId, long entryId) {
		GetBudgetEntryById req = new GetBudgetEntryById();
		req.entryId = entryId;
		req.budgetId = budgetId;
		return MenigaSDK.executor().getBudgetEntryById(req);
	}

	@Override
	public Result<List<MenigaBudgetEntry>> createBudgetEntries(long budgetId, List<MenigaBudgetEntry> entries, boolean isRecurring) {
		CreateBudgetEntries req = new CreateBudgetEntries();
		req.budgetId = budgetId;
		req.entries = entries;
		req.isRecurring = isRecurring;
		return MenigaSDK.executor().createBudgetEntries(req);
	}

	@Override
	public Result<Void> deleteBudgetEntry(long budgetId, long entryId) {
		DeleteBudgetEntry req = new DeleteBudgetEntry();
		req.entryId = entryId;
		req.budgetId = budgetId;
		return MenigaSDK.executor().deleteBudgetEntry(req);
	}

	@Override
	public Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, MenigaBudgetEntry entry) {
		UpdateBudgetEntry update = new UpdateBudgetEntry();
		update.autoFillType = 0;
		update.categoryIds = entry.getCategoryIds();
		update.endDate = entry.getEndDate();
		update.startDate = entry.getStartDate();
		return MenigaSDK.executor().updateBudgetEntry(budgetId, entry.getId(), update);
	}

	@Override
	public Result<MenigaBudget> createBudget(String name, String description, List<Long> accountIds, List<MenigaBudgetEntry> entries) {
		CreateBudget req = new CreateBudget();
		req.name = name;
		req.description = description;
		req.accountIds = accountIds;
		req.entries = new ArrayList<>();
		for(MenigaBudgetEntry entry : entries) {
			CreateBudgetEntry created = new CreateBudgetEntry();
			created.autoFillType = 0;
			created.categoryIds = entry.getCategoryIds();
			created.endDate = entry.getEndDate();
			created.startDate = entry.getStartDate();
			created.targetAmount = entry.getTargetAmount();
			req.entries.add(created);
		}
		return MenigaSDK.executor().createBudget(req);
	}

	@Override
	public Result<Void> deleteBudget(long id) {
		DeleteBudget req = new DeleteBudget();
		req.id = id;
		return MenigaSDK.executor().deleteBudget(req);
	}

	@Override
	public Result<Void> resetBudget(long id, List<Long> categoryIds, boolean resetManualEntries) {
		ResetBudget resetBudget = new ResetBudget();
		resetBudget.id = id;
		resetBudget.categoryIds = categoryIds;
		resetBudget.resetManualEntries = resetManualEntries;
		return MenigaSDK.executor().resetBudget(resetBudget);
	}
}
