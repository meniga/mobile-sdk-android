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
		int result = transactionTexts != null ? transactionTexts.hashCode() : 0;
		result = 31 * result + (recategorizeUnreadOnly != null ? recategorizeUnreadOnly.hashCode() : 0);
		result = 31 * result + (useSubTextInRecat != null ? useSubTextInRecat.hashCode() : 0);
		result = 31 * result + (markAsRead != null ? markAsRead.hashCode() : 0);
		return result;
	}
}
