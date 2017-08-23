package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.budget.enums.BudgetType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgets extends QueryRequestObject {

	public transient List<Long> ids;
	public transient List<Long> accountIds;
	public transient BudgetType type;

	@Override
	public long getValueHash() {
		int result = ids != null ? ids.hashCode() : 0;
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}


	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		String idsString = createList(this.ids);
		if(idsString.length() != 0){
			query.put("ids", idsString);
		}
		String accountIdsString = createList(this.accountIds);
		if(accountIdsString.length() != 0){
			query.put("accountIds", accountIdsString);
		}
		if(type != null)
			query.put("type", type.toString());
		return query;
	}

	private String createList(List<Long> listOfLongs){
		StringBuilder builder = new StringBuilder();
		if(listOfLongs != null && listOfLongs.size() > 0) {
			for (long id : listOfLongs) {
				if (builder.length() != 0) {
					builder.append(",");
				}
				builder.append(String.valueOf(id));
			}
		}
		return builder.toString();
	}
}
