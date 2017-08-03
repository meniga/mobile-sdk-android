package com.meniga.sdk.models.accounts.enums;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Type of authorizations during account aggregation
 */
public enum AccountAuthorizationType implements Serializable {
	NONE,
	EXTERNAL,
	INTERNAL,
	EXTERNAL_MULTIFACTOR;

	@Override
	public String toString() {
		switch (this) {
			case NONE:
				return "none";
			case EXTERNAL:
				return "external";
			case INTERNAL:
				return "internal";
			case EXTERNAL_MULTIFACTOR:
				return "external multifactor";
			default:
				return "none";
		}
	}
}
