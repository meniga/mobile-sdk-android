package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteUserCategory extends QueryRequestObject {

	public transient long id;
	public boolean deleteConnectedRules;
	public Long newCategoryId;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (deleteConnectedRules ? 1 : 0);
		result = 31 * result + (newCategoryId != null ? newCategoryId.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("deleteConnectedRules", Boolean.toString(this.deleteConnectedRules));
		if (this.newCategoryId != null) {
			map.put("newCategoryId", Long.toString(this.newCategoryId));
		}
		return map;
	}
}
