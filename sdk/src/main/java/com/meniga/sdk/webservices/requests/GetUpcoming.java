package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetUpcoming extends QueryRequestObject {
	public DateTime from;
	public DateTime to;
	public List<Long> accountIds;
	public Boolean includeDetails;
	public Boolean watchedOnly;
	public Long recurringPatternId;

	@Override
	public long getValueHash() {
		int result = from != null ? from.hashCode() : 0;
		result = 31 * result + (to != null ? to.hashCode() : 0);
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (includeDetails != null ? includeDetails.hashCode() : 0);
		result = 31 * result + (watchedOnly != null ? watchedOnly.hashCode() : 0);
		result = 31 * result + (recurringPatternId != null ? recurringPatternId.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();

		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

		query.put("dateFrom", fmt.print(from));
		query.put("dateTo", fmt.print(to));

		if(accountIds != null && accountIds.size() > 0) {
			String ids = "";
			for(int i = 0; i < accountIds.size(); i++) {
				if(i > 0) {
					ids += ",";
				}
				ids += accountIds.get(i);
			}
		}

		if(includeDetails != null) {
			query.put("includeDetails", Boolean.toString(includeDetails));
		}

		if(watchedOnly != null) {
			query.put("watchedOnly", Boolean.toString(watchedOnly));
		}

		if(recurringPatternId != null) {
			query.put("recurringPatternId", Long.toString(recurringPatternId));
		}

		return query;
	}
}
