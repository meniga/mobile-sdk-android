package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.organizations.MenigaRealmAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AddRealmAccountsToMeniga extends QueryRequestObject {

	public transient long realmUserId;
	public List<MenigaRealmAccount> realmAccounts;
	public transient String sessionToken;

	@Override
	public long getValueHash() {
		int result = (int) (realmUserId ^ (realmUserId >>> 32));
		result = 31 * result + (realmAccounts != null ? realmAccounts.hashCode() : 0);
		result = 31 * result + (sessionToken != null ? sessionToken.hashCode() : 0);
		return result;
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
