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
		if(this.dateFrom != null) {
			map.put("dateFrom", this.dateFrom.toString());
		}
		if(this.dateTo != null) {
			map.put("dateTo", this.dateTo.toString());
		}
		if(this.skip != null) {
			map.put("skip", Integer.toString(this.skip));
		}
		if(this.take != null) {
			map.put("take", Integer.toString(this.take));
		}
		return map;
	}

	@Override
	public long getValueHash() {
		return this.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GetRedemptions that = (GetRedemptions) o;

		if (skip != null ? !skip.equals(that.skip) : that.skip != null) return false;
		if (take != null ? !take.equals(that.take) : that.take != null) return false;
		if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null)
			return false;
		return dateTo != null ? dateTo.equals(that.dateTo) : that.dateTo == null;

	}

	@Override
	public int hashCode() {
		int result = skip != null ? skip.hashCode() : 0;
		result = 31 * result + (take != null ? take.hashCode() : 0);
		result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
		result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
		return result;
	}
}
