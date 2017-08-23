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
		return -1;
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
