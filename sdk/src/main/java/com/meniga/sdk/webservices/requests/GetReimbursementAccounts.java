package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class GetReimbursementAccounts extends QueryRequestObject {

	public Boolean includeInactive;
	public Integer skip;
	public Integer take;

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		if (this.includeInactive != null) {
			map.put("includeInactive", Boolean.toString(this.includeInactive));
		}
		if (this.skip != null) {
			map.put("skip", Integer.toString(this.skip));
		}
		if (this.take != null) {
			map.put("take", Integer.toString(this.take));
		}
		return map;
	}

	@Override
	public long getValueHash() {
		return -1;
	}
}
