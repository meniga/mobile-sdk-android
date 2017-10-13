package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateTransaction extends QueryRequestObject {

	public DateTime date;
	public String text;
	public MenigaDecimal amount;
	public long categoryId;
	public boolean setAsRead;

	@Override
	public long getValueHash() {
		int result = date != null ? date.hashCode() : 0;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
		result = 31 * result + (setAsRead ? 1 : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		query.put("date", fmt.print(this.date));
		query.put("text", this.text);
		query.put("amount", Double.toString(this.amount.doubleValue()));
		query.put("categoryId", Long.toString(this.categoryId));
		query.put("setAsRead", Boolean.toString(this.setAsRead));

		return query;
	}
}
