package com.meniga.sdk.models.upcoming;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.models.budget.MenigaBudgetRule;
import com.meniga.sdk.models.budget.NewBudgetRules;
import com.meniga.sdk.models.upcoming.enums.ReconcileEntityType;
import com.meniga.sdk.providers.tasks.Task;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 10.9.2018.
 */
public class MenigaUpcomingIntegrationTest {

	@Rule
	public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

	@Before
	public void setUp() {
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
		MenigaSDK.init(settings);

		DateTimeUtils.setCurrentMillisFixed(DateTime.parse("2018-01-01").getMillis());
	}

	@Test
	public void shouldReconcileTransaction() {
		server.enqueue(mockResponse("postreconciletransaction.json"));
		Task<Void> task = MenigaUpcoming.reconcile(1, ReconcileEntityType.INVOICE, 2).getTask();

		assertThat(task).isSuccessful();
		RecordedRequest request = server.takeRequest();
		Assertions.assertThat(request.getPath()).isEqualTo("/v1/upcoming/1/reconcile/Invoice/2");
		Assertions.assertThat(request.getMethod()).isEqualTo("POST");
		Assertions.assertThat(request.getBody().readUtf8()).isEqualTo("");
	}
}
