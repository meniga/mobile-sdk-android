package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetMerchants extends QueryRequestObject {

	public List<Long> ids;

	@Override
	public long getValueHash() {
		return ids != null ? ids.hashCode() : 0;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		if (ids == null || ids.size() == 0)
			return map;
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			if (i > 0) {
				stb.append(",");
			}
			stb.append(ids.get(i));
		}

		map.put("merchantIds", stb.toString());

		return map;
	}
}
