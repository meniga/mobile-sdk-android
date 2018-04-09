package com.meniga.sdk.models.challenges

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.challenges.enums.ChallengeInterval
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor
import org.joda.time.DateTime

/**
 * @param title The title of the challenge
 * @param description The description of the challenge
 * @param startDate The date when this challenge starts
 * @param endDate The date when this challenge ends
 * @param iconUrl An optional url referring to an icon
 * @param categoryDefinition Definition of tracked categories
 * @param customChallengeColor Color of the challenge being created
 * @param targetAmount The target amount for the challenge
 * @param recurringInterval The interval between repeats, e.g. monthly, annually etc.
 */
data class NewChallenge(
        @JvmField var title: String,
        @JvmField var description: String,
        @JvmField var startDate: DateTime,
        @JvmField var endDate: DateTime? = null,
        @JvmField var iconUrl: String? = null,
        @JvmField var categoryDefinition: CategoryDefinition,
        @JvmField var customChallengeColor: CustomChallengeColor? = null,
        @JvmField var targetAmount: MenigaDecimal,
        @JvmField var recurringInterval: ChallengeInterval? = null)
