/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices

import com.meniga.sdk.webservices.account.AccountService
import com.meniga.sdk.webservices.budget.BudgetService
import com.meniga.sdk.webservices.challenge.ChallengeService
import com.meniga.sdk.webservices.eventtracking.EventTrackingService
import com.meniga.sdk.webservices.offers.OffersService
import com.meniga.sdk.webservices.user.UsersService

enum class Service(
        private val key: String,
        val serviceClass: Class<*>? = null) {
    ALL("all"),
    ACCOUNTS("accounts", AccountService::class.java),
    AUTHENTICATION("authentication"),
    CATEGORIES("categories"),
    EVENT_TRACKING("eventtracking", EventTrackingService::class.java),
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
    USERS("users", UsersService::class.java),
    OFFERS("offers", OffersService::class.java),
    CHALLENGES("challenges", ChallengeService::class.java),
    BUDGET("budget", BudgetService::class.java),
    TERMS("terms"),
    // Bypass is not a service but refers to the free-form http call mechanism in the sdk
    BYPASS("bypass");

    companion object {

        @JvmStatic
        fun <T> from(serviceClass: Class<T>): Service =
                values().single { it.serviceClass == serviceClass }
    }
}
