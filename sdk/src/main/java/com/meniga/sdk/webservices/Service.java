package com.meniga.sdk.webservices;

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
	BUDGET("budget"),
	TERMS("terms"),
	// Bypass is not a service but refers to the free-form http call mechanism in the sdk
	BYPASS("bypass");

	private String key;

	Service(String key) {
		this.key = key;
	}
}
