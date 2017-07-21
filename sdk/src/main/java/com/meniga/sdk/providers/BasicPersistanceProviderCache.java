package com.meniga.sdk.providers;

import com.meniga.sdk.interfaces.PersistenceProvider;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import java.util.HashMap;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@SuppressWarnings("unchecked")
public class BasicPersistanceProviderCache implements PersistenceProvider {
	private HashMap<Object, Object> map = new HashMap<>();

	@Override
	public <T> T fetch(QueryRequestObject key) {
		if (this.map.containsKey(key.getValueHash()))
			return (T) this.map.get(key.getValueHash());
		return null;
	}

	@Override
	public void save(QueryRequestObject key, Object value) {
		this.map.put(key.getValueHash(), value);
	}

	@Override
	public boolean hasKey(QueryRequestObject key) {
		return this.map.containsKey(key.getValueHash());
	}

	@Override
	public void clear() {
		this.map.clear();
	}
}
