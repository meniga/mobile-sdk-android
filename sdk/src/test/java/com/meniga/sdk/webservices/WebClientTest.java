package com.meniga.sdk.webservices;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.providers.BasicAuthenticator;

import junit.framework.Assert;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import okhttp3.HttpUrl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class WebClientTest {

	@Test(expected = NullPointerException.class)
	public void testWebClientFailureNotInitialized() {
		MenigaSDK.init(null);
	}

	@Test
	public void testClient() {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.build();
		MenigaSDK.init(settings);
		assertThat(MenigaSDK.executor().getApis())
				.containsKey(Service.ALL)
				.containsKey(Service.BUDGET);
	}

	@Test
	public void testClients() {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.addEndpointForService(Service.TRANSACTIONS, "http://example.transactions.com")
				.build();
		MenigaSDK.init(settings);
		assertThat(MenigaSDK.executor().getApis())
				.containsKey(Service.ALL)
				.containsKey(Service.BUDGET)
				.containsKey(Service.TRANSACTIONS);
	}
}
