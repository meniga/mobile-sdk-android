package com.meniga.sdk.models.organizations.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.organizations.MenigaOrganization;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaOrganizationOperations {
	Result<List<MenigaOrganization>> getOrganizations();
}
