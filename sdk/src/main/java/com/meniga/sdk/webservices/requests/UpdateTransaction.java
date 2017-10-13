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
		int result = (int) (transactionId ^ (transactionId >>> 32));
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
		result = 31 * result + (hasUncertainCategorization ? 1 : 0);
		result = 31 * result + (useSubTextInRecat ? 1 : 0);
		result = 31 * result + (isRead != null ? isRead.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (userData != null ? userData.hashCode() : 0);
		return result;
	}
}
