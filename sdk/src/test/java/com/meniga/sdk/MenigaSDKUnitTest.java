package com.meniga.sdk;

import com.meniga.sdk.providers.BasicAuthenticator;
import com.meniga.sdk.providers.BasicPersistanceProviderNone;

import org.junit.Test;

import okhttp3.HttpUrl;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaSDKUnitTest {
	@Test
	public void testInitializing() {
		MenigaSettings settings = new MenigaSettings.Builder()
				.endpoint(HttpUrl.parse("http://example.com"))
				.authenticator(new BasicAuthenticator())
				.persistenceProvider(new BasicPersistanceProviderNone())
				.build();

		MenigaSDK.init(settings);
	}
}
