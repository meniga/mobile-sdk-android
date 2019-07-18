package com.meniga.sdk;

import javax.annotation.Nonnull;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 17.7.2019.
 */
public class SpecialServiceEndpointDefinition {
	private final String endpoint;
	private final long timeoutInSeconds;

	SpecialServiceEndpointDefinition(@Nonnull String endpoint, int timeoutInSeconds) {
		this.endpoint = requireNonNull(endpoint);
		this.timeoutInSeconds = timeoutInSeconds;
	}

	public SpecialServiceEndpointDefinition(@Nonnull String endpoint) {
		this(endpoint, -1);
	}

	@Nonnull
	public String getEndpoint() {
		return endpoint;
	}

	public long getTimeoutInSeconds() {
		return timeoutInSeconds;
	}
}
