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
		return this.id;
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
