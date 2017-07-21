package com.meniga.sdk.models.offers.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum

OfferFilterState implements Serializable {

	@SerializedName("All")
	ALL,
	@SerializedName("Activated")
	ACTIVATED,
	@SerializedName("Available")
	AVAILABLE,
	@SerializedName("Declined")
	DECLINED,
	@SerializedName("Expired")
	EXPIRED;

	@Override
	public String toString() {
		switch (this) {
			case ALL:
				return "all";
			case ACTIVATED:
				return "activated";
			case AVAILABLE:
				return "available";
			case DECLINED:
				return "declined";
			case EXPIRED:
				return "expired";
			default:
				return "all";
		}
	}
}

