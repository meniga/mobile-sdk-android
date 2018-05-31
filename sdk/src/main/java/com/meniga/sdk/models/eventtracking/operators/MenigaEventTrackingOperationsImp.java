package com.meniga.sdk.models.eventtracking.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.eventtracking.MenigaEventTracking;
import com.meniga.sdk.webservices.eventtracking.TrackEvent;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 31.5.2018.
 */
public class MenigaEventTrackingOperationsImp implements MenigaEventTrackingOperations {
	@Override
	public Result<Void> track(MenigaEventTracking event) {
		TrackEvent req = new TrackEvent(event.getTrackingType(), event.getTrackingState(), event.getTrackerId(), event.getMedia());
		return MenigaSDK.executor().trackEvent(req);
	}
}
