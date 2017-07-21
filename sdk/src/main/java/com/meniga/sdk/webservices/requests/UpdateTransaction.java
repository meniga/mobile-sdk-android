package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateTransaction extends QueryRequestObject {
	public transient long transactionId;
	public DateTime date;
	public String text;
	public MenigaDecimal amount;
	public long categoryId;
	public boolean hasUncertainCategorization;
	public boolean useSubTextInRecat;
	public Boolean isRead;
	public Boolean isFlagged;
	public String userData;

	@Override
	public long getValueHash() {
		return this.transactionId;
	}
}
