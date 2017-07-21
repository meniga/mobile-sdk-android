package com.meniga.sdk.webservices;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaWebException extends Exception {

	private final int responseCode;

	public MenigaWebException(String cause, int responseCode) {
		super(cause);
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}
}
