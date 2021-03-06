/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;
import com.meniga.sdk.models.accounts.enums.AccountCategory;
import com.meniga.sdk.providers.tasks.Task;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MenigaAccountApiTest {

    @Rule
    public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

    @Before
    public void setUp() {
        MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
        MenigaSDK.init(settings);
    }

    @Test
    public void shouldFetchAccount() {
        server.enqueue(mockResponse("account.json"));

        Task<MenigaAccount> task = MenigaAccount.fetch(1).getTask();

        assertThat(task).isSuccessful();
        MenigaAccount account = task.getResult();
        assertThat(account).isNotNull();
    }

    @Test
    public void shouldFetchAccounts() {
        server.enqueue(mockResponse("accounts.json"));

        Task<List<MenigaAccount>> task = MenigaAccount.fetch().getTask();

        assertThat(task).isSuccessful();
        List<MenigaAccount> accounts = task.getResult();
        assertThat(accounts).isNotNull();

    }

    @Test
    public void shouldUpdateAccount() {
        server.enqueue(mockResponse("account.json"));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        account.setHidden(true);
        account.setName("New name");
        Task<Void> task = account.update().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/accounts/0");
        assertThat(recordedRequest.getMethod()).isEqualTo("PUT");
        JsonAssert.with(recordedRequest.getBody().readUtf8())
                .assertThat("$.name", equalTo("New name"))
                .assertThat("$.isHidden", equalTo(true));
    }

    @Test
    public void shouldDeleteAccount() {
        server.enqueue(new MockResponse().setResponseCode(204));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        Task<Void> task = account.delete().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/accounts/0");
        assertThat(recordedRequest.getMethod()).isEqualTo("DELETE");
    }

    @Test
    public void shouldFetchAccountTypes() {
        server.enqueue(mockResponse("accounttypes.json"));

        Task<List<MenigaAccountType>> task = MenigaAccount.fetchAccountTypes().getTask();

        assertThat(task).isSuccessful();
        List<MenigaAccountType> accountTypes = task.getResult();
        assertThat(accountTypes).isNotEmpty();
        MenigaAccountType type = accountTypes.get(0);
        assertThat(type.getId()).isEqualTo(1);
        assertThat(type.getName()).isEqualTo("Wallet");
        assertThat(type.getDescription()).isEqualTo("Wallet");
        assertThat(type.getAccountCategory()).isEqualTo(AccountCategory.WALLET);
        assertThat(type.getOrganizationId()).isEqualTo(1);
        assertThat(type.getRealmId()).isEqualTo(0);
        assertThat(type.getAccountCategoryDetails()).isNull();
        assertThat(type.isCashbackEnabled()).isEqualTo(false);
    }

    @Test
    public void shouldFetchAccountCategories() {
        server.enqueue(mockResponse("accountcategories.json"));

        Task<List<AccountCategory>> task = MenigaAccount.fetchCategories().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/accountcategories");
        List<AccountCategory> categories = task.getResult();
        assertThat(categories).isNotEmpty();
    }

    @Test
    public void shouldFetchAuthorizationTypes() {
        server.enqueue(mockResponse("accountauthorizationtypes.json"));

        Task<List<MenigaAuthorizationType>> task = MenigaAccount.fetchAuthorizationTypes().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/authorizationtypes");
        List<MenigaAuthorizationType> authorizationTypes = task.getResult();
        assertThat(authorizationTypes)
                .hasSize(4);
    }

    @Test
    public void shouldFetchMetadata() {
        MenigaAccount account = MenigaAccountFactory.createAccount();
        server.enqueue(mockResponse("accountmetadata.json"));

        Task<List<KeyVal<String, String>>> task = account.fetchMetadata().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/0/metadata");
        assertThat(request.getMethod()).isEqualTo("GET");
        List<KeyVal<String, String>> result = task.getResult();
        assertThat(result.get(0).getKey()).isEqualTo("Card holder");
        assertThat(result.get(0).getValue()).isEqualTo("John Bob");
        assertThat(result.get(1).getKey()).isEqualTo("Card number");
        assertThat(result.get(1).getValue()).isEqualTo("1234 XXXX XXXX 5432");
        assertThat(result.get(2).getKey()).isEqualTo("Valid to");
        assertThat(result.get(2).getValue()).isEqualTo("12/2026");
    }

    @Test
    public void shouldUpdateMetadata() {
        server.enqueue(mockResponse("accountmetadataupdate.json"));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        Task<KeyVal<String, String>> task = account.updateMetadata(KeyVal.of("Valid to", "01/2100")).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/0/metadata");
        assertThat(request.getMethod()).isEqualTo("PUT");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.name", equalTo("Valid to"))
                .assertThat("$.value", equalTo("01/2100"));
    }

    @Test
    public void shouldFetchMetadataByName() {
        server.enqueue(mockResponse("accountmetadatasingle.json"));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        Task<KeyVal<String, String>> task = account.fetchMetadataKeyVal("Valid to").getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/0/metadata/Valid%20to");
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void shouldFetchBalanceHistory() {
        server.enqueue(mockResponse("accountbalancehistory.json"));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        Task<List<MenigaAccountBalanceHistory>> task = account.fetchBalanceHistory(DateTime.parse("2018-01-01"), DateTime.parse("2018-02-01"), AccountBalanceHistorySort.DATE_ASCENDING).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/0/history?dateTo=2018-02-01T00:00:00.000Z&sort=BalanceDate&dateFrom=2018-01-01T00:00:00.000Z");
        assertThat(request.getMethod()).isEqualTo("GET");
        List<MenigaAccountBalanceHistory> result = task.getResult();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(4114);
        assertThat(result.get(0).getAccountId()).isEqualTo(1795);
        assertThat(result.get(0).getBalance()).isEqualTo(MenigaDecimal.ZERO);
        assertThat(result.get(0).getBalanceInUserCurrency()).isEqualTo(MenigaDecimal.ZERO);
        assertThat(result.get(0).getBalanceDate()).isEqualTo(DateTime.parse("2018-02-01T13:55:14"));
        assertThat(result.get(0).isDefault()).isEqualTo(false);
    }

    @Test
    public void shouldRefresh() {
        server.enqueue(mockResponse("account.json"));
        MenigaAccount account = MenigaAccountFactory.createAccount();

        Task<MenigaAccount> task = account.refresh().getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v1/accounts/0");
        assertThat(request.getMethod()).isEqualTo("GET");
        MenigaAccount result = task.getResult();
        assertThat(account).isNotEqualTo(result);
    }
}
