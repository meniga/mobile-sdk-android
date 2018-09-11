package com.meniga.sdk.models.upcoming.enums;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 10.9.2018.
 */
public enum ReconcileEntityType {
	INVOICE,
	SCHEDULED_PAYMENT,
	TRANSACTION;

	@Override
	public String toString() {
		switch (this) {
			case INVOICE:
				return "Invoice";
			case SCHEDULED_PAYMENT:
				return "ScheduledPayment";
			case TRANSACTION:
			default:
				return "Transaction";
		}
	}
}
