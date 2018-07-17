package com.meniga.sdk.models.budget;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.webservices.budget.CreateBudgetRules;

import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.meniga.sdk.helpers.Objects.requireNonNull;
import static java.util.Collections.singletonList;

public class NewBudgetRule {
	private final long budgetId;
	private final MenigaDecimal targetAmount;
	private final DateTime startDate;
	private final DateTime endDate;
	private final List<Long> categoryIds;
	private final TargetAmountGeneration generation;
	private final Applying applying;

	private NewBudgetRule(
			long budgetId,
			@Nullable MenigaDecimal targetAmount,
			@Nonnull DateTime startDate,
			@Nullable DateTime endDate,
			@Nonnull List<Long> categoryIds,
			@Nonnull TargetAmountGeneration generation,
			@Nonnull Applying applying) {
		this.budgetId = budgetId;
		this.targetAmount = targetAmount;
		this.startDate = requireNonNull(startDate);
		this.endDate = endDate;
		this.categoryIds = requireNonNull(categoryIds);
		this.generation = requireNonNull(generation);
		this.applying = requireNonNull(applying);
		checkState();
	}

	private void checkState() {
		if (targetAmount == null && generation.getValue() == 0) {
			throw new IllegalStateException("When using Generation type 0, you must specify a target amount");
		} else if (targetAmount != null && !MenigaDecimal.ZERO.equals(targetAmount) && generation.getValue() != 0) {
			throw new IllegalStateException("A non-null or non-zero target amount is not allowed when the generation type is not zero (manual generation type).");
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public CreateBudgetRules toCreateBudgetRules() {
		CreateBudgetRules.CreateBudgetRuleData data = new CreateBudgetRules.CreateBudgetRuleData(
				targetAmount,
				startDate,
				endDate,
				categoryIds,
				toGenerationType(generation),
				toRecurringPattern(applying),
				toRepeatUntil(applying)
		);
		return new CreateBudgetRules(singletonList(data));
	}

	private int toGenerationType(TargetAmountGeneration generation) {
		return generation.getValue();
	}

	private CreateBudgetRules.RecurringPattern toRecurringPattern(Applying applying) {
		return new CreateBudgetRules.RecurringPattern(applying.getEvery());
	}

	private DateTime toRepeatUntil(Applying applying) {
		return applying.getUntil();
	}

	public long getBudgetId() {
		return budgetId;
	}

	public static final class Builder {
		private long budgetId;
		private MenigaDecimal targetAmount;
		private DateTime startDate;
		private DateTime endDate;
		private List<Long> categoryIds;
		private TargetAmountGeneration generation;
		private Applying applying;

		private Builder() {
		}

		public Builder budgetId(long budgetId) {
			this.budgetId = budgetId;
			return this;
		}

		public Builder targetAmount(MenigaDecimal targetAmount) {
			this.targetAmount = targetAmount;
			return this;
		}

		public Builder startDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}

		public Builder endDate(DateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		public Builder categoryIds(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
			return this;
		}

		public Builder generation(TargetAmountGeneration generation) {
			this.generation = generation;
			return this;
		}

		public Builder applying(Applying applying) {
			this.applying = applying;
			return this;
		}

		public NewBudgetRule build() {
			return new NewBudgetRule(budgetId, targetAmount, startDate, endDate, categoryIds, generation, applying);
		}
	}
}

/*
{
  "rules": [
    {
      "targetAmount": 0,
      "startDate": "2018-07-01",
      "endDate": "2018-07-31",
      "categoryIds": [
        72
      ],
      "generationType": 3,
      "recurringPattern": {
        "monthInterval": 2
      },
      "repeatUntil": "2018-10-01"
    }
  ]
}
 */
