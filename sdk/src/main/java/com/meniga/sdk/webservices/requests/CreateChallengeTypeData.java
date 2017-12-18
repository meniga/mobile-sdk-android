package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.challenges.enums.ChallengeInterval;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateChallengeTypeData extends QueryRequestObject {
	public List<Long> categoryIds;
	public MenigaDecimal targetAmount;
	public String metaData;
	public ChallengeInterval recurringInterval;

	@Override
	public long getValueHash() {
		int result = categoryIds != null ? categoryIds.hashCode() : 0;
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (metaData != null ? metaData.hashCode() : 0);
		result = 31 * result + (recurringInterval != null ? recurringInterval.hashCode() : 0);
		return result;
	}
}
