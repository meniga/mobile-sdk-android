package com.meniga.sdk.providers;

import android.util.Base64;

import com.meniga.sdk.ErrorHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * <h1>Basic authenticator implementation</h1> <br>
 * Provides an implementation on top of the Authenticator interface for BASIC authentication. <br>
 * Supports settings a username and password <br>
 * Copyright 2017 Meniga Iceland Inc. <br>
 *
 * @version 2.0
 */
public class BasicAuthenticator implements Authenticator {

	private String basicAuthenticationHeader;

	public void setUserNameAndPassword(String username, String password) {
		String encodedHeader = encode(username, password);
		this.basicAuthenticationHeader = "Basic " + encodedHeader;
	}

	private String encode(String username, String password) {
		String comb = username + ":" + password;

		byte[] data = null;

		try {
			data = comb.trim().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			ErrorHandler.reportAndHandle(e);
			// If its a Java String we cant possibly produce this UnsupportedEncodingException;
		}
		return Base64.encodeToString(data, Base64.DEFAULT);
	}

	@Override
	public Request authenticate(Route route, Response response) throws IOException {
		return response.request().newBuilder()
				.header("Authorization", this.basicAuthenticationHeader.trim())
				.header("X-XSRF-Header", "true")
				.build();
	}
}
