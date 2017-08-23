package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateChallengeTypeData extends QueryRequestObject {

	public List<Long> categoryIds;
	public MenigaDecimal targetAmount;
	public String metaData;

	@Override
	public long getValueHash() {
		return 0;
	}
}
