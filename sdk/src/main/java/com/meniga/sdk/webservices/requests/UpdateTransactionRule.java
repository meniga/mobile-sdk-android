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
public class UpdateTransactionRule extends QueryRequestObject {

	public transient long id;
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
	public transient boolean applyOnExisting;

	@Override
	public long getValueHash() {
		return this.id;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("applyOnExisting", Boolean.toString(this.applyOnExisting));
		return map;
	}
}
