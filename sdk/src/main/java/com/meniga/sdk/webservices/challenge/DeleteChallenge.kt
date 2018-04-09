/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.webservices.requests.QueryRequestObject

import java.util.UUID

internal data class DeleteChallenge(
        @JvmField var id: UUID
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
