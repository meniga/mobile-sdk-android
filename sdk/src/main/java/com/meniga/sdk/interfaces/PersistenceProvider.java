package com.meniga.sdk.interfaces;

import com.meniga.sdk.webservices.requests.QueryRequestObject;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface PersistenceProvider {

	<T> T fetch(QueryRequestObject key);

	<T> void save(QueryRequestObject key, T value);

	boolean hasKey(QueryRequestObject key);

	void clear();
}
