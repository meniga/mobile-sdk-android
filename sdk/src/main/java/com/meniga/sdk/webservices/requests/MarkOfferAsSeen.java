package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MarkOfferAsSeen extends QueryRequestObject {

	public long id;

	public MarkOfferAsSeen(long id) {
		this.id = id;
	}

	@Override
	public long getValueHash() {
		return this.id;
	}
}
