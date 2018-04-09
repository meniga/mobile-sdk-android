/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.webservices.requests.QueryRequestObject
import java.util.UUID

internal data class GetChallengeHistory(
        @JvmField var id: UUID,
        @JvmField var skip: Int = 0,
        @JvmField var take: Int? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    override fun toQueryMap(): Map<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                skip.let { queryMap["skip"] = Integer.toString(it) }
                take?.let { queryMap["take"] = Integer.toString(it) }
            }
}
