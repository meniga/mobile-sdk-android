package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class UpdateBudgetEntry extends QueryRequestObject {

	public transient long budgetId;
	public transient long id;
	public MenigaDecimal targetAmount;
	public DateTime startDate;
	public DateTime endDate;
	public List<Long> categoryIds;
	public int generationType;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + generationType;
		return result;
	}


}
