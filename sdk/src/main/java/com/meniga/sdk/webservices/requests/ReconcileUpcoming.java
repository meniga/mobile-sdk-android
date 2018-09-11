package com.meniga.sdk.webservices.requests;

import java.util.Objects;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 10.9.2018.
 */
public class ReconcileUpcoming extends QueryRequestObject {
	public long id;
	public String entityType;
	public long entityId;

	@Override
	public long getValueHash() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (entityType != null ? entityType.hashCode() : 0);
		result = 31 * result + (int) (entityId ^ (entityId >>> 32));
		return result;
	}
}
