package com.meniga.sdk.models.challenges;

import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.challenges.enums.ChallengeInterval;
import com.meniga.sdk.models.challenges.enums.ChallengeType;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;
import com.meniga.sdk.providers.tasks.Task;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MenigaChallengesApiTest {

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
    public void shouldFetchChallenges() throws Exception {
        server.enqueue(mockResponse("challenges.json"));

        Task<List<MenigaChallenge>> task = MenigaChallenge.fetch().getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges?includeExpired=true&excludeSuggested=false&excludeAccepted=false&includeDisabled=true");
        List<MenigaChallenge> result = task.getResult();
        assertThat(result).hasSize(8);
    }

    @Test
    public void shouldCreateChallenge() throws InterruptedException {
        server.enqueue(mockResponse("challenges.json"));

        Task<MenigaChallenge> task = MenigaChallenge.create(
                "title",
                "description",
                DateTime.parse("2018-01-01"),
                DateTime.parse("2018-02-01"),
                Lists.newArrayList(1L, 2L),
                new MenigaDecimal(1),
                1L,
                CustomChallengeColor.YELLOW,
                ChallengeInterval.WEEKLY)
                .getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges");
        JsonAssert.with(recordedRequest.getBody().readUtf8())
                .assertThat("$.title", equalTo("title"))
                .assertThat("$.startDate", equalTo("2018-01-01T00:00:00.000Z"))
                .assertThat("$.endDate", equalTo("2018-02-01T00:00:00.000Z"))
                .assertThat("$.typeData.categoryIds", Matchers.contains(1, 2))
                .assertThat("$.typeData.targetAmount", equalTo(1.0))
                .assertThat("$.typeData.metaData", equalTo("{\"color\": \"warning\"}"))
                .assertThat("$.typeData.recurringInterval", equalTo("Weekly"));
    }

    @Test
    public void shouldDeleteChallenge() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(new MockResponse().setResponseCode(204));

        Task<Void> task = challenge.delete().getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("DELETE");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50");
        assertThat(task.isFaulted()).isFalse();
    }

    @Test
    public void shouldFetchSingleChallenge() throws InterruptedException {
        server.enqueue(mockResponse("challenge.json"));

        Task<MenigaChallenge> task = MenigaChallenge.fetch(UUID.fromString("e9b6101a-49eb-4d25-98e4-7b48dab90d50")).getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50");
        MenigaChallenge result = task.getResult();
        assertThat(result.title).isEqualTo("Eat like a Boss");
        assertThat(result.description).isEqualTo("You can't handle the truth");
        assertThat(result.type).isEqualTo(ChallengeType.SPENDING);
    }

    @Test
    public void shouldUpdateChallenge() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(mockResponse("challenge.json"));

        challenge.title = "New title";
        Task<Void> update = challenge.update().getTask();
        update.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("PUT");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50");
        JsonAssert.with(recordedRequest.getBody().readUtf8())
                .assertThat("$.title", equalTo("New title"))
                .assertThat("$.description", equalTo("You can't handle the truth"))
                .assertThat("$.startDate", equalTo("2018-03-01T00:00:00.000Z"))
                .assertThat("$.endDate", equalTo("2018-04-01T00:00:00.000Z"))
                .assertThat("$.iconUrl", equalTo("fuel.png"))
                .assertThat("$.typeData.targetAmount", equalTo(0.0))
                .assertThat("$.typeData.metaData", equalTo("{\"color\": \"warning\"}"));
    }

    @Test
    public void shouldAcceptChallenge() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(mockResponse("challenge.json"));

        Task<MenigaChallenge> task = challenge.accept(new MenigaDecimal(1)).getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50/accept");
        JsonAssert.with(recordedRequest.getBody().readUtf8())
                .assertThat("$.targetAmount", equalTo(1.0));
        assertThat(task.getResult().accepted).isTrue();
    }

    @Test
    public void shouldDisableChallenge() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(new MockResponse().setResponseCode(204));

        Task<Void> task = challenge.disable().getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50/disable");
        assertThat(task.isFaulted()).isFalse();
    }

    @Test
    public void shouldEnableChallenge() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(new MockResponse().setResponseCode(204));

        Task<Void> task = challenge.enable().getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50/enable");
        assertThat(task.isFaulted()).isFalse();
    }

    @Test
    public void shouldFetchChallengeHistory() throws InterruptedException {
        MenigaChallenge challenge = fetchChallenges().get(0);
        server.enqueue(mockResponse("challenges.json"));

        Task<List<MenigaChallenge>> task = challenge.history(1, 8).getTask();
        task.waitForCompletion();

        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/v1/challenges/e9b6101a-49eb-4d25-98e4-7b48dab90d50/history?skip=8&take=8");
        assertThat(task.getResult()).hasSize(8);
    }

    private List<MenigaChallenge> fetchChallenges() throws InterruptedException {
        server.enqueue(mockResponse("challenges.json"));
        Task<List<MenigaChallenge>> task = MenigaChallenge.fetch().getTask();
        task.waitForCompletion();
        server.takeRequest();
        return task.getResult();
    }
}
