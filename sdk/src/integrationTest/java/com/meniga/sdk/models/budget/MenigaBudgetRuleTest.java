package com.meniga.sdk.models.budget;

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer;
import com.jayway.jsonassert.JsonAssert;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.SwaggerJsonExtensions;
import com.meniga.sdk.providers.tasks.Task;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.meniga.sdk.models.budget.RecurringPattern.everyMonths;
import static com.meniga.sdk.providers.tasks.TaskAssertions.assertThat;
import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class MenigaBudgetRuleTest {

	@Rule
	public ValidatingMockWebServer server = SwaggerJsonExtensions.createValidatingMockWebServer();

	@Before
	public void setUp() {
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(server.baseUrl()).build();
		MenigaSDK.init(settings);

		DateTimeUtils.setCurrentMillisFixed(DateTime.parse("2018-01-01").getMillis());
	}

	@Test
	public void shouldGetRules() {
		Task<List<MenigaBudgetRule>> task = fetchBudgetRules();

		assertThat(task).isSuccessful();
		RecordedRequest request = server.takeRequest();
		assertThat(request.getPath()).isEqualTo("/v1/budgets/97/rules?categoryIds=72&startDate=2018-01-01T00:00:00.000Z&endDate=2018-02-01T00:00:00.000Z&allowOverlappingRules=true");
		assertThat(request.getMethod()).isEqualTo("GET");
		List<MenigaBudgetRule> rules = task.getResult();
		assertThat(rules).hasSize(3);
		MenigaBudgetRule rule = rules.get(2);
		assertThat(rule.getId()).isEqualTo(13102);
		assertThat(rule.getTargetAmount()).isEqualTo(MenigaDecimal.ZERO);
		assertThat(rule.getStartDate()).isEqualTo(new DateTime("2018-09-01"));
		assertThat(rule.getEndDate()).isEqualTo(new DateTime("2018-09-30"));
		assertThat(rule.getUpdatedAt()).isEqualTo(new DateTime("2018-07-16T11:36:16"));
		assertThat(rule.getBudgetId()).isEqualTo(97);
		assertThat(rule.getTargetAmountGeneration()).isEqualTo(TargetAmountGeneration.create(3));
		assertThat(rule.getCategoryIds()).isEqualTo(singletonList(72L));
	}

	@Test
	public void shouldAddRule() {
		server.enqueue(mockResponse("postbudgetrule.json"));
		NewBudgetRule budgetRule = NewBudgetRule.manualRecurring(
				everyMonths(2)
						.starting(new DateTime("2018-01-01"))
						.until(new DateTime("2019-01-01")))
				.budgetId(97)
				.targetAmount(new MenigaDecimal(42))
				.categoryIds(singletonList(72L))
				.build();

		Task<List<MenigaBudgetRule>> task = MenigaBudgetRule.create(budgetRule).getTask();

		assertThat(task).isSuccessful();
		RecordedRequest request = server.takeRequest();
		assertThat(request.getPath()).isEqualTo("/v1/budgets/97/rules");
		assertThat(request.getMethod()).isEqualTo("POST");
		JsonAssert.with(request.getBody().readUtf8())
				.assertThat("$.rules", hasSize(1))
				.assertThat("$.rules[0].targetAmount", equalTo(42.0))
				.assertThat("$.rules[0].startDate", equalTo("2018-01-01T00:00:00.000Z"))
				.assertThat("$.rules[0].endDate", equalTo("2018-01-31T23:59:59.999Z"))
				.assertThat("$.rules[0].categoryIds", equalTo(singletonList(72)))
				.assertThat("$.rules[0].generationType", equalTo(0))
				.assertThat("$.rules[0].recurringPattern.monthInterval", equalTo(2))
				.assertThat("$.rules[0].repeatUntil", equalTo("2019-01-01T00:00:00.000Z"));
	}

	@Test
	public void shouldDeleteRule() throws InterruptedException {
		Task<List<MenigaBudgetRule>> fetchTask = fetchBudgetRules();
		fetchTask.waitForCompletion();
		MenigaBudgetRule budgetRule = fetchTask.getResult().get(0);
		server.takeRequest();
		server.enqueue(new MockResponse().setResponseCode(204));

		Task<Void> task = budgetRule.delete().getTask();

		assertThat(task).isSuccessful();
		RecordedRequest request = server.takeRequest();
		assertThat(request.getPath()).isEqualTo("/v1/budgets/97/rules/13103");
		assertThat(request.getMethod()).isEqualTo("DELETE");
	}

	private Task<List<MenigaBudgetRule>> fetchBudgetRules() {
		server.enqueue(mockResponse("getbudgetrules.json"));
		FetchBudgetRulesFilter filter = FetchBudgetRulesFilter.builder()
				.budgetId(97)
				.categoryIds(singletonList(72L))
				.startDate(new DateTime("2018-01-01"))
				.endDate(new DateTime("2018-02-01"))
				.allowOverlappingRules(true)
				.build();

		return MenigaBudgetRule.fetch(filter).getTask();
	}
}
