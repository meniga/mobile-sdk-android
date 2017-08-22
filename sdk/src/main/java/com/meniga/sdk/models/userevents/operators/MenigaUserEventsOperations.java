package com.meniga.sdk.models.userevents.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.userevents.MenigaUserEvent;
import com.meniga.sdk.models.userevents.enums.UserEventType;

import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public interface MenigaUserEventsOperations {

	Result<List<MenigaUserEvent>> getUserEvents();

	Result<Void> setSubscription(List<UserEventType> userEventTypeIdentifiers, boolean isSubscribed, String channelName, String reason);

	Result<Void> updateSettings(Map<String, String> subscriptionSettings);
}
