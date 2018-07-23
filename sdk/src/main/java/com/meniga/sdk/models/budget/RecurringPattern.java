package com.meniga.sdk.models.budget;

import org.joda.time.DateTime;

import javax.annotation.Nonnull;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class RecurringPattern {

	private final int months;
	@Nonnull
	private final DateTime starting;
	@Nonnull
	private final DateTime until;

	private RecurringPattern(int months, @Nonnull DateTime starting, @Nonnull DateTime until) {
		this.months = months;
		this.starting = requireNonNull(starting);
		this.until = requireNonNull(until);
	}

	public static Builder everyMonths(int months) {
		return new Builder(months);
	}

	public int getMonths() {
		return months;
	}

	@Nonnull
	public DateTime getStarting() {
		return starting.withTimeAtStartOfDay().withDayOfMonth(1);
	}

	@Nonnull
	public DateTime getEndDate() {
		return getStarting().plusMonths(1).minusMillis(1);
	}

	@Nonnull
	public DateTime getUntil() {
		return until;
	}

	public static class Builder {
		private final int months;
		private DateTime starting;

		public Builder(int months) {
			this.months = months;
		}

		public Builder starting(@Nonnull DateTime starting) {
			this.starting = requireNonNull(starting);
			return this;
		}
		public RecurringPattern until(@Nonnull DateTime until) {
			return new RecurringPattern(months, starting, until);
		}

		public RecurringPattern withoutEndDate() {
			return new RecurringPattern(months, starting, DateTime.now().plusYears(16).withTimeAtStartOfDay());
		}
	}
}
