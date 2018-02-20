package com.meniga.sdk.webservices;

import com.annimon.stream.Stream;

import static com.annimon.stream.Objects.requireNonNull;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum Service {
	ALL("all"),
	ACCOUNTS("accounts"),
	AUTHENTICATION("authentication"),
	CATEGORIES("categories"),
	EVENT_TRACKING("eventtracking"),
	FEED("feed"),
	MERCHANTS("merchants"),
	NET_WORTH("networth"),
	ORGANIZATIONS("organizations"),
	PUBLIC("public"),
	SYNC("sync"),
	TAGS("tags"),
	TRANSACTIONS("transactions"),
	UPCOMING("upcoming"),
	USER_EVENTS("userevents"),
	USERS("users"),
	OFFERS("offers"),
	CHALLENGES("challenges"),
	BUDGET("budget", BudgetService.class),
	TERMS("terms"),
	// Bypass is not a service but refers to the free-form http call mechanism in the sdk
	BYPASS("bypass");

	private final String key;
	private final Class<?> serviceClass;

	Service(String key) {
		this(key, null);
	}

	Service(String key, Class<?> serviceClass) {
		this.key = requireNonNull(key);
		this.serviceClass = serviceClass;
	}

	public static <T> Service from(Class<T> serviceClass) {
		return Stream.of(values())
				.filter(value -> serviceClass.equals(value.serviceClass))
				.single();
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getServiceClass() {
		return (Class<T>) serviceClass;
	}
}
