package com.meniga.sdk.helpers;

import org.joda.time.LocalDate;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BudgetDate implements Serializable {
	private LocalDate date;

	public BudgetDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getLocalDate() {
		return this.date;
	}

	public void setLocalDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BudgetDate that = (BudgetDate) o;

		return date.equals(that.date);

	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}
}
