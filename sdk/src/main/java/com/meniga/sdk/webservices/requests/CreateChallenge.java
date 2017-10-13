package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class CreateChallenge extends QueryRequestObject {

	public String title;
	public String description;
	public DateTime startDate;
	public DateTime endDate;
	public Long iconId;
	public CreateChallengeTypeData typeData;

	@Override
	public long getValueHash() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (iconId != null ? iconId.hashCode() : 0);
		result = 31 * result + (typeData != null ? typeData.hashCode() : 0);
		return result;
	}

}
