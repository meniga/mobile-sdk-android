/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.converters

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.meniga.sdk.helpers.FeedItemFactory
import com.meniga.sdk.helpers.get
import com.meniga.sdk.helpers.type
import com.meniga.sdk.models.accounts.MenigaAccount
import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.setActualEndDate
import com.meniga.sdk.models.feed.setHasMoreData
import com.meniga.sdk.models.feed.setTotalCount
import com.meniga.sdk.models.merchants.MenigaMerchant
import okhttp3.ResponseBody
import org.joda.time.DateTime
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MenigaFeedConverter(
        private val feedItemFactory: FeedItemFactory,
        private val gson: Gson
) : MenigaConverter() {

    private val transactionEventSorter = TransactionEventSorter()

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return when (type) {
            type<MenigaFeed>() -> Converter<ResponseBody, MenigaFeed> { resBody ->
                MenigaFeed().apply {
                    val (data, meta, included) = getAsArrayApiResponse(resBody.byteStream())
                    val (accounts, merchants) = gson.getAccountsAndMerchants(included)

                    data.forEach {
                        add(feedItemFactory.getMenigaFeedItem(it as JsonObject, accounts, merchants))
                    }

                    transactionEventSorter.moveTransactionEventsToTransaction(this)

                    meta?.let {
                        val actualEndDate = it.get("actualEndDate")
                        if (!actualEndDate.isJsonNull) {
                            setActualEndDate(DateTime.parse(actualEndDate.asString))
                        }
                        setHasMoreData(it.get("hasMoreData").asBoolean)
                        setTotalCount(it.get("totalCount").asInt)
                    }
                }
            }
            else -> null
        }
    }

    private fun Gson.getAccountsAndMerchants(included: JsonObject?)
            : Pair<List<MenigaAccount>, List<MenigaMerchant>> = Pair(
            included?.let { get<List<MenigaAccount?>>(it, "accounts") }
                    .orEmpty()
                    .filterNotNull(),
            included?.let { get<List<MenigaMerchant?>>(it, "merchants") }
                    .orEmpty()
                    .filterNotNull()
    )
}
