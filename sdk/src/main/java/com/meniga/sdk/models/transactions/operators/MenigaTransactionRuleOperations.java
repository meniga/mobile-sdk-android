package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.enums.AmountLimitSignCriteria;
import com.meniga.sdk.models.transactions.enums.AmountLimitTypeCriteria;
import com.meniga.sdk.models.transactions.enums.DateMatchTypeCriteria;
import com.meniga.sdk.models.transactions.enums.TransactionRuleTextCriteriaOperatorType;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaTransactionRuleOperations {
	Result<List<MenigaTransactionRule>> getTransactionRules();

	Result<MenigaTransactionRule> createTransactionRule(
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
	);

	Result<Void> deleteTransactionRule(long id);

	Result<MenigaTransactionRule> getTransactionRule(long id);

	Result<Void> updateTransactionRule(
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
			boolean applyOnExisting
	);
}
