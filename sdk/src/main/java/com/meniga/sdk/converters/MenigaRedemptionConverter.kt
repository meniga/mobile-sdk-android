package com.meniga.sdk.converters

import com.google.gson.Gson
import com.meniga.sdk.helpers.type
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions
import com.meniga.sdk.models.offers.redemptions.MenigaScheduledReimbursement
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
class MenigaRedemptionConverter(private val gson: Gson) : MenigaConverter() {
    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return when (type) {
            type<MenigaRedemptions>() -> Converter<ResponseBody, MenigaRedemptions> { resBody ->
                val (data, meta) = MenigaConverter.getAsArrayApiResponse(resBody.byteStream())

                var menigaRedemptions = gson.fromJson(data, MenigaRedemptions::class.java)

                val cashBackRaw = meta?.let {
                    gson.fromJson<List<MenigaScheduledReimbursement>>(it.getAsJsonArray("scheduledReimbursements"), type<List<MenigaScheduledReimbursement>>())
                }
                meta?.let {
                    menigaRedemptions = MenigaConverter.mergeMeta(gson, menigaRedemptions, it)
                }

                menigaRedemptions.setScheduledReimbursement(cashBackRaw)
                menigaRedemptions
            }
            else -> null
        }
    }
}
