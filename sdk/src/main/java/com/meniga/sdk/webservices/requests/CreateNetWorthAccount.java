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
public class CreateNetWorthAccount extends QueryRequestObject {

	public MenigaDecimal initialBalance;
	public MenigaDecimal balance;
	public String accountIdentifier;
	public String displayName;
	public String networthType;
	public DateTime initialBalanceDate;

	@Override
	public long getValueHash() {
		int result = initialBalance != null ? initialBalance.hashCode() : 0;
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (accountIdentifier != null ? accountIdentifier.hashCode() : 0);
		result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
		result = 31 * result + (networthType != null ? networthType.hashCode() : 0);
		result = 31 * result + (initialBalanceDate != null ? initialBalanceDate.hashCode() : 0);
		return result;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		query.put("initialBalance", Double.toString(this.initialBalance.doubleValue()));
		query.put("balance", Double.toString(this.balance.doubleValue()));
		query.put("accountIdentifier", this.accountIdentifier);
		query.put("displayName", this.displayName);
		query.put("networthType", this.networthType);
		query.put("initialBalanceDate", fmt.print(this.initialBalanceDate));

		return query;
	}

}
