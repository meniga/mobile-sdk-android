package com.meniga.sdk.models.organizations.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.organizations.MenigaOrganization;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOrganizationOperationsImp implements MenigaOrganizationOperations {
	@Override
	public Result<List<MenigaOrganization>> getOrganizations() {
		return MenigaSDK.executor().getOrganizations();
	}
}
