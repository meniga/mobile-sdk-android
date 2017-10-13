package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetChallenges extends QueryRequestObject {

	public boolean includeExpired;
	public boolean excludeSuggested;
	public boolean excludeAccepted;

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("includeExpired", Boolean.toString(includeExpired));
		map.put("excludeSuggested", Boolean.toString(excludeSuggested));
		map.put("excludeAccepted", Boolean.toString(excludeAccepted));
		return map;
	}

	@Override
	public long getValueHash() {
		int result = (includeExpired ? 1 : 0);
		result = 31 * result + (excludeSuggested ? 1 : 0);
		result = 31 * result + (excludeAccepted ? 1 : 0);
		return result;
	}
}
