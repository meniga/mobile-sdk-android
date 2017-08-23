package com.meniga.sdk.models.budget;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BudgetFilter {

	/**
	 * Category Ids linked to budget buckets.
	 */
	private List<Long> categoryIds;
	/**
	 * Lower date limit for periods on budget buckets.
	 */
	private DateTime startDate;
	/**
	 * Upper date limit for periods on budget buckets.
	 */
	private DateTime endDate;
	/**
	 * Flag controlling how dates are handled in searches for budget buckets. When true all budget buckets
	 * with period that intersects the filter dates are returned. Otherwise only budget buckets with period
	 * included in the filter period are returned.
	 */
	private Boolean allowOverlappingDates;

	/**
	 * Include the budget entries of the budget
	 */
	private Boolean includeEntries;

	private BudgetFilter() {
	}

	public Map<String, String> asMap() {
		Map<String, String> map = new HashMap<>();

		StringBuilder catIdsBld = new StringBuilder();
		if (categoryIds != null) {
			for (Long id : categoryIds) {
				if (catIdsBld.length() > 0) {
					catIdsBld.append(",");
				}
				catIdsBld.append(id);
			}
		}

		map.put("categoryIds", catIdsBld.toString());

		if (startDate != null) {
			map.put("startDate", startDate.toString());
		}

		if (endDate != null) {
			map.put("endDate", endDate.toString());
		}

		if (includeEntries != null) {
			map.put("includeEntries", Boolean.toString(includeEntries));
		}
		if (allowOverlappingDates != null) {
			map.put("allowOverlappingDates", Boolean.toString(allowOverlappingDates));
		}


		return map;
	}


	public static class Builder {

		private List<Long> categoryIds;
		private DateTime startDate;
		private DateTime endDate;
		private Boolean allowOverlappingDates;
		private Boolean includeEntries;

		/**
		 * {@link BudgetFilter#includeEntries}
		 */
		public void setIncludeEntries(Boolean includeEntries) {
			this.includeEntries = includeEntries;
		}


		/**
		 * {@link BudgetFilter#categoryIds}
		 */
		public Builder setCategoryIds(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
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
		 * {@link BudgetFilter#allowOverlappingDates}
		 */
		public Builder setAllowOverlappingDates(Boolean allowOverlappingDates) {
			this.allowOverlappingDates = allowOverlappingDates;
			return this;
		}

		public BudgetFilter build() {
			BudgetFilter filter = new BudgetFilter();

			filter.categoryIds = categoryIds;
			filter.startDate = startDate;
			filter.endDate = endDate;
			filter.allowOverlappingDates = allowOverlappingDates;
			filter.includeEntries = includeEntries;

			return filter;
		}
	}
}
