package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaSync;
import com.meniga.sdk.models.user.MenigaSyncStatus;
import com.meniga.sdk.webservices.requests.GetSync;
import com.meniga.sdk.webservices.requests.StartSync;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public final class MenigaSyncOperationsImp implements MenigaSyncOperations {

	@Override
	public Result<MenigaSync> startSync(long timeout) {
		StartSync req = new StartSync();
		req.waitForCompleteMilliseconds = timeout;

		return MenigaSDK.executor().startSync(req);
	}

	@Override
	public Result<MenigaSyncStatus> getSyncStatus() {
		return MenigaSDK.executor().getSyncStatus();
	}

	@Override
	public Result<MenigaSync> getSync(long syncHistoryId) {
		GetSync req = new GetSync();
		req.syncHistoryId = syncHistoryId;
		return MenigaSDK.executor().getSync(req);
	}
}
