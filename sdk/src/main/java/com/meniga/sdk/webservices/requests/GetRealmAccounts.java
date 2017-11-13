package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetRealmAccounts extends QueryRequestObject {
	public long realmUserId;
	public transient String sessionToken;

    @Override
	public long getValueHash() {
		return (int) (realmUserId ^ (realmUserId >>> 32));
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		if (sessionToken != null) {
			query.put("sessionToken", sessionToken);
		}
		return query;
	}
}
