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
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (isHidden ? 1 : 0);
		result = 31 * result + (isDisabled ? 1 : 0);
		result = 31 * result + (emergencyFundBalanceLimit != null ? emergencyFundBalanceLimit.hashCode() : 0);
		return result;
	}
}
