package com.meniga.sdk.helpers;

import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.eventconverters.generic.MenigaAccountAvailableAmountEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaCustomEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaTransactionCountEventConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MockFeedItemFactory extends FeedItemFactory {

	@Override
	protected List<EventBaseConverter> getUserEventFeedConverters() {
		List<EventBaseConverter> mockConverters = new ArrayList<>();
		mockConverters.add(new MenigaAccountAvailableAmountEventConverter());
		mockConverters.add(new MenigaCustomEventConverter());
		mockConverters.add(new MenigaTransactionCountEventConverter());
		return mockConverters;
	}
}
