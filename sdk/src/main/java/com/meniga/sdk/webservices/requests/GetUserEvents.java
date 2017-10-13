package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetUserEvents extends QueryRequestObject {

	@Override
	public long getValueHash() {
		return -1;
	}

	@Override
	public Map<String, String> toQueryMap() {
		return new HashMap<>();
	}


}
