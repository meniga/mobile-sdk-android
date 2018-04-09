package com.meniga.sdk.models.challenges

import com.meniga.sdk.models.categories.enums.CategoryType

/**
 * @param includeExpired Include expired challenges in the response. Default: false (expired challenges are not included in the response)
 * @param excludeSuggested Skip suggested/system challenges in the response. Default: false (suggested/system challenges are included in the response)
 * @param excludeAccepted Skip accepted challenges in the response. Default: false (accepted challenges are included in the response)
 * @param includeDisabled Include ignored/disabled challenges in the response. Default: false (ignored/disabled challenges are not included in the response)
 */
data class FetchChallengeFilter(
        @JvmField var includeExpired: Boolean = true,
        @JvmField var excludeSuggested: Boolean = false,
        @JvmField var excludeAccepted: Boolean = false,
        @JvmField var includeDisabled: Boolean = true
)

/**
 * Defines categories to be tracked
 */
sealed class CategoryDefinition {

    /**
     * @param categoryIds The ids of the categories which this challenge tracks
     */
    data class CategoryList(var categoryIds: List<Long>) : CategoryDefinition()

    /**
     * @param categoryType The type of categories this challenge tracks
     */
    data class Type(var categoryType: CategoryType) : CategoryDefinition()
}
