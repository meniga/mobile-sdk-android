package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetChallengeHistory extends QueryRequestObject {
	public UUID id;
	public int skip;
	public int take;

	@Override
	public long getValueHash() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + skip;
		result = 31 * result + take;
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("skip", Integer.toString(skip));
		map.put("take", Integer.toString(take));
		return map;
	}
}
