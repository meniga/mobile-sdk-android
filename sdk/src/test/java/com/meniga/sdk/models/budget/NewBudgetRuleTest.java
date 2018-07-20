package com.meniga.sdk.models.budget;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;
import org.junit.Test;

import static com.meniga.sdk.models.budget.RecurringPattern.everyMonths;
import static java.util.Collections.singletonList;

public class NewBudgetRuleTest {

	@Test
	public void shouldCreateManualNewBudgetRule() {
		NewBudgetRule.manual()
				.targetAmount(new MenigaDecimal(44))
				.startDate(new DateTime("2018-01-01"))
				.endDate(new DateTime("2018-01-01"))
				.categoryIds(singletonList(44L))
				.build();
	}

	@Test
	public void shouldCreateSameAsMonthNewBudgetRule() {
		NewBudgetRule.sameAsMonthAgo(5)
				.startDate(new DateTime("2018-01-01"))
				.endDate(new DateTime("2019-01-01"))
				.categoryIds(singletonList(44L))
				.build();
	}

	@Test
	public void shouldCreateAverageNewBudgetRule() {
		NewBudgetRule.averageForLastMonths(3)
				.startDate(new DateTime("2018-01-01"))
				.endDate(new DateTime("2019-01-01"))
				.categoryIds(singletonList(44L))
				.build();
	}

	@Test
	public void shouldCreateRecurringManualNewBudgetRule() {
		NewBudgetRule.manualRecurring(
				everyMonths(3)
						.starting(new DateTime("2018-01-01"))
						.until(new DateTime("2019-01-01")))
				.targetAmount(new MenigaDecimal(44))
				.categoryIds(singletonList(44L))
				.build();
	}

	@Test
	public void shouldCreateRecurringSameAsMonthNewBudgetRule() {
		NewBudgetRule.sameAsMonthAgoRecurring(3,
				everyMonths(6)
						.starting(new DateTime("2018-01-01"))
						.until(new DateTime("2019-01-01")))
				.categoryIds(singletonList(44L))
				.build();
	}

	@Test
	public void shouldCreateRecurringAverageNewBudgetRule() {
		NewBudgetRule.averageForLastMonthsRecurring(3,
				everyMonths(6)
						.starting(new DateTime("2018-01-01"))
						.until(new DateTime("2019-01-01")))
				.categoryIds(singletonList(44L))
				.build();
	}
}
