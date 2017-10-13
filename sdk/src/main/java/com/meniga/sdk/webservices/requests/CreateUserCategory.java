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
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (isFixedExpenses ? 1 : 0);
		result = 31 * result + categoryType;
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		return result;
	}
}
