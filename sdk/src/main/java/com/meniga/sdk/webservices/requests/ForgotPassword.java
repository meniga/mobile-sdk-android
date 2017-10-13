package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ForgotPassword extends QueryRequestObject {

	public String email;

	@Override
	public long getValueHash() {
		return email != null ? email.hashCode() : 0;
	}
}
