package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.enums.BudgetType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgetEntries extends QueryRequestObject {
	public transient long id;
	public List<Long> categoryIds;
	public DateTime startDate;
	public DateTime endDate;
	public boolean allowOverlappingEntries;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (allowOverlappingEntries ? 1 : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		if (categoryIds != null && categoryIds.size() > 0) {
			StringBuilder bld = new StringBuilder();
			for (int i = 0; i < categoryIds.size(); i++) {
				if (i > 0) {
					bld.append(",");
				}
				bld.append(categoryIds.get(i));
			}
			map.put("categoryIds", bld.toString());
		}
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		if (startDate != null) {
			map.put("startDate", fmt.print(startDate));
		}
		if (endDate != null) {
			map.put("endDate", fmt.print(endDate));
		}
		map.put("allowOverlappingEntries", Boolean.toString(allowOverlappingEntries));

		return map;
	}
}
