package com.meniga.sdk.models.budget;

import com.meniga.sdk.webservices.budget.GetBudgetRules;

import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class FetchBudgetRulesFilter {
	private final Long budgetId;
	private final List<Long> categoryIds;
	private final DateTime startDate;
	private final DateTime endDate;
	private final boolean allowOverlappingRules;

	public Long getBudgetId() {
		return budgetId;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public boolean isAllowOverlappingRules() {
		return allowOverlappingRules;
	}

	private FetchBudgetRulesFilter(@Nonnull Long budgetId,
								   @Nullable List<Long> categoryIds,
								   @Nullable DateTime startDate,
								   @Nullable DateTime endDate,
								   boolean allowOverlappingRules) {
		this.budgetId = requireNonNull(budgetId);
		this.categoryIds = categoryIds;
		this.startDate = startDate;
		this.endDate = endDate;
		this.allowOverlappingRules = allowOverlappingRules;
	}

	public static Builder builder() {
		return new Builder();
	}

	@Nonnull
	public GetBudgetRules toGetBudgetRules() {
		return new GetBudgetRules(budgetId, categoryIds, startDate, endDate, allowOverlappingRules);
	}

	public static final class Builder {
		private Long budgetId;
		private List<Long> categoryIds;
		private DateTime startDate;
		private DateTime endDate;
		private boolean allowOverlappingRules;

		private Builder() {
		}

		public Builder budgetId(long budgetId) {
			this.budgetId = budgetId;
			return this;
		}

		public Builder categoryIds(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
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

		public Builder allowOverlappingRules(boolean allowOverlappingRules) {
			this.allowOverlappingRules = allowOverlappingRules;
			return this;
		}

		public FetchBudgetRulesFilter build() {
			return new FetchBudgetRulesFilter(budgetId, categoryIds, startDate, endDate, allowOverlappingRules);
		}
	}
}
