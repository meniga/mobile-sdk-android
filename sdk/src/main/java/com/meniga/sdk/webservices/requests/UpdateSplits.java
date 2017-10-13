package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateSplits extends QueryRequestObject {

	public transient long id;
	public MenigaDecimal amount;
	public String text;
	public long categoryId;
	public boolean isFlagged;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
		result = 31 * result + (isFlagged ? 1 : 0);
		return result;
	}
}
