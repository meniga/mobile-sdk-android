package com.meniga.sdk;

import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.transactions.Options;
import com.meniga.sdk.models.transactions.SeriesSelector;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.models.transactions.enums.TimeResolution;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTransactionsTest {

	@Test
	public void test() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("transactionsPage.json")));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		Task<MenigaTransactionPage> transactionsTask = MenigaTransaction.fetch(new TransactionsFilter.Builder().build()).getTask();
		transactionsTask.waitForCompletion();
		MenigaTransactionPage transactionPage = transactionsTask.getResult();

		Assert.assertNotNull(transactionPage);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}

	@Test
	public void testSeries() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		MockResponse mockResponse = new MockResponse().setBody(FileImporter.getJsonFileFromRaw("transactionseries.json"))
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.addHeader("Cache-Control", "no-cache");
		server.enqueue(mockResponse);
		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		List<SeriesSelector> ss = new ArrayList<>();
		ss.add(new SeriesSelector(new TransactionsFilter.Builder().periodTo(new DateTime()).periodFrom(new DateTime()).build()));
		Task<List<MenigaTransactionSeries>> menigaTransactionSeriesTask = MenigaTransactionSeries.fetch(
				new TransactionsFilter.Builder().periodTo(new DateTime()).periodFrom(new DateTime()).build(),
				new Options(TimeResolution.DAY,
						true,
						true,
						true),ss).getTask();
		menigaTransactionSeriesTask.waitForCompletion();
		List<MenigaTransactionSeries> menigaTransactionSeries = menigaTransactionSeriesTask.getResult();

		Assert.assertNotNull(menigaTransactionSeries);

		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}
}
