/*
 * Copyright 2017-2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.converters

import com.google.gson.reflect.TypeToken
import com.meniga.sdk.helpers.FeedItemFactory
import com.meniga.sdk.models.feed.MenigaFeedItem
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MenigaFeedItemConverter(private val feedItemFactory: FeedItemFactory) : MenigaConverter() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val typeOfMenigaFeedItem = object : TypeToken<MenigaFeedItem?>() {}.type
        return if (typeOfMenigaFeedItem == type) {
            Converter<ResponseBody, Any> { resBody ->
                val obj = getAsObject(resBody.byteStream())
                feedItemFactory.getMenigaFeedItem(obj)
            }
        } else null
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return null
    }
}
