package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.enums.AmountLimitSignCriteria;
import com.meniga.sdk.models.transactions.enums.AmountLimitTypeCriteria;
import com.meniga.sdk.models.transactions.enums.DateMatchTypeCriteria;
import com.meniga.sdk.models.transactions.enums.TransactionRuleTextCriteriaOperatorType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateTransactionRule extends QueryRequestObject {

	public transient boolean applyOnExisting;
	public String name;
	public String textCriteria;
	public TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType;
	public DateMatchTypeCriteria dateMatchTypeCriteria;
	public Integer daysLimitCriteria;
	public AmountLimitTypeCriteria amountLimitTypeCriteria;
	public AmountLimitSignCriteria amountLimitSignCriteria;
	public MenigaDecimal amountCriteria;
	public String accountCategoryCriteria;
	public Boolean acceptAction;
	public Integer monthShiftAction;
	public Boolean removeAction;
	public String textAction;
	public String commentAction;
	public Long categoryIdAction;
	public List<MenigaTransactionRule.SplitAction> splitActions;
	public Boolean flagAction;

	@Override
	public long getValueHash() {
		int result = (applyOnExisting ? 1 : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (textCriteria != null ? textCriteria.hashCode() : 0);
		result = 31 * result + (textCriteriaOperatorType != null ? textCriteriaOperatorType.hashCode() : 0);
		result = 31 * result + (dateMatchTypeCriteria != null ? dateMatchTypeCriteria.hashCode() : 0);
		result = 31 * result + (daysLimitCriteria != null ? daysLimitCriteria.hashCode() : 0);
		result = 31 * result + (amountLimitTypeCriteria != null ? amountLimitTypeCriteria.hashCode() : 0);
		result = 31 * result + (amountLimitSignCriteria != null ? amountLimitSignCriteria.hashCode() : 0);
		result = 31 * result + (amountCriteria != null ? amountCriteria.hashCode() : 0);
		result = 31 * result + (accountCategoryCriteria != null ? accountCategoryCriteria.hashCode() : 0);
		result = 31 * result + (acceptAction != null ? acceptAction.hashCode() : 0);
		result = 31 * result + (monthShiftAction != null ? monthShiftAction.hashCode() : 0);
		result = 31 * result + (removeAction != null ? removeAction.hashCode() : 0);
		result = 31 * result + (textAction != null ? textAction.hashCode() : 0);
		result = 31 * result + (commentAction != null ? commentAction.hashCode() : 0);
		result = 31 * result + (categoryIdAction != null ? categoryIdAction.hashCode() : 0);
		result = 31 * result + (splitActions != null ? splitActions.hashCode() : 0);
		result = 31 * result + (flagAction != null ? flagAction.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("applyOnExisting", Boolean.toString(this.applyOnExisting));
		return map;
	}
}
