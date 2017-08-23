package com.meniga.sdk;

import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaBudgetTest {

	@Test
	public void test() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budgets.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);

		List<Long> ids = new ArrayList<>();
		ids.add(1L);

		Task<List<MenigaBudget>> budgetTask = MenigaBudget.fetch(ids, ids, BudgetType.PLANNING).getTask();
		budgetTask.waitForCompletion();
		List<MenigaBudget> budgets = budgetTask.getResult();

		Assert.assertTrue(server.takeRequest().getPath().equals("/v1/budgets?accountIds=1&ids=1&type=planning"));

		Assert.assertNotNull(budgets);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testSingleBudget() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budget.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);


		Task<MenigaBudget> budgetTask = MenigaBudget.fetch(1L, null).getTask();
		budgetTask.waitForCompletion();
		MenigaBudget budget = budgetTask.getResult();


		Assert.assertNotNull(budget);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testEntries() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budget.json")));
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budgetentries.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);


		Task<MenigaBudget> budgetTask = MenigaBudget.fetch(1L, null).getTask();
		budgetTask.waitForCompletion();
		MenigaBudget budget = budgetTask.getResult();

		Assert.assertTrue(server.takeRequest().getPath().equals("/v1/budgets/1"));

		Task<List<MenigaBudgetEntry>> entrieTask = budget.fetchEntries(null).getTask();
		entrieTask.waitForCompletion();
		List<MenigaBudgetEntry> entries = entrieTask.getResult();

		Assert.assertTrue(server.takeRequest().getPath().equals("/v1/budgets/1/entries"));

		Assert.assertNotNull(entries);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testCreateBudget() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budget.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);


		Task<MenigaBudget> createBudgetTask = MenigaBudget.create(BudgetType.PLANNING,"Test Budget", "Test Budget", Arrays.asList(1L),false, BudgetPeriod.WEEK,0).getTask();
		createBudgetTask.waitForCompletion();
		MenigaBudget budget = createBudgetTask.getResult();
		RecordedRequest request = server.takeRequest();
		Assert.assertTrue(request.getPath().equals("/v1/budgets"));
		Assert.assertTrue(request.getMethod().equals("POST"));
		Assert.assertTrue(request.getBody().readUtf8().equals("{\"type\":\"planning\",\"period\":\"week\",\"name\":\"Test Budget\",\"description\":\"Test Budget\",\"accountIds\":[1],\"isDefault\":false,\"offset\":0}"));
		Assert.assertNotNull(budget);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testDeleteBudget() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody("{}"));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);


		Task<Void> createBudgetTask = MenigaBudget.delete(1L).getTask();
		createBudgetTask.waitForCompletion();
		RecordedRequest request = server.takeRequest();
		Assert.assertTrue(request.getPath().equals("/v1/budgets/1"));
		Assert.assertTrue(request.getMethod().equals("DELETE"));

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testResetBudget() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody("{}"));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);


		Task<Void> resetBudgetTask = MenigaBudget.reset(1L).getTask();
		resetBudgetTask.waitForCompletion();
		RecordedRequest request = server.takeRequest();
		Assert.assertTrue(request.getPath().equals("/v1/budgets/1/reset"));
		Assert.assertTrue(request.getMethod().equals("POST"));

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

}
