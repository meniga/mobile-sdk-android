package com.meniga.sdk;

import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountType;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;


import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaAccountTest  {

	@Test
	public void test() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("accounts.json")));
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("account.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		Task<List<MenigaAccount>> accountsTask = MenigaAccount.fetch().getTask();
		accountsTask.waitForCompletion();
		List<MenigaAccount> accounts = accountsTask.getResult();

		Assert.assertNotNull(accounts);

		Task<MenigaAccount> accountTask = MenigaAccount.fetch(1).getTask();
		accountTask.waitForCompletion();
		MenigaAccount account = accountTask.getResult();
		Assert.assertNotNull(account);
		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testAccountTypes() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("accounttypes.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		Task<List<MenigaAccountType>> accountsTask = MenigaAccount.fetchAccountTypes().getTask();
		accountsTask.waitForCompletion();
		List<MenigaAccountType> accounts = accountsTask.getResult();

		Assert.assertNotNull(accounts);
		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

}
