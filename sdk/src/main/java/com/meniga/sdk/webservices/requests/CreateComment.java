package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateComment extends QueryRequestObject {

	public transient long transactionId;
	public String comment;

	@Override
	public long getValueHash() {
		int result = (int) (transactionId ^ (transactionId >>> 32));
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}

}
