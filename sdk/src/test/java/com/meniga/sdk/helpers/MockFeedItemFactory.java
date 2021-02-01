package com.meniga.sdk.helpers;

import androidx.annotation.NonNull;

import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.eventconverters.generic.MenigaAccountAvailableAmountEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaCustomEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaTransactionEventConverter;
import com.meniga.sdk.eventconverters.generic.MenigaTransactionCountEventConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MockFeedItemFactory extends FeedItemFactory {

	@Override
	@NonNull
	protected List<EventBaseConverter<?>> getUserEventFeedConverters() {
		List<EventBaseConverter<?>> mockConverters = new ArrayList<>();
		mockConverters.add(new MenigaTransactionEventConverter());
		mockConverters.add(new MenigaAccountAvailableAmountEventConverter());
		mockConverters.add(new MenigaCustomEventConverter());
		mockConverters.add(new MenigaTransactionCountEventConverter());
		return mockConverters;
	}
}
