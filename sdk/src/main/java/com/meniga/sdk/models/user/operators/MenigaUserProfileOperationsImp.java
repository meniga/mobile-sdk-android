package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaUserProfile;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUserProfileOperationsImp implements MenigaUserProfileOperations {

	@Override
	public Result<MenigaUserProfile> getUserProfile() {
		return MenigaSDK.executor().getUserProfile();
	}
}
