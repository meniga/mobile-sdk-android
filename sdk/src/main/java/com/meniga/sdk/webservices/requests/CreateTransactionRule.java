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
		return -1;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("applyOnExisting", Boolean.toString(this.applyOnExisting));
		return map;
	}
}
