package com.meniga.sdk.models.terms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public enum TermsState implements Serializable {
	@SerializedName("Declined")
	DECLINED,
	@SerializedName("Accepted")
	ACCEPTED,
	@SerializedName("NoInput")
	NOINPUT;

	@Override
	public String toString() {
		switch (this) {
			case DECLINED:
				return "declined";
			case ACCEPTED:
				return "accepted";
			case NOINPUT:
				return "no_input";
			default:
				return "no_input";
		}
	}
}
