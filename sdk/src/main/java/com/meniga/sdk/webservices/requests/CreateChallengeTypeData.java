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
		int result = categoryIds != null ? categoryIds.hashCode() : 0;
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (metaData != null ? metaData.hashCode() : 0);
		return result;
	}
}
