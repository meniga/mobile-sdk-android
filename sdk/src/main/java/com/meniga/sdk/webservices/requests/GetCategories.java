package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetCategories extends QueryRequestObject {
	public Boolean isPublic;
	public boolean flat = true;

	@Override
	public long getValueHash() {
		return this.getClass().getName().hashCode() + (this.isPublic == null ? 0 : this.isPublic ? 1 : 2);
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		if (this.isPublic != null) {
			query.put("isPublic", Boolean.toString(this.isPublic));
		}
		return query;
	}
}
