package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetAccounts extends QueryRequestObject {

	public boolean includeDisabled;
	public boolean includeHidden;

	@Override
	public long getValueHash() {
		int result = (includeDisabled ? 1 : 0);
		result = 31 * result + (includeHidden ? 1 : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		query.put("includeDisabled", Boolean.toString(includeDisabled));
		query.put("includeHidden", Boolean.toString(includeHidden));
		return query;
	}
}
