/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.requests.QueryRequestObject
import java.util.UUID

/**
 * @param id The id of the challenge to accept.
 * @param targetAmount The target amount for the challenge. If not specified, the target amount will be calculated according to the target percentage of the challenge.
 */
internal data class AcceptChallenge(
        @JvmField var id: UUID? = null,
        @JvmField var targetAmount: MenigaDecimal? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
