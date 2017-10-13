package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DeleteTransactions extends QueryRequestObject {

	public List<Long> transactionIds;

	@Override
	public long getValueHash() {
		return transactionIds != null ? transactionIds.hashCode() : 0;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> delKeys = new HashMap<>();
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < this.transactionIds.size(); i++) {
			if (i > 0) {
				bld.append(",");
			}
			bld.append(this.transactionIds.get(i));
		}
		delKeys.put("transactionIds", bld.toString());
		return delKeys;
	}
}
