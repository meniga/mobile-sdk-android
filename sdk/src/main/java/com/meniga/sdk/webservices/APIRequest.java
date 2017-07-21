package com.meniga.sdk.webservices;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class APIRequest {

	public static Result<Object> genericRequest(HttpMethod method, String path, String body, Map<String, String> query) {
		if (method == HttpMethod.GET && query == null) {
			query = new HashMap<>();
		}
		return MenigaSDK.executor().genericRequest(method, path, body, query);
	}

	public static Result<Object> genericRequest(HttpMethod method, String path, String body) {
		return MenigaSDK.executor().genericRequest(method, path, body, new HashMap<String, String>());
	}

	public static Result<Object> genericRequest(HttpMethod method, String path) {
		String body = null;
		if(method == HttpMethod.POST) {
			body = "";
		}
		return MenigaSDK.executor().genericRequest(method, path, body, new HashMap<String, String>());
	}
}
