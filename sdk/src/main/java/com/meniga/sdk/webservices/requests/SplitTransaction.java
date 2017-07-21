package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class SplitTransaction extends QueryRequestObject {

	public transient long transactionId;
	public MenigaDecimal amount;
	public String text;
	public long categoryId;
	public boolean isFlagged;

	@Override
	public long getValueHash() {
		StringBuilder bld = new StringBuilder();
		long rest = this.transactionId + this.categoryId;
		bld.append(rest);
		try {
			return Long.parseLong(bld.toString());
		} catch (Exception ex) {
			return this.transactionId + (this.amount == null ? 0 : this.amount.intValue());
		}
	}
}
