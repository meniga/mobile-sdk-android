package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
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
		return this.dateFrom.getMillis() + this.dateTo.getMillis();
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();

		LocalDateTime dateTimeFrom = new LocalDateTime(this.dateFrom.getMillis());
		LocalDateTime dateTimeTo = new LocalDateTime(this.dateTo.getMillis());

		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

		query.put("dateFrom", fmt.print(dateTimeFrom));
		query.put("dateTo", fmt.print(dateTimeTo));

		if (this.take > 0) {
			query.put("skip", Integer.toString(this.skip));
			query.put("take", Integer.toString(this.take));
		}

		return query;
	}
}
