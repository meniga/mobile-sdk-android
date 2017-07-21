package com.meniga.sdk.models.networth.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum NetWorthType implements Serializable {
	@SerializedName("Asset")
	ASSET,
	@SerializedName("Liability")
	LIABILITY;

	@Override
	public String toString() {
		switch (this) {
			case ASSET:
				return "Asset";
			default:
				return "Liability";
		}
	}
}
