package com.meniga.sdk.webservices.requests;

import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetChallenge extends QueryRequestObject {

	public UUID id;

	@Override
	public long getValueHash() {
		return id != null ? id.hashCode() : 0;
	}
}
