/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.transactions;

import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.providers.tasks.Task;

import org.assertj.core.api.Condition;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.models.transactions.MenigaTransactionPageAssertions.assertThat;
import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MenigaTransactionApiSpec {

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
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void shouldCreateTransaction() throws InterruptedException {
        server.enqueue(mockResponse("transaction.json"));

        Task<MenigaTransaction> task = MenigaTransaction.create(DateTime.parse("2018-03-08"), "Hagkaup", new MenigaDecimal(5000), 45).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/transactions");
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        JsonAssert.with(recordedRequest.getBody().readUtf8())
                .assertThat("$.date", equalTo("2018-03-08T00:00:00.000Z"))
                .assertThat("$.text", equalTo("Hagkaup"))
                .assertThat("$.amount", equalTo(5000.0))
                .assertThat("$.categoryId", equalTo(45))
                .assertThat("$.setAsRead", is(true));
        assertThat(task.getResult()).isNotNull();
    }

    @Test
    public void shouldFetchSingleTransaction() throws InterruptedException {
        server.enqueue(mockResponse("transaction.json"));

        Task<MenigaTransaction> task = MenigaTransaction.fetch(138327).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/transactions/138327?include=Account,Merchant");
        MenigaTransaction transaction = task.getResult();
        assertThat(transaction.getId()).isEqualTo(138327);
        assertThat(transaction.getAccount()).isNotNull();
        assertThat(transaction.getAccount().getId()).isEqualTo(transaction.getAccountId());
    }

    @Test
    public void shouldFetchMultipleTransactions() throws Exception {
        server.enqueue(mockResponse("transactionsPage.json"));
        TransactionsFilter filter = new TransactionsFilter.Builder()
                .page(20, 0)
                .build();

        Task<MenigaTransactionPage> task = MenigaTransaction.fetch(filter).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/transactions?include=Account,Merchant&take=20&skip=0");
        MenigaTransactionPage page = task.getResult();
        assertThat(page)
                .hasPageIndex(0)
                .hasAccountsIncluded()
                .hasMerchantsIncluded();
    }

    @Test
    public void shouldFetchMultipleTransactionsWithFiltersList() throws Exception {
        server.enqueue(mockResponse("transactionsPage.json"));
        server.enqueue(mockResponse("transactionsPage.json"));
        List<TransactionsFilter> filters = newArrayList(
                new TransactionsFilter.Builder()
                        .comment("comment")
                        .build(),
                new TransactionsFilter.Builder()
                        .page(20, 0)
                        .build());

        Task<MenigaTransactionPage> task = MenigaTransaction.fetch(filters).getTask();

        assertThat(task).isSuccessful();
        List<RecordedRequest> recordedRequests = newArrayList(server.takeRequest(), server.takeRequest());
        assertThat(recordedRequests).haveExactly(1, withPath("/v1/transactions?include=Account,Merchant&take=50&comment=comment&skip=0"));
        assertThat(recordedRequests).haveExactly(1, withPath("/v1/transactions?include=Account,Merchant&take=20&skip=0"));
        MenigaTransactionPage page = task.getResult();
        assertThat(page)
                .hasPageIndex(0)
                .hasAccountsIncluded()
                .hasMerchantsIncluded();
    }

    @Test
    public void shouldAddCommentToTransactions() throws InterruptedException {
        server.enqueue(mockResponse("addComments.json"));

        List<Long> transactionIds = newArrayList(1L, 2L);
        Task<List<MenigaComment>> task = MenigaTransaction.addComments(transactionIds, "newComment").getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/transactions/comments");
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        List<MenigaComment> comments = task.getResult();
        MenigaComment firstComment = comments.get(0);
        assertThat(firstComment.id).isEqualTo(20409);
        assertThat(firstComment.comment).isEqualTo("newComment");
        assertThat(firstComment.personId).isEqualTo(1180);
        MenigaComment secondComment = comments.get(1);
        assertThat(secondComment.id).isEqualTo(20410);
        assertThat(secondComment.comment).isEqualTo("newComment");
        assertThat(secondComment.personId).isEqualTo(1180);
    }

    private Condition<RecordedRequest> withPath(final String expectedPath) {
        return new Condition<RecordedRequest>(expectedPath) {
            @Override
            public boolean matches(RecordedRequest value) {
                return expectedPath.equals(value.getPath());
            }
        };
    }
}
