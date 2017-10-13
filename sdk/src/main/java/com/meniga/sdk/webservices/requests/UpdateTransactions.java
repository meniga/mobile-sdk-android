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
		int result = transactionIds != null ? transactionIds.hashCode() : 0;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
		result = 31 * result + (hasUncertainCategorization ? 1 : 0);
		result = 31 * result + (useSubTextInRecat != null ? useSubTextInRecat.hashCode() : 0);
		result = 31 * result + (isRead != null ? isRead.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (userData != null ? userData.hashCode() : 0);
		return result;
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
