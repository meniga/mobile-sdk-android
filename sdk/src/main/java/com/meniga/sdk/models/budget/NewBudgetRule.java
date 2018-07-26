package com.meniga.sdk.models.budget;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Preconditions;
import com.meniga.sdk.models.budget.enums.GenerationType;
import com.meniga.sdk.webservices.budget.CreateBudgetRules;

import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class NewBudgetRule {
	private final MenigaDecimal targetAmount;
	private final DateTime startDate;
	private final DateTime endDate;
	private final List<Long> categoryIds;
	private final TargetAmountGeneration generation;
	private final RecurringPattern recurringPattern;

	private NewBudgetRule(
			@Nullable MenigaDecimal targetAmount,
			@Nonnull DateTime startDate,
			@Nullable DateTime endDate,
			@Nonnull List<Long> categoryIds,
			@Nonnull TargetAmountGeneration generation,
			@Nullable RecurringPattern recurringPattern) {
		this.targetAmount = targetAmount;
		this.startDate = requireNonNull(toStartDate(startDate, recurringPattern));
		this.endDate = toEndDate(endDate, recurringPattern);
		this.categoryIds = requireNonNull(categoryIds);
		this.generation = requireNonNull(generation);
		this.recurringPattern = recurringPattern;
		checkState();
	}

	private DateTime toStartDate(DateTime startDate, RecurringPattern recurringPattern) {
		if (recurringPattern != null) {
			return recurringPattern.getStarting();
		} else {
			return startDate;
		}
	}

	private DateTime toEndDate(DateTime endDate, RecurringPattern recurringPattern) {
		if (recurringPattern != null) {
			return recurringPattern.getEndDate();
		} else {
			return endDate;
		}
	}

	private void checkState() {
		if (targetAmount == null && generation.getValue() == 0) {
			throw new IllegalStateException("When using Generation type 0, you must specify a target amount");
		} else if (targetAmount != null && !MenigaDecimal.ZERO.equals(targetAmount) && generation.getValue() != 0) {
			throw new IllegalStateException("A non-null or non-zero target amount is not allowed when the generation type is not zero (manual generation type).");
		}
	}

	CreateBudgetRules.CreateBudgetRuleData toCreateBudgetRulesData() {
		return new CreateBudgetRules.CreateBudgetRuleData(
				targetAmount,
				startDate,
				endDate,
				categoryIds,
				toGenerationType(generation),
				toRecurringPattern(recurringPattern),
				toRepeatUntil(recurringPattern)
		);
	}

	private int toGenerationType(TargetAmountGeneration generation) {
		return generation.getValue();
	}

	private CreateBudgetRules.RecurringPattern toRecurringPattern(@Nullable RecurringPattern recurringPattern) {
		return recurringPattern == null ? null : new CreateBudgetRules.RecurringPattern(recurringPattern.getMonths());
	}

	private DateTime toRepeatUntil(@Nullable RecurringPattern recurringPattern) {
		return recurringPattern == null ? null : recurringPattern.getUntil();
	}

	public static ManualBuilder manual() {
		return new ManualBuilder();
	}

	public static AutomaticBuilder sameAsMonthAgo(int months) {
		return new AutomaticBuilder(months, GenerationType.SAME_AS_MONTH);
	}

	public static AutomaticBuilder averageForLastMonths(int months) {
		return new AutomaticBuilder(months, GenerationType.AVERAGE_MONTHS);
	}

	public static ManualRecurringBuilder manualRecurring(@Nonnull RecurringPattern recurringPattern) {
		return new ManualRecurringBuilder(recurringPattern);
	}

	public static AutomaticRecurringBuilder sameAsMonthAgoRecurring(int monthAgo, @Nonnull RecurringPattern recurringPattern) {
		return new AutomaticRecurringBuilder(monthAgo, GenerationType.SAME_AS_MONTH, recurringPattern);
	}

	public static AutomaticRecurringBuilder averageForLastMonthsRecurring(int lastMonths, @Nonnull RecurringPattern recurringPattern) {
		return new AutomaticRecurringBuilder(lastMonths, GenerationType.AVERAGE_MONTHS, recurringPattern);
	}

	public static class ManualBuilder extends BaseManualBuilder<ManualBuilder> {

		public ManualBuilder startDate(@Nonnull DateTime startDate) {
			this.startDate = requireNonNull(startDate);
			return this;
		}

		public ManualBuilder endDate(@Nullable DateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		private ManualBuilder() {
			this.targetAmountGeneration = TargetAmountGeneration.manual();
		}
	}

	public static class AutomaticBuilder extends Builder<AutomaticBuilder> {

		public AutomaticBuilder startDate(@Nonnull DateTime startDate) {
			this.startDate = requireNonNull(startDate);
			return this;
		}

		public AutomaticBuilder endDate(@Nullable DateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		private AutomaticBuilder(int months, GenerationType generationType) {
			this.targetAmountGeneration = TargetAmountGeneration.create(generationType, months);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class ManualRecurringBuilder extends BaseManualBuilder<ManualRecurringBuilder> {
		private ManualRecurringBuilder(RecurringPattern recurringPattern) {
			this.targetAmountGeneration = TargetAmountGeneration.manual();
			this.recurringPattern = recurringPattern;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class AutomaticRecurringBuilder extends Builder<AutomaticRecurringBuilder> {
		private AutomaticRecurringBuilder(int months, GenerationType generationType, RecurringPattern recurringPattern) {
			this.targetAmountGeneration = TargetAmountGeneration.create(generationType, months);
			this.recurringPattern = recurringPattern;
		}
	}

	protected static abstract class BaseManualBuilder<TBuilder extends BaseManualBuilder<TBuilder>> extends Builder<TBuilder> {

		@SuppressWarnings("unchecked")
		public TBuilder targetAmount(@Nonnull MenigaDecimal targetAmount) {
			this.targetAmount = requireNonNull(targetAmount);
			return (TBuilder) this;
		}
	}

	protected static abstract class Builder<TBuilder extends Builder<TBuilder>> {
		MenigaDecimal targetAmount;
		DateTime startDate;
		DateTime endDate;
		private List<Long> categoryIds;
		TargetAmountGeneration targetAmountGeneration;
		RecurringPattern recurringPattern;

		private Builder() {
		}

		@SuppressWarnings("unchecked")
		public TBuilder categoryIds(@Nonnull List<Long> categoryIds) {
			this.categoryIds = requireNonNull(categoryIds);
			Preconditions.checkState(categoryIds.size() > 0, "At least one category id is expected");
			return (TBuilder) this;
		}

		public NewBudgetRule build() {
			return new NewBudgetRule(targetAmount, startDate, endDate, categoryIds, targetAmountGeneration, recurringPattern);
		}
	}
}
