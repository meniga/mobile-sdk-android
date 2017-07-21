package com.meniga.sdk.models.serverpublic.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.serverpublic.MenigaPublicSettings;
import com.meniga.sdk.webservices.requests.GetPublicSettings;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaPublicSettingsOperationsImp implements MenigaPublicSettingsOperations {

	@Override
	public Result<MenigaPublicSettings> getPublicSettings() {
		return MenigaSDK.executor().getPublicSettings(new GetPublicSettings());
	}

}
