/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.helpers

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meniga.sdk.models.categories.MenigaCategoryScore
import com.meniga.sdk.webservices.serializers.BudgetDateSerializer
import com.meniga.sdk.webservices.serializers.DateTimeSerializer
import com.meniga.sdk.webservices.serializers.LocalDateSerializer
import com.meniga.sdk.webservices.serializers.MenigaCategoryScoreSerializer
import com.meniga.sdk.webservices.serializers.MenigaDecimalSerializer

import org.joda.time.DateTime
import org.joda.time.LocalDate

object GsonProvider {

    @JvmStatic
    val gson: Gson by lazy {
        GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(DateTime::class.java, DateTimeSerializer())
                .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
                .registerTypeAdapter(MenigaDecimal::class.java, MenigaDecimalSerializer())
                .registerTypeAdapter(BudgetDate::class.java, BudgetDateSerializer())
                .registerTypeAdapter(MenigaCategoryScore::class.java, MenigaCategoryScoreSerializer())
                .create()
    }
}
