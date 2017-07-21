package com.meniga.sdk.models.serverpublic.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.serverpublic.MenigaPublicSettings;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaPublicSettingsOperations {
	Result<MenigaPublicSettings> getPublicSettings();
}
