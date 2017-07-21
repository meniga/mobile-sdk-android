package com.meniga.sdk.models.user.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum ApartmentType implements Serializable {
	@SerializedName("1")
	Apartment,
	@SerializedName("2")
	House,
	@SerializedName("3")
	Duplex;

	@Override
	public String toString() {
		if (this == ApartmentType.Apartment)
			return "apartment";
		else if (this == ApartmentType.House)
			return "house";
		else if (this == ApartmentType.Duplex)
			return "duplex";
		else
			return "unknown";
	}
}
