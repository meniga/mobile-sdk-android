package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RegisterUser extends QueryRequestObject {

	public String email;
	public String password;
	public String culture;

	@Override
	public long getValueHash() {
		int result = email != null ? email.hashCode() : 0;
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (culture != null ? culture.hashCode() : 0);
		return result;
	}
}
