package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class RecategorizeTransactions extends QueryRequestObject {

	public List<String> transactionTexts;
	public Boolean recategorizeUnreadOnly;
	public Boolean useSubTextInRecat;
	public Boolean markAsRead;

	@Override
	public long getValueHash() {
		// not used for POST only method recategorize transactions
		return -1;
	}
}
