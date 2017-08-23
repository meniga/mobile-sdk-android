package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AcceptChallenge extends QueryRequestObject {

	public UUID id;
	public transient MenigaDecimal targetAmount;

	@Override
	public long getValueHash() {
		return id.hashCode() + targetAmount.hashCode();
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		query.put("targetAmount", Double.toString(targetAmount.doubleValue()));
		return query;
	}
}
