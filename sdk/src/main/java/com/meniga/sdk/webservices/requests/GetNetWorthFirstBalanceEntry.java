package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetNetWorthFirstBalanceEntry extends QueryRequestObject {

	public boolean excludeAccountsExcludedFromNetWorth;

	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("excludeAccountsExcludedFromNetWorth", Boolean.toString(excludeAccountsExcludedFromNetWorth));

		return map;
	}

	@Override
	public long getValueHash() {
		return -1;
	}
}
