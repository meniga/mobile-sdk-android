package com.meniga.sdk.models.upcoming.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum PaymentStatus {
	@SerializedName("Open")
	OPEN,
	@SerializedName("Paid")
	PAID,
	@SerializedName("OnHold")
	ON_HOLD
}
