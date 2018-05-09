/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.transactions;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.models.transactions.enums.TimeResolution;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.providers.tasks.TaskAssertions;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;

import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class MenigaTransactionsTest {

	@Rule
	public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

	@Before
	public void setUp() {
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
		MenigaSDK.init(settings);
	}

	@Test
	public void test() {
		server.enqueue(mockResponse("transactionsPage.json"));

		Task<MenigaTransactionPage> transactionsTask = MenigaTransaction.fetch(new TransactionsFilter.Builder().build()).getTask();

		TaskAssertions.assertThat(transactionsTask).isSuccessful();
		MenigaTransactionPage transactionPage = transactionsTask.getResult();
		assertThat(transactionPage).isNotNull();
	}

	@Test
	public void testSeries() {
		MockResponse mockResponse = mockResponse("transactionseries.json")
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.addHeader("Cache-Control", "no-cache");
		server.enqueue(mockResponse);

		List<SeriesSelector> seriesSelectors = new ArrayList<>();
		seriesSelectors.add(new SeriesSelector(new TransactionsFilter.Builder().periodTo(new DateTime()).periodFrom(new DateTime()).build()));
		Task<List<MenigaTransactionSeries>> task = MenigaTransactionSeries.fetch(
				new TransactionsFilter.Builder().periodTo(new DateTime()).periodFrom(new DateTime()).build(),
				new Options(TimeResolution.DAY,
						true,
						true,
						true), seriesSelectors).getTask();

		TaskAssertions.assertThat(task).isSuccessful();
		List<MenigaTransactionSeries> menigaTransactionSeries = task.getResult();
		assertThat(menigaTransactionSeries).isNotNull();
	}
}
