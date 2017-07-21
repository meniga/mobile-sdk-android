package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.enums.AmountLimitSignCriteria;
import com.meniga.sdk.models.transactions.enums.AmountLimitTypeCriteria;
import com.meniga.sdk.models.transactions.enums.DateMatchTypeCriteria;
import com.meniga.sdk.models.transactions.enums.TransactionRuleTextCriteriaOperatorType;
import com.meniga.sdk.webservices.requests.CreateTransactionRule;
import com.meniga.sdk.webservices.requests.DeleteTransactionRule;
import com.meniga.sdk.webservices.requests.GetTransactionRule;
import com.meniga.sdk.webservices.requests.GetTransactionRules;
import com.meniga.sdk.webservices.requests.UpdateTransactionRule;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionRuleOperationsImp implements MenigaTransactionRuleOperations {

	@Override
	public Result<List<MenigaTransactionRule>> getTransactionRules() {

		GetTransactionRules req = new GetTransactionRules();

		return MenigaSDK.executor().getTransactionRules(req);
	}

	@Override
	public Result<MenigaTransactionRule> createTransactionRule(
			String name,
			String textCriteria,
			TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType,
			DateMatchTypeCriteria dateMatchTypeCriteria,
			Integer daysLimitCriteria,
			AmountLimitTypeCriteria amountLimitTypeCriteria,
			AmountLimitSignCriteria amountLimitSignCriteria,
			MenigaDecimal amountCriteria,
			String accountCategoryCriteria,
			Boolean acceptAction,
			Integer monthShiftAction,
			Boolean removeAction,
			String textAction,
			String commentAction,
			Long categoryIdAction,
			List<MenigaTransactionRule.SplitAction> splitActions,
			Boolean flagAction
	) {
		CreateTransactionRule req = new CreateTransactionRule();
		req.name = name;
		req.textCriteria = textCriteria;
		req.textCriteriaOperatorType = textCriteriaOperatorType;
		req.dateMatchTypeCriteria = dateMatchTypeCriteria;
		req.daysLimitCriteria = daysLimitCriteria;
		req.amountLimitTypeCriteria = amountLimitTypeCriteria;
		req.amountCriteria = amountCriteria;
		req.amountLimitSignCriteria = amountLimitSignCriteria;
		req.accountCategoryCriteria = accountCategoryCriteria;
		req.acceptAction = acceptAction;
		req.monthShiftAction = monthShiftAction;
		req.removeAction = removeAction;
		req.textAction = textAction;
		req.commentAction = commentAction;
		req.categoryIdAction = categoryIdAction;
		req.splitActions = splitActions;
		req.flagAction = flagAction;

		return MenigaSDK.executor().createTransactionRule(req);
	}

	@Override
	public Result<Void> deleteTransactionRule(long id) {
		DeleteTransactionRule req = new DeleteTransactionRule();
		req.id = id;

		return MenigaSDK.executor().deleteTransactionRule(req);
	}

	@Override
	public Result<MenigaTransactionRule> getTransactionRule(long id) {
		GetTransactionRule req = new GetTransactionRule();
		req.id = id;

		return MenigaSDK.executor().getTransactionRule(req);
	}

	@Override
	public Result<Void> updateTransactionRule(
			long id,
			String name,
			String textCriteria,
			TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType,
			DateMatchTypeCriteria dateMatchTypeCriteria,
			Integer daysLimitCriteria,
			AmountLimitTypeCriteria amountLimitTypeCriteria,
			AmountLimitSignCriteria amountLimitSignCriteria,
			MenigaDecimal amountCriteria,
			String accountCategoryCriteria,
			Boolean acceptAction,
			Integer monthShiftAction,
			Boolean removeAction,
			String textAction,
			String commentAction,
			Long categoryIdAction,
			List<MenigaTransactionRule.SplitAction> splitActions,
			Boolean flagAction,
			boolean applyOnExisting) {

		UpdateTransactionRule req = new UpdateTransactionRule();
		req.id = id;
		req.name = name;
		req.textCriteria = textCriteria;
		req.textCriteriaOperatorType = textCriteriaOperatorType;
		req.dateMatchTypeCriteria = dateMatchTypeCriteria;
		req.daysLimitCriteria = daysLimitCriteria;
		req.amountLimitTypeCriteria = amountLimitTypeCriteria;
		req.amountLimitSignCriteria = amountLimitSignCriteria;
		req.amountCriteria = amountCriteria;
		req.accountCategoryCriteria = accountCategoryCriteria;
		req.acceptAction = acceptAction;
		req.monthShiftAction = monthShiftAction;
		req.removeAction = removeAction;
		req.textAction = textAction;
		req.commentAction = commentAction;
		req.categoryIdAction = categoryIdAction;
		req.splitActions = splitActions;
		req.flagAction = flagAction;
		req.applyOnExisting = applyOnExisting;
		return MenigaSDK.executor().updateTransactionRule(req);
	}
}
