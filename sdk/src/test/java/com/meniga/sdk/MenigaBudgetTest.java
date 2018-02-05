package com.meniga.sdk;

import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static java.util.Collections.singletonList;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaBudgetTest {

    private MockWebServer server;

    @Before
    public void setUp() throws IOException {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        server = new MockWebServer();

        // Start the server.
        server.start();

        // Ask the server for its URL. You'll need this to make HTTP requests.
        HttpUrl baseUrl = server.url("/v1");
        MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
        MenigaSDK.init(settings);
    }

    @After
    public void tearDown() throws IOException {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

    @Test
    public void testFetchBudgetByType() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budgets.json")));

        Task<List<MenigaBudget>> budgetTask = MenigaBudget.fetch(BudgetType.PLANNING).getTask();
        budgetTask.waitForCompletion();
        List<MenigaBudget> budgets = budgetTask.getResult();

        // TODO Add ids and accountIds missing (available in the API but not here)
        Assert.assertEquals("/v1/budgets?type=Planning", server.takeRequest().getPath());
        Assert.assertNotNull(budgets);
    }

    @Ignore("Fetching by budget id was removed from the SDK at some point. Should we add it again?")
    @Test
    public void testSingleBudget() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budget.json")));

        Task<List<MenigaBudget>> budgetTask = MenigaBudget.fetch().getTask();
        budgetTask.waitForCompletion();
        List<MenigaBudget> budget = budgetTask.getResult();

        Assert.assertNotNull(budget);
    }

    @Test
    public void testEntries() throws Exception {
        // Schedule some responses.;
        server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budgetentries.json")));

        Task<List<MenigaBudgetEntry>> entriesTask = MenigaBudgetEntry.fetch(1).getTask();
        entriesTask.waitForCompletion();
        List<MenigaBudgetEntry> entries = entriesTask.getResult();

        // TODO Add categoryIds, endDate, startDate, allowOverlappingEntries, includeOptionalHistoricalData?
        Assert.assertEquals("/v1/budgets/1/entries?endDate=2018-02-28T00:00:00.000Z&allowOverlappingEntries=true&startDate=2018-02-01T00:00:00.000Z", server.takeRequest().getPath());
        Assert.assertNotNull(entries);
    }

    @Test
    public void testCreateBudget() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody(FileImporter.getJsonFileFromRaw("budget.json")));

        Task<MenigaBudget> createBudgetTask = MenigaBudget.create(BudgetType.PLANNING, "Test Budget", "Test Budget", singletonList(1L), BudgetPeriod.WEEK, 0).getTask();
        createBudgetTask.waitForCompletion();

        MenigaBudget budget = createBudgetTask.getResult();
        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("/v1/budgets", request.getPath());
        Assert.assertEquals("POST", request.getMethod());
        JSONAssert.assertEquals("{\"type\":\"Planning\",\"period\":\"Week\",\"name\":\"Test Budget\",\"description\":\"Test Budget\",\"accountIds\":[1],\"offset\":0}", request.getBody().readUtf8(), false);
        Assert.assertNotNull(budget);
    }

    @Test
    public void testDeleteBudget() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody("{}"));

        Task<Void> createBudgetTask = MenigaBudget.delete(1L).getTask();
        createBudgetTask.waitForCompletion();
        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("/v1/budgets/1", request.getPath());
        Assert.assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testResetBudget() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody("{}"));

        Task<Void> resetBudgetTask = MenigaBudget.reset(1L).getTask();
        resetBudgetTask.waitForCompletion();
        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("/v1/budgets/1/reset", request.getPath());
        Assert.assertEquals("POST", request.getMethod());
    }
}
