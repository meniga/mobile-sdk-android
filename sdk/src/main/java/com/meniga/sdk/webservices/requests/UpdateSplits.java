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
		return -1;
	}
}
