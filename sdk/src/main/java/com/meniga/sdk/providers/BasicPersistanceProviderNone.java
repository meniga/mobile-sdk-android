package com.meniga.sdk.providers;

import com.meniga.sdk.interfaces.PersistenceProvider;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BasicPersistanceProviderNone implements PersistenceProvider {
	@Override
	public <T> T fetch(QueryRequestObject key) {
		return null;
	}

	@Override
	public void save(QueryRequestObject key, Object value) {
	}

	@Override
	public boolean hasKey(QueryRequestObject key) {
		return false;
	}

	@Override
	public void clear() {
	}
}
