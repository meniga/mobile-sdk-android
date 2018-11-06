/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.converters

import com.google.gson.Gson
import com.meniga.sdk.helpers.type
import com.meniga.sdk.models.offers.MenigaBrandSpending
import com.meniga.sdk.models.offers.MenigaSimilarBrandSpendingDetails
import com.meniga.sdk.models.offers.createMenigaSimilarBrandSpendingDetails
import okhttp3.ResponseBody
import org.joda.time.DateTime
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class MenigaSimilarBrandSpendingConverter(private val gson: Gson) : MenigaConverter() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return when (type) {
            type<MenigaSimilarBrandSpendingDetails>() -> Converter<ResponseBody, MenigaSimilarBrandSpendingDetails> { resBody ->
                val (data, meta) = MenigaConverter.getAsArrayApiResponse(resBody.byteStream())

                val startDate: DateTime? =
                        meta?.let { metaObject ->
                            metaObject.get("startDate")
                                    ?.takeUnless { it.isJsonNull }
                                    ?.let { DateTime.parse(it.asString) }
                        }
                val endDate: DateTime? =
                        meta?.let { metaObject ->
                            metaObject.get("endDate")
                                    ?.takeUnless { it.isJsonNull }
                                    ?.let { DateTime.parse(it.asString) }
                        }

                val brandSpendings: List<MenigaBrandSpending> = data.map {
                    gson.fromJson(it, MenigaBrandSpending::class.java)
                }

                createMenigaSimilarBrandSpendingDetails(startDate, endDate, brandSpendings)
            }
            else -> null
        }
    }
}
