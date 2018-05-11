/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.budget;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.providers.tasks.Task;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class MenigaBudgetTest {

    @Rule
    public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

    @Before
    public void setUp() {
        MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
        MenigaSDK.init(settings);

        DateTimeUtils.setCurrentMillisFixed(DateTime.parse("2018-02-01").getMillis());
    }

    @Test
    public void testFetchBudgets() {
        server.enqueue(mockResponse("budgets.json"));
        FetchBudgetsFilter parameters = new FetchBudgetsFilter();
        parameters.setIds(asList(1L, 2L));
        parameters.setAccountIds(asList(10L, 20L));
        parameters.setType(BudgetType.PLANNING);

        Task<List<MenigaBudget>> budgetTask = MenigaBudget.fetch(parameters).getTask();

        assertThat(budgetTask).isSuccessful();
        assertThat(server.takeRequest().getPath()).isEqualTo("/v1/budgets?ids=1,2&accountIds=10,20&type=Planning");
        List<MenigaBudget> budgets = budgetTask.getResult();
        assertThat(budgets).isNotNull();
    }

    @Test
    public void testCreatePlanningBudget() throws Exception {
        server.enqueue(mockResponse("budget.json"));

        MenigaBudget budget = createMenigaBudgetTask(BudgetType.PLANNING).getResult();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets");
        assertThat(request.getMethod()).isEqualTo("POST");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.type", equalTo("Planning"))
                .assertThat("$.period", equalTo("Month"))
                .assertThat("$.name", equalTo("Test Budget"))
                .assertThat("$.description", equalTo("Test Budget"))
                .assertThat("$.accountIds", equalTo(singletonList(1)))
                .assertThat("$.offset", equalTo(0));
        assertThat(budget).isNotNull();
    }

    @Test
    public void testCreateBudget() throws Exception {
        server.enqueue(mockResponse("budget.json"));

        MenigaBudget budget = createMenigaBudgetTask(BudgetType.BUDGET).getResult();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets");
        assertThat(request.getMethod()).isEqualTo("POST");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.type", equalTo("Budget"))
                .assertThat("$.name", equalTo("Test Budget"))
                .assertThat("$.description", equalTo("Test Budget"))
                .assertThat("$.accountIds", equalTo(singletonList(1)));
        assertThat(budget).isNotNull();
    }

    @Test
    public void testDeleteBudget() throws Exception {
        MenigaBudget menigaBudget = prepareMenigaPlanningBudget();
        server.enqueue(new MockResponse().setResponseCode(204));

        Task<Void> task = menigaBudget.delete().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1");
        assertThat(request.getMethod()).isEqualTo("DELETE");
    }

    @Test
    public void testFetchSingleBudget() {
        server.enqueue(mockResponse("budget.json"));
        FetchBudgetFilter filter = new FetchBudgetFilter(1L);
        filter.setId(1L);
        filter.setCategoryIds(asList(1L, 2L));
        filter.setStartDate(DateTime.parse("2018-01-01"));
        filter.setEndDate(DateTime.parse("2018-01-01"));
        filter.setAllowOverlappingEntries(true);
        filter.setIncludeEntries(true);
        filter.setIncludeOptionalHistoricalData(true);

        Task<MenigaBudget> task = MenigaBudget.fetch(filter).getTask();

        assertThat(task).isSuccessful();
        assertThat(server.takeRequest().getPath()).isEqualTo("/v1/budgets/1?categoryIds=1,2&startDate=2018-01-01T00:00:00.000Z&endDate=2018-01-01T00:00:00.000Z&allowOverlappingEntries=true&includeEntries=true&includeOptionalHistoricalData=true");
        MenigaBudget budget = task.getResult();
        assertThat(budget).isNotNull();
    }

    @Test
    public void testUpdateSingleBudget() throws Exception {
        MenigaBudget budget = prepareMenigaPlanningBudget();
        server.enqueue(mockResponse("budget.json"));
        BudgetUpdate parameters = new BudgetUpdate();
        parameters.setName("New name");
        parameters.setDescription("New description");
        parameters.setAccountIds(asList(1L, 2L));

        Task<MenigaBudget> task = budget.update(parameters).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1");
        assertThat(request.getMethod()).isEqualTo("PUT");
        MenigaBudget updatedBudget = task.getResult();
        assertThat(updatedBudget).isNotNull();
    }

    @Test
    public void testResetBudget() throws Exception {
        MenigaBudget menigaBudget = prepareMenigaPlanningBudget();
        server.enqueue(new MockResponse().setResponseCode(204));

        Task<Void> task = menigaBudget.reset().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1/reset");
        assertThat(request.getMethod()).isEqualTo("POST");
    }

    @Test
    public void testFetchEntries() {
        server.enqueue(mockResponse("budgetentries.json"));

        Task<List<MenigaBudgetEntry>> task = MenigaBudgetEntry.fetch(1).getTask();

        assertThat(task).isSuccessful();
        List<MenigaBudgetEntry> entries = task.getResult();
        assertThat(server.takeRequest().getPath())
                 .isEqualTo("/v1/budgets/1/entries?id=1&startDate=2018-02-01T00:00:00.000Z&endDate=2018-02-28T00:00:00.000Z&allowOverlappingEntries=true&includeOptionalHistoricalData=false");
        assertThat(entries).isNotNull();
    }

    @Test
    public void testCreateBudgetEntry() {
        server.enqueue(mockResponse("budgetentries.json"));
        NewBudgetEntry parameters = new NewBudgetEntry();
        parameters.setCategoryIds(asList(1L, 2L));
        parameters.setStartDate(DateTime.parse("2018-01-01"));
        parameters.setEndDate(DateTime.parse("2018-01-02"));
        parameters.setTargetAmount(new MenigaDecimal(42));

        Task<List<MenigaBudgetEntry>> task = MenigaBudgetEntry.create(1, parameters).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1/entries");
        assertThat(request.getMethod()).isEqualTo("POST");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.entries",  hasSize(1))
                .assertThat("$.entries[0].targetAmount", equalTo(42.0))
                .assertThat("$.entries[0].startDate", equalTo("2018-01-01T00:00:00.000Z"))
                .assertThat("$.entries[0].endDate", equalTo("2018-01-02T00:00:00.000Z"))
                .assertThat("$.entries[0].categoryIds", equalTo(asList(1, 2)));
        assertThat(task.getResult()).isNotNull();
    }

    @Test
    public void testDeleteSingleBudgetEntry() throws Exception {
        MenigaBudgetEntry menigaBudgetEntry = prepareMenigaBudgetEntry();
        server.enqueue(new MockResponse().setBody("{}"));

        Task<Void> task = menigaBudgetEntry.delete().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1/entries/2");
        assertThat(request.getMethod()).isEqualTo("DELETE");
    }

    @Test
    public void testGetSingleBudgetEntry() {
        server.enqueue(mockResponse("budgetentry.json"));

        Task<MenigaBudgetEntry> task = MenigaBudgetEntry.fetch(1L, 2L).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1/entries/2");
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(task.getResult()).isNotNull();
    }

    @Test
    public void testUpdateSingleBudgetEntry() throws Exception {
        MenigaBudgetEntry menigaBudgetEntry = prepareMenigaBudgetEntry();
        BudgetEntryUpdate parameters = new BudgetEntryUpdate(DateTime.parse("2016-01-08"), asList(1L, 2L));
        parameters.setTargetAmount(new MenigaDecimal(42));
        parameters.setEndDate(DateTime.parse("2019-11-01"));
        server.enqueue(mockResponse("budgetentryupdate.json"));

        Task<MenigaBudgetEntry> task = menigaBudgetEntry.update(parameters).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/budgets/1/entries/2");
        assertThat(request.getMethod()).isEqualTo("PUT");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.targetAmount", equalTo(42.0))
                .assertThat("$.startDate", equalTo(DateTime.parse("2016-01-08").toString()))
                .assertThat("$.endDate", equalTo(DateTime.parse("2019-11-01").toString()))
                .assertThat("$.categoryIds", equalTo(asList(1, 2)));
        assertThat(task.getResult()).isNotNull();
    }

    private Task<MenigaBudget> createMenigaBudgetTask(BudgetType budgetType) throws InterruptedException {
        server.enqueue(mockResponse("budget.json"));
        Task<MenigaBudget> task = MenigaBudget.create(budgetType, "Test Budget", "Test Budget", singletonList(1L), BudgetPeriod.MONTH, 0).getTask();
        task.waitForCompletion();
        return task;
    }

    private MenigaBudget prepareMenigaPlanningBudget() throws InterruptedException {
        MenigaBudget menigaBudget = createMenigaBudgetTask(BudgetType.PLANNING).getResult();
        server.takeRequest();
        return menigaBudget;
    }

    private Task<List<MenigaBudgetEntry>> createMenigaBudgetEntryTask() throws InterruptedException {
        server.enqueue(mockResponse("budgetentries.json"));
        NewBudgetEntry parameters = new NewBudgetEntry();
        parameters.setTargetAmount(MenigaDecimal.ZERO);
        parameters.setStartDate(DateTime.parse("2016-01-08"));
        parameters.setEndDate(DateTime.parse("2019-11-01"));
        parameters.setCategoryIds(asList(1L, 2L));
        Task<List<MenigaBudgetEntry>> task = MenigaBudgetEntry.create(1L, parameters).getTask();
        task.waitForCompletion();
        server.takeRequest();
        return task;
    }

    private MenigaBudgetEntry prepareMenigaBudgetEntry() throws InterruptedException {
        List<MenigaBudgetEntry> menigaBudgetEntries = createMenigaBudgetEntryTask().getResult();
        return menigaBudgetEntries.get(1);
    }
}
