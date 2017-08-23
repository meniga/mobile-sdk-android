package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class CreateBudgetEntry extends QueryRequestObject {

	public MenigaDecimal targetAmount;
	public DateTime startDate;
	public DateTime endDate;
	public List<Long> categoryIds;
	public int autoFillType;

	@Override
	public long getValueHash() {
		return 0;
	}
}
