package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.enums.BudgetType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgets extends QueryRequestObject {
	public List<Long> ids;
	public List<Long> accountIds;
	public BudgetType type;

	@Override
	public long getValueHash() {
		int result = ids != null ? ids.hashCode() : 0;
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		if (ids != null && ids.size() > 0) {
			StringBuilder bld = new StringBuilder();
			for (int i = 0; i < ids.size(); i++) {
				if (i > 0) {
					bld.append(",");
				}
				bld.append(ids.get(i));
			}
			map.put("ids", bld.toString());
		}
		if (ids != null && accountIds.size() > 0) {
			StringBuilder bld = new StringBuilder();
			for (int i = 0; i < accountIds.size(); i++) {
				if (i > 0) {
					bld.append(",");
				}
				bld.append(ids.get(i));
			}
			map.put("accountIds", bld.toString());
		}
		if (type != null) {
			map.put("type", type.toString());
		}

		return map;
	}
}
