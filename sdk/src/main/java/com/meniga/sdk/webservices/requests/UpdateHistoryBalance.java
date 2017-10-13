package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateHistoryBalance extends QueryRequestObject {

	public transient long accountId;
	public transient long balanceId;
	public MenigaDecimal balance;
	public DateTime balanceDate;

	@Override
	public long getValueHash() {
		int result = (int) (accountId ^ (accountId >>> 32));
		result = 31 * result + (int) (balanceId ^ (balanceId >>> 32));
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (balanceDate != null ? balanceDate.hashCode() : 0);
		return result;
	}

}
