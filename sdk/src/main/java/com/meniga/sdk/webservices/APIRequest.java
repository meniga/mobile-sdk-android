package com.meniga.sdk.webservices;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class APIRequest {
	public static Result<Object> genericRequest(HttpMethod method, String path) {
		return genericRequest(method,  path, null, null, null);
	}

	public static Result<Object> genericRequest(HttpMethod method, String path, Map<String, String> headers) {
		return genericRequest(method,  path, headers, null, null);
	}

	public static Result<Object> genericRequest(HttpMethod method, String path, Map<String, String> headers, Object body) {
		return genericRequest(method,  path, headers, body, null);
	}

	public static Result<Object> genericRequest(HttpMethod method, String path, Map<String, String> headers, Object body, Map<String, String> query) {
		if (query == null) {
			query = new HashMap<>();
		}
		if (headers == null) {
			headers = new HashMap<>();
		}
		return MenigaSDK.executor().genericRequest(method, path, headers, body, query);
	}
}
