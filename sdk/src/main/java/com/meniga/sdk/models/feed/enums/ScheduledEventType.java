package com.meniga.sdk.models.feed.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum ScheduledEventType implements Serializable {
	@SerializedName("Weekly")
	WEEKLY,
	@SerializedName("Monthly")
	MONTHLY,
	UNKOWN
}
