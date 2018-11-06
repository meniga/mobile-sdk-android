@file:JvmName("MenigaSimilarBrandSpendingDetailsExtensions")

package com.meniga.sdk.models.offers

import org.joda.time.DateTime

internal fun createMenigaSimilarBrandSpendingDetails(
        startDate: DateTime?,
        endDate: DateTime?,
        brandSpendings: List<MenigaBrandSpending>): MenigaSimilarBrandSpendingDetails {
    return MenigaSimilarBrandSpendingDetails(startDate, endDate, brandSpendings)
}
