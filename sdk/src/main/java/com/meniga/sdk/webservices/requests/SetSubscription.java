package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class SetSubscription extends QueryRequestObject {

	public Boolean isSubscribed;
	public String channelName;
	public List<String> userEventTypeIdentifiers;
	public String unsubscriptionReason;

	@Override
	public long getValueHash() {
		int result = isSubscribed != null ? isSubscribed.hashCode() : 0;
		result = 31 * result + (channelName != null ? channelName.hashCode() : 0);
		result = 31 * result + (userEventTypeIdentifiers != null ? userEventTypeIdentifiers.hashCode() : 0);
		result = 31 * result + (unsubscriptionReason != null ? unsubscriptionReason.hashCode() : 0);
		return result;
	}
}
