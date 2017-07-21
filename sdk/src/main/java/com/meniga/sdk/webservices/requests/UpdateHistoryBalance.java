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
		return this.accountId + this.balanceId;
	}
}
