package com.meniga.sdk.webservices.requests

import com.google.gson.annotations.SerializedName
import com.meniga.sdk.models.offers.enums.OfferFilterState

data class GetOffers(
        @JvmField var skip: Int = 0,
        @JvmField var take: Int = 0,
        @JvmField
        @SerializedName("filter.states")
        var filterStates: List<OfferFilterState>? = null,
        @JvmField
        @SerializedName("filter.offerIds")
        var filterOfferIds: List<Long>? = null,
        @JvmField
        @SerializedName("filter.expiredWithRedemptionsOnly")
        var filterExpiredWithRedemptionsOnly: Boolean = false
) : QueryRequestObject() {

    override fun toQueryMap(): MutableMap<String, String> =
            mutableMapOf<String, String>().also { queryMap ->
                skip.let { queryMap["skip"] = it.toString() }
                take.let { queryMap["take"] = it.toString() }
                filterStates?.let { queryMap["filter.states"] = it.joinToString(",") }
                filterOfferIds?.let { queryMap["filter.offerIds"] = it.joinToString(",") }
                filterExpiredWithRedemptionsOnly.toString().let { queryMap["filter.expiredWithRedemptionsOnly"] = it }
            }

    override fun getValueHash() = hashCode().toLong()
}
