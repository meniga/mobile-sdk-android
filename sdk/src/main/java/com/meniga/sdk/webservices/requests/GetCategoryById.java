package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetCategoryById extends QueryRequestObject {

	public long categoryId;
	public transient String culture;

	@Override
	public long getValueHash() {
		int result = (int) (categoryId ^ (categoryId >>> 32));
		result = 31 * result + (culture != null ? culture.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		if (culture != null) {
			query.put("culture", culture);
		}
		return query;
	}
}
