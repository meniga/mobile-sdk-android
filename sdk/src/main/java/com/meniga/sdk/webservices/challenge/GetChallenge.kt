/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.webservices.requests.QueryRequestObject

import java.util.UUID

data class GetChallenge(
        @JvmField var id: UUID
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
