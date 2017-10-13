package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.organizations.MenigaRealmAuthParameter;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetRealmAuthMethod extends QueryRequestObject {

	public transient long id;
	public List<MenigaRealmAuthParameter.SimpleAuthParameter> parameters;
	public Boolean saveDetails;
	public String realmUserIdentifier;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
		result = 31 * result + (saveDetails != null ? saveDetails.hashCode() : 0);
		result = 31 * result + (realmUserIdentifier != null ? realmUserIdentifier.hashCode() : 0);
		return result;
	}
}
