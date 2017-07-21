package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateComment extends QueryRequestObject {

	public transient long transactionId;
	public String comment;

	@Override
	public long getValueHash() {
		return this.transactionId + this.comment.hashCode();
	}
}
