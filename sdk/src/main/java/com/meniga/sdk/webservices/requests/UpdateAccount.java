package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class UpdateAccount extends QueryRequestObject {

	public long id;
	public String name;
	public boolean isHidden;
	public boolean isDisabled;
	public Double emergencyFundBalanceLimit;

	@Override
	public long getValueHash() {
		return this.id;
	}
}
