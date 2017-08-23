package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaUserProfile;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaUserProfileOperations {

	Result<MenigaUserProfile> getUserProfile();
}
