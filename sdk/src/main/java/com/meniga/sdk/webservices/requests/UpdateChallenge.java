package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.helpers.MenigaDecimal;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateChallenge extends QueryRequestObject {
	public transient UUID id;
	public String title;
	public String description;
	public DateTime startDate;
	public DateTime endDate;
	public String iconUrl;
	public TypeData typeData;

    @Override
	public long getValueHash() {
	    int result = id != null ? id.hashCode() : 0;
	    result = 31 * result + (title != null ? title.hashCode() : 0);
	    result = 31 * result + (description != null ? description.hashCode() : 0);
	    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
	    result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
	    result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
	    result = 31 * result + (typeData != null ? typeData.hashCode() : 0);
	    return result;
	}

	public static class TypeData {
		public MenigaDecimal targetAmount;
		public List<Long> categoryIds;
		public String metaData;

		@Override
		public int hashCode() {
			int result = targetAmount != null ? targetAmount.hashCode() : 0;
			result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
			result = 31 * result + (metaData != null ? metaData.hashCode() : 0);
			return result;
		}
	}
}
