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
		return 0;
	}
}
