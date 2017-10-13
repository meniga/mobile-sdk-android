package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetAccountBalanceHistory extends QueryRequestObject {

	public long id;
	public DateTime dateFrom;
	public DateTime dateTo;
	public AccountBalanceHistorySort sort;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
		result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
		result = 31 * result + (sort != null ? sort.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("dateFrom", this.dateFrom.toString());
		map.put("dateTo", this.dateTo.toString());
		map.put("sort", this.sort.toString());
		return map;
	}
}
