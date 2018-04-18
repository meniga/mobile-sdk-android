/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.converters

import com.google.gson.JsonObject
import com.meniga.sdk.helpers.FeedItemFactory
import com.meniga.sdk.helpers.type
import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.setActualEndDate
import com.meniga.sdk.models.feed.setHasMoreData
import com.meniga.sdk.models.feed.setTotalCount
import okhttp3.ResponseBody
import org.joda.time.DateTime
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class MenigaFeedConverter(private val feedItemFactory: FeedItemFactory) : MenigaConverter() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return when (type) {
            type<MenigaFeed>() -> Converter<ResponseBody, MenigaFeed> { resBody ->
                MenigaFeed().apply {
                    val (data, meta) = MenigaConverter.getAsArrayApiResponse(resBody.byteStream())

                    data.forEach {
                        add(feedItemFactory.getMenigaFeetItem(it as JsonObject))
                    }

                    meta?.let {
                        setActualEndDate(DateTime.parse(it.get("actualEndDate").asString))
                        setHasMoreData(it.get("hasMoreData").asBoolean)
                        setTotalCount(it.get("totalCount").asInt)
                    }
                }
            }
            else -> null
        }
    }
}
