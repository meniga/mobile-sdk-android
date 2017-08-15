package com.meniga.sdk.models.sync.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.MenigaSyncStatus;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaSyncOperations {
	Result<MenigaSync> startSync(long timeout);

	Result<MenigaSyncStatus> getSyncStatus();

	Result<MenigaSync> getSync(long syncHistoryId);
}
