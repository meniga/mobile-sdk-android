/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.webservices.requests.QueryRequestObject

import org.joda.time.DateTime
import java.util.UUID

internal data class UpdateChallenge(
        @JvmField var id: UUID? = null,
        @JvmField var title: String? = null,
        @JvmField var description: String? = null,
        @JvmField var startDate: DateTime? = null,
        @JvmField var endDate: DateTime? = null,
        @JvmField var iconUrl: String? = null,
        @JvmField var typeData: TypeData? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    data class TypeData(
            @JvmField var targetAmount: MenigaDecimal? = null,
            @JvmField var categoryIds: List<Long>? = null,
            @JvmField var metaData: String? = null
    )
}
