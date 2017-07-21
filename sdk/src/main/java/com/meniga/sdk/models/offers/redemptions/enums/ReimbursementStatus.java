package com.meniga.sdk.models.offers.redemptions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum ReimbursementStatus implements Serializable {
	@SerializedName("Pending")
	PENDING,
	@SerializedName("Processing")
	PROCESSING,
	@SerializedName("Processed")
	PROCESSED,
	@SerializedName("Failed")
	FAILED;

	@Override
	public String toString() {
		switch (this) {

			case PENDING:
				return "Pending";

			case PROCESSING:
				return "Processing";

			case PROCESSED:
				return "Processed";

			case FAILED:
				return "Failed";

			default:
				return "Unknown";
		}
	}
}
