/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.user;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.providers.tasks.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MenigaUserTest {

    @Rule
    public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

    @Before
    public void setUp() {
        MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
        MenigaSDK.init(settings);
    }

    @Test
    public void shouldRegisterInStandardMode() {
        String email = "bjork@mail.is";
        String password = "42";
        String culture = "en-GB";
        server.enqueue(mockResponse("postregister.json"));

        Task<MenigaUser> task = MenigaUser.create(email, password, culture).getTask();

        RecordedRequest request = server.takeRequest();
        assertThat(task).isSuccessful();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v1/me/register");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.email", equalTo(email))
                .assertThat("$.password", equalTo(password))
                .assertThat("$.culture", equalTo(culture));
    }

    @Test
    public void shouldBeginRegistrationInOutOfBandMode() {
        String email = "bjork@mail.is";
        server.enqueue(new MockResponse().setBody("{}"));

        Task<Void> task = MenigaUser.beginRegistration(email).getTask();

        assertThat(task).isSuccessful();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v1/me/beginregistration");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.email", equalTo(email));
    }

    @Test
    public void shouldRegisterInOutOfBandMode() {
        Registration registration = Registration.builder()
                .email("bjork@mail.is")
                .password("42")
                .signupToken("0e9bec4c-891e-4646-b377-d38006a8a694")
                .culture("en-GB")
                .build();
        server.enqueue(mockResponse("postregister.json"));

        Task<MenigaUser> task = MenigaUser.register(registration).getTask();

        RecordedRequest request = server.takeRequest();
        assertThat(task).isSuccessful();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v1/me/register");
        JsonAssert.with(request.getBody().readUtf8())
                .assertThat("$.email", equalTo("bjork@mail.is"))
                .assertThat("$.password", equalTo("42"))
                .assertThat("$.culture", equalTo("en-GB"))
                .assertThat("$.signupToken", equalTo("0e9bec4c-891e-4646-b377-d38006a8a694"));
    }
}
