package com.meniga.sdk.models.userevents.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.userevents.MenigaUserEvent;
import com.meniga.sdk.models.userevents.enums.UserEventType;
import com.meniga.sdk.webservices.requests.SetSubscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaUserEventsOperationsImp implements MenigaUserEventsOperations {

	@Override
	public Result<List<MenigaUserEvent>> getUserEvents() {
		return MenigaSDK.executor().getUserEvents();
	}

	@Override
	public Result<Void> setSubscription(List<UserEventType> userEventTypeIdentifiers, boolean isSubscribed, String channelName, String reason) {
		SetSubscription req = new SetSubscription();
		req.channelName = channelName;
		req.isSubscribed = isSubscribed;
		req.unsubscriptionReason = reason;
		req.userEventTypeIdentifiers = new ArrayList<>();
		for (UserEventType type : userEventTypeIdentifiers) {
			req.userEventTypeIdentifiers.add(type.toString());
		}

		return MenigaSDK.executor().setSubscription(req);
	}
}
