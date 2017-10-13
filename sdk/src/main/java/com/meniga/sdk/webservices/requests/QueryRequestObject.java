package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.interfaces.ValueHashable;

import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public abstract class QueryRequestObject implements ValueHashable {

	/**
	 * To be overridden in declared classes since definition varies a lot
	 */
	public Map<String, String> toQueryMap() {
		return null;
	}
}
