package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetAccounts extends QueryRequestObject {
	@Override
	public long getValueHash() {
		return this.getClass().toString().hashCode();
	}

	@Override
	public Map<String, String> toQueryMap() {
		return new HashMap<>();
	}
}
