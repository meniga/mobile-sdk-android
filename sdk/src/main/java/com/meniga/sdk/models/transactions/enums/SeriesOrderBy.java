package com.meniga.sdk.models.transactions.enums;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum SeriesOrderBy implements Serializable {
	BY_DATE("ByDate"),
	BY_TEXT("ByText"),
	BY_AMOUNT("ByAmount"),
	BY_CATEGORY("ByCategory"),
	BY_PARSED_DATA("ByParsedData"),
	BY_ORIGINAL_DATE("ByOriginalDate");

	private final String name;

	SeriesOrderBy(String nameIn) {
		name = nameIn;
	}

	@Override
	public String toString() {
		return name;
	}
}
