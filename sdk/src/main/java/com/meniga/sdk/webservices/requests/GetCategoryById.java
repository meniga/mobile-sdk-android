package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetCategoryById extends QueryRequestObject {

	public long categoryId;

	@Override
	public long getValueHash() {
		return this.categoryId;
	}
}
