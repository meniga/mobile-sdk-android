package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateTransactions extends QueryRequestObject {

	public transient List<Long> transactionIds;
	public DateTime date;
	public String text;
	public MenigaDecimal amount;
	public long categoryId;
	public boolean hasUncertainCategorization;
	public Boolean useSubTextInRecat;
	public Boolean isRead;
	public Boolean isFlagged;
	public String userData;

	@Override
	public long getValueHash() {
		return -1;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		String ids = "";
		for (int i = 0; i < this.transactionIds.size(); i++) {
			if (i > 0)
				ids += ",";
			ids += transactionIds.get(i).toString();
		}
		query.put("transactionIds", ids);

		return query;
	}
}
