package com.meniga.sdk.models.budget;

import com.meniga.sdk.webservices.budget.CreateBudgetRules;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static com.meniga.sdk.helpers.Objects.checkArgument;
import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class NewBudgetRules {

	private final long budgetId;
	private final List<NewBudgetRule> newBudgetRules;

	private NewBudgetRules(long budgetId, @Nonnull List<NewBudgetRule> newBudgetRules) {
		checkArgument(!newBudgetRules.isEmpty(), "Should contain at least one new rule");
		this.budgetId = budgetId;
		this.newBudgetRules = newBudgetRules;
	}

	public static Builder withBudgetId(long budgetId) {
		return new Builder(budgetId);
	}

	public long getBudgetId() {
		return budgetId;
	}

	public CreateBudgetRules toCreateBudgetRules() {
		List<CreateBudgetRules.CreateBudgetRuleData> createBudgetRuleDataList = new ArrayList<>();
		for (NewBudgetRule newBudgetRule : newBudgetRules) {
			createBudgetRuleDataList.add(newBudgetRule.toCreateBudgetRulesData());
		}
		return new CreateBudgetRules(createBudgetRuleDataList);
	}

	public static final class Builder {
		private long budgetId;
		private List<NewBudgetRule> newBudgetRules = new ArrayList<>();

		private Builder(long budgetId) {
			this.budgetId = budgetId;
		}

		public Builder addRule(@Nonnull NewBudgetRule newBudgetRule) {
			this.newBudgetRules.add(requireNonNull(newBudgetRule));
			return this;
		}

		public NewBudgetRules build() {
			return new NewBudgetRules(budgetId, newBudgetRules);
		}
	}
}
