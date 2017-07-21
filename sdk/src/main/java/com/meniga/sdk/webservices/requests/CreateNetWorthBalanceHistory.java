package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateNetWorthBalanceHistory extends QueryRequestObject {

	public transient long id;
	public MenigaDecimal balance;
	public DateTime balanceDate;


	@Override
	public long getValueHash() {
		return 0;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		query.put("balance", Double.toString(this.balance.doubleValue()));
		query.put("balanceDate", fmt.print(this.balanceDate));

		return query;
	}
}
