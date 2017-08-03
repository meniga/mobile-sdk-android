package com.meniga.sdk.webservices.requests;

import com.google.gson.Gson;
import com.meniga.sdk.interfaces.ValueHashable;

import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public abstract class QueryRequestObject implements ValueHashable {

	private static final Object lock = new Object();

	public String toJsonString(Gson gson) {
		String obj;
		synchronized (lock) {
			 obj = gson.toJson(this);
		}
		return obj;
	}

	/**
	 * To be overridden in declered classes since definition varies a lot
	 */
	public Map<String, String> toQueryMap() {
		return null;
	}
}
