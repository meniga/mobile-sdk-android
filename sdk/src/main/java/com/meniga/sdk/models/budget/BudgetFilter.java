package com.meniga.sdk.models.budget;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.budget.enums.BudgetType;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BudgetFilter {
	/**
	 * Ids of budget buckets.
	 */
	private List<Long> ids;
	/**
	 * Account Ids linked to budget buckets.
	 */
	private List<Long> accountIds;
	/**
	 * Category Ids linked to budget buckets.
	 */
	private List<Long> categoryIds;
	/**
	 * Category type on budget buckets.
	 */
	private BudgetType type;
	/**
	 * String contained in names on budget buckets.
	 */
	private String name;
	/**
	 * Lower limit on target amounts on budget buckets.
	 */
	private MenigaDecimal targetAmountFrom;
	/**
	 * Upper limit on target amounts on budget buckets.
	 */
	private MenigaDecimal targetAmountTo;
	/**
	 * Lower limit on spent amounts on budget buckets.
	 */
	private MenigaDecimal spentAmountFrom;
	/**
	 * Lower limit on spent amounts on budget buckets.
	 */
	private MenigaDecimal spentAmountTo;
	/**
	 * Lower date limit for periods on budget buckets.
	 */
	private DateTime startDate;
	/**
	 * Upper date limit for periods on budget buckets.
	 */
	private DateTime endDate;
	/**
	 * String contained in descriptions on budget buckets
	 */
	private String description;
	/**
	 * Flag controlling how account ids are handled in searches. When true all account ids
	 * must be linked to the budget buckets returned.
	 */
	private Boolean matchAllAccounts;
	/**
	 * Flag controlling how category ids are handled in searches for budget buckets. When
	 * true all category ids must be linked to the budget buckets returned.
	 */
	private Boolean matchAllCategories;
	/**
	 * Flag controlling how dates are handled in searches for budget buckets. When true all budget buckets
	 * with period that intersects the filter dates are returned. Otherwise only budget buckets with period
	 * included in the filter period are returned.
	 */
	private Boolean allowOverlappingDates;
	/**
	 * Id of the budget's parent
	 */
	private Long parentId;
	/**
	 * The property “autoFill” determines if auto generated items are returned for periods where we don’t
	 * have manually created entries. Only Budgets of type=”Planning” are returned from this endpoint.
	 */
	private Boolean autoFill;

	/**
	 * Include the budget entries of the budget
	 */
	private Boolean includeEntries;

	private BudgetFilter() {
	}

	public Map<String, String> asMap() {
		String prefix = "";
		Map<String, String> map = new HashMap<>();

		StringBuilder idsBld = new StringBuilder();
		if (ids != null) {
			for (Long id : ids) {
				if (idsBld.length() > 0) {
					idsBld.append(",");
				}
				idsBld.append(id);
			}
		}
		map.put(prefix + "ids", idsBld.toString());

		StringBuilder accountIdsBld = new StringBuilder();
		if (accountIds != null) {
			for (Long id : accountIds) {
				if (accountIdsBld.length() > 0) {
					accountIdsBld.append(",");
				}
				accountIdsBld.append(id);
			}
		}
		map.put(prefix + "accountIds", idsBld.toString());

		StringBuilder catIdsBld = new StringBuilder();
		if (categoryIds != null) {
			for (Long id : categoryIds) {
				if (catIdsBld.length() > 0) {
					catIdsBld.append(",");
				}
				catIdsBld.append(id);
			}
		}
		map.put(prefix + "categoryIds", idsBld.toString());

		if (type != null) {
			map.put(prefix + "type", type.toString());
		}

		if (name != null) {
			map.put(prefix + "name", name);
		}

		if (targetAmountFrom != null) {
			map.put(prefix + "targetAmountFrom", Double.toString(targetAmountFrom.doubleValue()));
		}

		if (targetAmountTo != null) {
			map.put(prefix + "targetAmountTo", Double.toString(targetAmountTo.doubleValue()));
		}

		if (spentAmountFrom != null) {
			map.put(prefix + "spentAmountFrom", Double.toString(spentAmountFrom.doubleValue()));
		}

		if (spentAmountTo != null) {
			map.put(prefix + "spentAmountTo", Double.toString(spentAmountTo.doubleValue()));
		}

		if (startDate != null) {
			map.put(prefix + "startDate", startDate.toString());
		}

		if (endDate != null) {
			map.put(prefix + "endDate", endDate.toString());
		}

		if (description != null) {
			map.put(prefix + "description", description);
		}

		if (matchAllAccounts != null) {
			map.put(prefix + "matchAllAccounts", Boolean.toString(matchAllAccounts));
		}

		if (matchAllCategories != null) {
			map.put(prefix + "matchAllCategories", Boolean.toString(matchAllCategories));
		}

		if (allowOverlappingDates != null) {
			map.put(prefix + "allowOverlappingDates", Boolean.toString(allowOverlappingDates));
		}

		if (parentId != null) {
			map.put(prefix + "parentId", Long.toString(parentId));
		}

		if (autoFill != null) {
			map.put(prefix + "autoFill", Boolean.toString(autoFill));
		}

		if (includeEntries != null) {
			map.put(prefix + "includeEntries", Boolean.toString(includeEntries));
		}

		return map;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BudgetFilter that = (BudgetFilter) o;

		if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;
		if (accountIds != null ? !accountIds.equals(that.accountIds) : that.accountIds != null)
			return false;
		if (categoryIds != null ? !categoryIds.equals(that.categoryIds) : that.categoryIds != null)
			return false;
		if (type != that.type) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (targetAmountFrom != null ? !targetAmountFrom.equals(that.targetAmountFrom) : that.targetAmountFrom != null)
			return false;
		if (targetAmountTo != null ? !targetAmountTo.equals(that.targetAmountTo) : that.targetAmountTo != null)
			return false;
		if (spentAmountFrom != null ? !spentAmountFrom.equals(that.spentAmountFrom) : that.spentAmountFrom != null)
			return false;
		if (spentAmountTo != null ? !spentAmountTo.equals(that.spentAmountTo) : that.spentAmountTo != null)
			return false;
		if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
			return false;
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (matchAllAccounts != null ? !matchAllAccounts.equals(that.matchAllAccounts) : that.matchAllAccounts != null)
			return false;
		if (matchAllCategories != null ? !matchAllCategories.equals(that.matchAllCategories) : that.matchAllCategories != null)
			return false;
		if (allowOverlappingDates != null ? !allowOverlappingDates.equals(that.allowOverlappingDates) : that.allowOverlappingDates != null)
			return false;
		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null)
			return false;
		if (autoFill != null ? !autoFill.equals(that.autoFill) : that.autoFill != null)
			return false;
		return includeEntries != null ? includeEntries.equals(that.includeEntries) : that.includeEntries == null;

	}

	@Override
	public int hashCode() {
		int result = ids != null ? ids.hashCode() : 0;
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (targetAmountFrom != null ? targetAmountFrom.hashCode() : 0);
		result = 31 * result + (targetAmountTo != null ? targetAmountTo.hashCode() : 0);
		result = 31 * result + (spentAmountFrom != null ? spentAmountFrom.hashCode() : 0);
		result = 31 * result + (spentAmountTo != null ? spentAmountTo.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (matchAllAccounts != null ? matchAllAccounts.hashCode() : 0);
		result = 31 * result + (matchAllCategories != null ? matchAllCategories.hashCode() : 0);
		result = 31 * result + (allowOverlappingDates != null ? allowOverlappingDates.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + (autoFill != null ? autoFill.hashCode() : 0);
		result = 31 * result + (includeEntries != null ? includeEntries.hashCode() : 0);
		return result;
	}

	public static class Builder {
		private List<Long> ids;
		private List<Long> accountIds;
		private List<Long> categoryIds;
		private BudgetType type;
		private String name;
		private MenigaDecimal targetAmountFrom;
		private MenigaDecimal targetAmountTo;
		private MenigaDecimal spentAmountFrom;
		private MenigaDecimal spentAmountTo;
		private DateTime startDate;
		private DateTime endDate;
		private String description;
		private Boolean matchAllAccounts;
		private Boolean matchAllCategories;
		private Boolean allowOverlappingDates;
		private Long parentId;
		private Boolean autoFill;
		private Boolean includeEntries;

		/**
		 * {@link BudgetFilter#autoFill}
		 */
		public void setAutoFill(Boolean autoFill) {
			this.autoFill = autoFill;
		}

		/**
		 * {@link BudgetFilter#includeEntries}
		 */
		public void setIncludeEntries(Boolean includeEntries) {
			this.includeEntries = includeEntries;
		}

		/**
		 * {@link BudgetFilter#ids}
		 */
		public Builder setIds(List<Long> ids) {
			this.ids = ids;
			return this;
		}

		/**
		 * {@link BudgetFilter#accountIds}
		 */
		public Builder setAccountIds(List<Long> accountIds) {
			this.accountIds = accountIds;
			return this;
		}

		/**
		 * {@link BudgetFilter#categoryIds}
		 */
		public Builder setCategoryIds(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
			return this;
		}

		/**
		 * {@link BudgetFilter#type}
		 */
		public Builder setType(BudgetType type) {
			this.type = type;
			return this;
		}

		/**
		 * {@link BudgetFilter#name}
		 */
		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * {@link BudgetFilter#targetAmountFrom}
		 */
		public Builder setTargetAmountFrom(MenigaDecimal targetAmountFrom) {
			this.targetAmountFrom = targetAmountFrom;
			return this;
		}

		/**
		 * {@link BudgetFilter#targetAmountTo}
		 */
		public Builder setTargetAmountTo(MenigaDecimal targetAmountTo) {
			this.targetAmountTo = targetAmountTo;
			return this;
		}

		/**
		 * {@link BudgetFilter#spentAmountFrom}
		 */
		public Builder setSpentAmountFrom(MenigaDecimal spentAmountFrom) {
			this.spentAmountFrom = spentAmountFrom;
			return this;
		}

		/**
		 * {@link BudgetFilter#spentAmountTo}
		 */
		public Builder setSpentAmountTo(MenigaDecimal spentAmountTo) {
			this.spentAmountTo = spentAmountTo;
			return this;
		}


		/**
		 * {@link BudgetFilter#startDate}
		 */
		public Builder setStartDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}

		/**
		 * {@link BudgetFilter#endDate}
		 */
		public Builder setEndDate(DateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		/**
		 * {@link BudgetFilter#description}
		 */
		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		/**
		 * {@link BudgetFilter#matchAllAccounts}
		 */
		public Builder setMatchAllAccounts(Boolean matchAllAccounts) {
			this.matchAllAccounts = matchAllAccounts;
			return this;
		}

		/**
		 * {@link BudgetFilter#matchAllCategories}
		 */
		public Builder setMatchAllCategories(Boolean matchAllCategories) {
			this.matchAllCategories = matchAllCategories;
			return this;
		}

		/**
		 * {@link BudgetFilter#allowOverlappingDates}
		 */
		public Builder setAllowOverlappingDates(Boolean allowOverlappingDates) {
			this.allowOverlappingDates = allowOverlappingDates;
			return this;
		}

		/**
		 * {@link BudgetFilter#parentId}
		 */
		public Builder setParentId(Long parentId) {
			this.parentId = parentId;
			return this;
		}

		public BudgetFilter build() {
			BudgetFilter filter = new BudgetFilter();
			filter.ids = ids;
			filter.accountIds = accountIds;
			filter.categoryIds = categoryIds;
			filter.type = type;
			filter.name = name;
			filter.targetAmountFrom = targetAmountFrom;
			filter.targetAmountTo = targetAmountTo;
			filter.spentAmountFrom = spentAmountFrom;
			filter.spentAmountTo = spentAmountTo;
			filter.startDate = startDate;
			filter.endDate = endDate;
			filter.description = description;
			filter.matchAllAccounts = matchAllAccounts;
			filter.matchAllCategories = matchAllCategories;
			filter.allowOverlappingDates = allowOverlappingDates;
			filter.parentId = parentId;

			return filter;
		}
	}
}
