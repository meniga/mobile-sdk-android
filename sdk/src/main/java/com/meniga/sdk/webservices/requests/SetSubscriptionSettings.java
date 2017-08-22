package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class SetSubscriptionSettings extends QueryRequestObject {

	public List<SubscriptionSettings> subscriptionSettings;

	@Override
	public long getValueHash() {
		return subscriptionSettings != null ? subscriptionSettings.hashCode() : 0;
	}

	public static class SubscriptionSettings {

		public String identifier;
		public String value;

		@Override
		public int hashCode() {
			int result = identifier != null ? identifier.hashCode() : 0;
			result = 31 * result + (value != null ? value.hashCode() : 0);
			return result;
		}
	}
}
