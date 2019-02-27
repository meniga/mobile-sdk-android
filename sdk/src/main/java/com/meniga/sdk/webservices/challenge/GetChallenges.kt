/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class GetChallenges(
        @JvmField var includeExpired: Boolean = false,
        @JvmField var excludeSuggested: Boolean = false,
        @JvmField var excludeAccepted: Boolean = false,
        @JvmField var includeDisabled: Boolean = false
) : QueryRequestObject() {

    override fun toQueryMap(): Map<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                queryMap["includeExpired"] = includeExpired.toString()
                queryMap["excludeSuggested"] = excludeSuggested.toString()
                queryMap["excludeAccepted"] = excludeAccepted.toString()
                queryMap["includeDisabled"] = includeDisabled.toString()
            }

    override fun getValueHash(): Long = hashCode().toLong()
}
