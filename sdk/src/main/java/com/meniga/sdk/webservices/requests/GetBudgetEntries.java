package com.meniga.sdk.webservices.requests;

import com.annimon.stream.Optional;
import com.meniga.sdk.helpers.DateTimeUtils;
import com.meniga.sdk.helpers.Joiner;

import org.joda.time.DateTime;

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
	public boolean includeOptionalHistoricalData;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (allowOverlappingEntries ? 1 : 0);
		result = 31 * result + (includeOptionalHistoricalData ? 1 : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> queryMap = new HashMap<>();
		Optional.ofNullable(categoryIds)
				.executeIfPresent(categoryIds ->
						queryMap.put("categoryIds", Joiner.join(categoryIds, ",")));

		Optional.ofNullable(startDate)
				.map(DateTimeUtils::toString)
				.executeIfPresent(dateTime -> queryMap.put("startDate", dateTime));

		Optional.ofNullable(endDate)
				.map(DateTimeUtils::toString)
				.executeIfPresent(dateTime -> queryMap.put("endDate", dateTime));

		queryMap.put("allowOverlappingEntries", Boolean.toString(allowOverlappingEntries));
		queryMap.put("includeOptionalHistoricalData", Boolean.toString(includeOptionalHistoricalData));

		return queryMap;
	}
}
