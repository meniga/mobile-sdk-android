package com.meniga.sdk;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 17.7.2019.
 */
public class SpecialServiceEndpointDefinition {
	private final String endpoint;
	private final long timeout;

	SpecialServiceEndpointDefinition(String endpoint, int timeout) {
		this.endpoint = endpoint;
		this.timeout = timeout;
	}

	public SpecialServiceEndpointDefinition(String endpoint) {
		this(endpoint, -1);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public long getTimeout() {
		return timeout;
	}
}
