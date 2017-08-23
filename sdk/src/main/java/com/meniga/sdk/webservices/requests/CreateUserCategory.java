package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateUserCategory extends QueryRequestObject {

	public String name;
	public boolean isFixedExpenses;
	public int categoryType;
	public Long parentId;

	@Override
	public long getValueHash() {
		return 0;
	}
}
