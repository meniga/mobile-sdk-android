/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.categories.enums.CategoryType
import com.meniga.sdk.models.challenges.enums.ChallengeInterval
import com.meniga.sdk.webservices.requests.QueryRequestObject

import org.joda.time.DateTime

internal data class CreateChallenge(
        @JvmField var title: String,
        @JvmField var description: String,
        @JvmField var startDate: DateTime,
        @JvmField var endDate: DateTime? = null,
        @JvmField var iconUrl: String? = null,
        @JvmField var typeData: CreateChallengeTypeData
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    internal data class CreateChallengeTypeData(
            var categoryIds: List<Long>? = null,
            var categoryType: CategoryType? = null,
            var targetAmount: MenigaDecimal? = null,
            var metaData: String? = null,
            var recurringInterval: ChallengeInterval? = null
    )
}
