package com.meniga.sdk.models.budget;

import org.joda.time.DateTime;

import javax.annotation.Nullable;

public class Applying {

	private final int every;
	private final DateTime until;

	public int getEvery() {
		return every;
	}

	@Nullable
	public DateTime getUntil() {
		return until;
	}

	private Applying(int every, @Nullable DateTime until) {
		this.every = every;
		this.until = until;
	}

	public static Builder every(int months) {
		return new Builder(months);
	}

	public static Applying always() {
		return Applying.every(1).build();
	}

	public static final class Builder {
		private int months;
		private DateTime until;

		private Builder(int months) {
			this.months = months;
		}

		public Applying until(@Nullable DateTime until) {
			this.until = until;
			return build();
		}

		private Applying build() {
			return new Applying(months, until);
		}
	}
}
