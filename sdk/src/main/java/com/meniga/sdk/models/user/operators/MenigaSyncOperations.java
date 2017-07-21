package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaSync;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaSyncOperations {
	Result<MenigaSync> startSync(long timeout);

	Result<MenigaSync.MenigaSyncStatus> getSyncStatus();

	Result<MenigaSync> getSync(long syncHistoryId);
}
