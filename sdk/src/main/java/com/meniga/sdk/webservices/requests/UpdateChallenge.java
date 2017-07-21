package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateChallenge extends QueryRequestObject {

	public transient UUID id;
	/*public String title;
	public String description;
	public DateTime startDate;
	public DateTime endDate;
	public String iconUrl;*/
	public TypeData typeData;

	@Override
	public long getValueHash() {
		return id.hashCode();
	}

	public static class TypeData {
		public MenigaDecimal targetAmount;
		//public List<Long> categoryIds;
		//public String metaData;
	}
}
