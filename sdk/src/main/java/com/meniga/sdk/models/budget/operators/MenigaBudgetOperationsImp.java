package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.BudgetFilter;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.webservices.requests.CreateBudget;
import com.meniga.sdk.webservices.requests.CreateBudgetEntries;
import com.meniga.sdk.webservices.requests.DeleteBudget;
import com.meniga.sdk.webservices.requests.DeleteBudgetEntry;
import com.meniga.sdk.webservices.requests.GetBudgetById;
import com.meniga.sdk.webservices.requests.GetBudgetEntries;
import com.meniga.sdk.webservices.requests.GetBudgets;
import com.meniga.sdk.webservices.requests.ResetBudget;
import com.meniga.sdk.webservices.requests.UpdateBudget;
import com.meniga.sdk.webservices.requests.UpdateBudgetEntry;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBudgetOperationsImp implements MenigaBudgetOperations {

	@Override
	public Result<List<MenigaBudget>> getBudgets(List<Long> ids, List<Long> accountIds, BudgetType budgetType) {
		GetBudgets req = new GetBudgets();
		req.accountIds = accountIds;
		req.ids = ids;
		req.type = budgetType;
		return MenigaSDK.executor().getBudgets(req);
	}

	@Override
	public Result<MenigaBudget> createBudget(BudgetType type, String name, String description, List<Long> accountIds, boolean isDefault, BudgetPeriod period, int offset) {
		CreateBudget req = new CreateBudget();
		req.type = type;
		req.name = name;
		req.description = description;
		req.accountIds = accountIds;
		req.isDefault = isDefault;
		req.period = period;
		req.offset = offset;
		return MenigaSDK.executor().createBudget(req);
	}

	@Override
	public Result<Void> deleteBudget(long id) {
		DeleteBudget req = new DeleteBudget();
		req.id = id;
		return MenigaSDK.executor().deleteBudget(req);
	}

	@Override
	public Result<MenigaBudget> updateBudget(long id, String name, String description, List<Long> accountIds, boolean isDefault, int offset) {
		UpdateBudget req = new UpdateBudget();
		req.id = id;
		req.name = name;
		req.description = description;
		req.accountIds = accountIds;
		req.isDefault = isDefault;
		req.offset = offset;
		return MenigaSDK.executor().updateBudget(req);
	}

	@Override
	public Result<Void> reset(long id) {
		ResetBudget req = new ResetBudget();
		req.id = id;
		return MenigaSDK.executor().resetBudget(req);
	}

	@Override
	public Result<List<MenigaBudgetEntry>> getBudgetEntries(long id, BudgetFilter filter) {
		GetBudgetEntries req = new GetBudgetEntries();
		req.budgetId = id;
		req.filter = filter;
		return MenigaSDK.executor().getBudgetEntries(req);
	}

	@Override
	public Result<List<MenigaBudgetEntry>> createBudgetEntries(long id, List<MenigaBudgetEntry> entries) {
		CreateBudgetEntries req = new CreateBudgetEntries();
		req.budgetId = id;
		req.entries = entries;
		return MenigaSDK.executor().createBudgetEntries(req);
	}

	@Override
	public Result<MenigaBudget> getBudgetById(Long id, BudgetFilter filter) {
		GetBudgetById req = new GetBudgetById();
		req.id = id;
		req.filter = filter;
		return MenigaSDK.executor().getBudgetById(req);
	}

	@Override
	public Result<MenigaBudgetEntry> updateEntry(long budgetId, long id, MenigaDecimal targetAmount, DateTime startDate, DateTime endDate, int generationType, List<Long> categoryIds) {
		UpdateBudgetEntry req = new UpdateBudgetEntry();
		req.budgetId = budgetId;
		req.id = id;
		req.targetAmount = targetAmount;
		req.startDate = startDate;
		req.endDate = endDate;
		req.generationType = generationType;
		req.categoryIds = categoryIds;
		return MenigaSDK.executor().updateBudgetEntry(req);
	}

	@Override
	public Result<Void> deleteBudgetEntry(long id, long entryId) {
		DeleteBudgetEntry req = new DeleteBudgetEntry();
		req.budgetId = id;
		req.entryId = entryId;
		return MenigaSDK.executor().deleteBudgetEntry(req);
	}
}
