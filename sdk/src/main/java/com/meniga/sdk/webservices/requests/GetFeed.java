package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetFeed extends QueryRequestObject {

	public DateTime dateFrom;
	public DateTime dateTo;
	public int skip = 0;
	public int take = -1;

	@Override
	public long getValueHash() {
		int result = dateFrom != null ? dateFrom.hashCode() : 0;
		result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
		result = 31 * result + skip;
		result = 31 * result + take;
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();

		DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

		query.put("dateFrom", formatter.print(dateFrom));
		query.put("dateTo", formatter.print(dateTo));

		if (take > 0) {
			query.put("skip", Integer.toString(skip));
			query.put("take", Integer.toString(take));
		}

		return query;
	}
}
