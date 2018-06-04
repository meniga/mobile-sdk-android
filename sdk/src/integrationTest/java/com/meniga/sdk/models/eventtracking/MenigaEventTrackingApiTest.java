package com.meniga.sdk.models.eventtracking;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.providers.tasks.Task;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 4.6.2018.
 */
public class MenigaEventTrackingApiTest {

	@Rule
	public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

	@Before
	public void setUp() {
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
		MenigaSDK.init(settings);
	}

	@Test
	public void testTracking() {
		server.enqueue(mockResponse("eventtracking.json").setResponseCode(204));
		Task<Void> task = MenigaEventTracking.build("dialog", "seen", 25, "mobile_androud").track().getTask();

		assertThat(task).isSuccessful();
		RecordedRequest request = server.takeRequest();
		Assertions.assertThat(request.getPath()).isEqualTo("/v1/eventtracking");
		Assertions.assertThat(request.getMethod()).isEqualTo("POST");
	}
}
