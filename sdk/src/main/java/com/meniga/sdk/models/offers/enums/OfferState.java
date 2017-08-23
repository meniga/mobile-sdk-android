package com.meniga.sdk.models.offers.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum OfferState implements Serializable {

	@SerializedName("Available")
	AVAILABLE,
	@SerializedName(value = "Activated", alternate = {"Accepted"})
	ACTIVATED,
	@SerializedName("Declined")
	DECLINED,
	@SerializedName("Expired")
	EXPIRED;

	@Override
	public String toString() {
		switch (this) {
			case AVAILABLE:
				return "Available";
			case ACTIVATED:
				return "Accepted";
			case DECLINED:
				return "Declined";
			case EXPIRED:
				return "Expired";
			default:
				return null;
		}
	}
}
