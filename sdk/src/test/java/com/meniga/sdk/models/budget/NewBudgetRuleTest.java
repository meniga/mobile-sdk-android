package com.meniga.sdk.models.budget;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.budget.enums.GenerationType;

import org.joda.time.DateTime;
import org.junit.Test;

import static java.util.Collections.singletonList;

public class NewBudgetRuleTest {

	@Test(expected = IllegalStateException.class)
	public void shouldFailWhenTargetAmountProvidedAndSameAsMonth() {
		builder().generation(TargetAmountGeneration.create(GenerationType.SAME_AS_MONTH, 3))
				.targetAmount(new MenigaDecimal(42))
				.build();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailWhenTargetAmountProvidedAndAverage() {
		builder().generation(TargetAmountGeneration.create(GenerationType.AVERAGE_MONTHS, 3))
				.targetAmount(new MenigaDecimal(42))
				.build();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailWithoutTargetAmountProvidedAndManual() {
		builder().generation(TargetAmountGeneration.create(GenerationType.MANUAL, 3))
				.build();
	}

	@Test
	public void shouldPassForSameAsMonth() {
		builder().generation(TargetAmountGeneration.create(GenerationType.SAME_AS_MONTH, 3))
				.build();
	}

	@Test
	public void shouldPassForAverage() {
		builder().generation(TargetAmountGeneration.create(GenerationType.AVERAGE_MONTHS, 3))
				.build();
	}

	@Test
	public void shouldPassForFixed() {
		builder().generation(TargetAmountGeneration.create(GenerationType.MANUAL, 3))
				.targetAmount(new MenigaDecimal(44))
				.build();
	}

	private NewBudgetRule.Builder builder() {
		return NewBudgetRule.builder()
				.startDate(DateTime.now())
				.categoryIds(singletonList(44L))
				.applying(Applying.always());
	}
}
