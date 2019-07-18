package com.meniga.sdk.models.sync.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.MenigaSyncStatus;
import com.meniga.sdk.webservices.requests.GetSync;
import com.meniga.sdk.webservices.requests.StartRealmSync;
import com.meniga.sdk.webservices.requests.StartSync;

import javax.annotation.Nullable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public final class MenigaSyncOperationsImp implements MenigaSyncOperations {
	@Override
	public Result<MenigaSync> startSync(Long realmUserId, @Nullable String sessionToken, long timeout) {
		if (realmUserId == null) {
			StartSync req = new StartSync();
			req.waitForCompleteMilliseconds = timeout;
			return MenigaSDK.executor().startSync(req);
		} else {
			StartRealmSync req = new StartRealmSync();
			req.realmUserId = realmUserId;
			req.waitForCompleteMilliseconds = timeout;
			req.sessionToken = sessionToken;
			return MenigaSDK.executor().startRealmSync(req);
		}
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
