package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetRedemptions extends QueryRequestObject {

	public Integer skip;
	public Integer take;
	public DateTime dateFrom;
	public DateTime dateTo;

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		if (this.dateFrom != null) {
			map.put("dateFrom", this.dateFrom.toString());
		}
		if (this.dateTo != null) {
			map.put("dateTo", this.dateTo.toString());
		}
		if (this.skip != null) {
			map.put("skip", Integer.toString(this.skip));
		}
		if (this.take != null) {
			map.put("take", Integer.toString(this.take));
		}
		return map;
	}

	@Override
	public long getValueHash() {
		int result = skip != null ? skip.hashCode() : 0;
		result = 31 * result + (take != null ? take.hashCode() : 0);
		result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
		result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
		return result;
	}
}
