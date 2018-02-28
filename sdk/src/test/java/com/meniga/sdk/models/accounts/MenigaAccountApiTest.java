/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.models.accounts.enums.AccountCategory;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class MenigaAccountApiTest {

	private MockWebServer server;

	@Before
	public void setUp() throws Exception {
		server = new MockWebServer();
		server.start();
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void test() throws Exception {
		server.enqueue(mockResponse("accounts.json"));
		server.enqueue(mockResponse("account.json"));

		Task<List<MenigaAccount>> accountsTask = MenigaAccount.fetch().getTask();
		accountsTask.waitForCompletion();

		List<MenigaAccount> accounts = accountsTask.getResult();
		Assert.assertNotNull(accounts);
		Task<MenigaAccount> accountTask = MenigaAccount.fetch(1).getTask();
		accountTask.waitForCompletion();
		MenigaAccount account = accountTask.getResult();
		Assert.assertNotNull(account);
	}

	@Test
	public void testAccountTypes() throws Exception {
		server.enqueue(mockResponse("accounttypes.json"));

		Task<List<MenigaAccountType>> accountsTask = MenigaAccount.fetchAccountTypes().getTask();
		accountsTask.waitForCompletion();

		List<MenigaAccountType> accounts = accountsTask.getResult();
		Assert.assertNotNull(accounts);
	}

	@Test
	public void shouldFetchAccountCategories() throws Exception {
		server.enqueue(mockResponse("accountcategories.json"));

        Task<List<AccountCategory>> task = MenigaAccount.fetchCategories().getTask();
        task.waitForCompletion();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/accountcategories");
        List<AccountCategory> categories = task.getResult();
        assertThat(categories).isNotEmpty();
    }

	private MockResponse mockResponse(String path) throws IOException {
		return new MockResponse().setBody(FileImporter.getJsonFileFromRaw(path));
	}
}
