package com.meniga.sdk.models.user.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum Gender implements Serializable {
	@SerializedName("1")
	FEMALE,
	@SerializedName("2")
	MALE;

	@Override
	public String toString() {
		if (this == Gender.MALE)
			return "male";
		else if (this == Gender.FEMALE)
			return "female";
		else
			return "unknown";
	}
}
