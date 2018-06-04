package com.meniga.sdk.models.eventtracking.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.eventtracking.MenigaEventTracking;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 31.5.2018.
 */
public interface MenigaEventTrackingOperations {
	Result<Void> track(MenigaEventTracking event);
}
