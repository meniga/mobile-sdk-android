/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices

import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.converters.*
import com.meniga.sdk.helpers.ChallengeItemFactory
import com.meniga.sdk.helpers.FeedItemFactory
import com.meniga.sdk.helpers.GsonProvider
import com.meniga.sdk.webservices.interceptors.AcceptLanguageInterceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object WebClient {

    private val cookieJar = JavaNetCookieJar(CookieManager())

    fun createApiInterfaces(settings: MenigaSettings): Map<Service, *> {
        // First do the default config
        val endpoints: MutableMap<Service, Any> = mutableMapOf()

        // Create the default retrofit and client for all services.
        val defaultRetrofit = buildRetrofit(Service.ALL, settings)
        endpoints[Service.ALL] = defaultRetrofit.create(MenigaAPI::class.java)

        Service.values()
                .forEach { service ->
                    service.serviceClass
                            ?.let { serviceClass -> endpoints[service] = defaultRetrofit.create(serviceClass) }
                }

        settings.specialServiceEndpoints.keys
                .forEach { service ->
                    val specialRetrofit = buildRetrofit(service, settings)
                    endpoints[service] = service.serviceClass?.let { specialRetrofit.create(it) }
                            ?: specialRetrofit.create(MenigaAPI::class.java)
                }

        return endpoints
    }

    private fun buildRetrofit(service: Service, settings: MenigaSettings): Retrofit {
        val builder = OkHttpClient.Builder()
                .cookieJar(WebClient.cookieJar)
                .readTimeout(settings.timeout, TimeUnit.SECONDS)
                .writeTimeout(settings.timeout, TimeUnit.SECONDS)
                .connectTimeout(settings.timeout, TimeUnit.SECONDS)

        settings.authenticator?.let { builder.authenticator(it) }
        settings.sslSocketFactory?.let { builder.sslSocketFactory(it, settings.x509TrustManager) }
        settings.httpInterceptors?.forEach { builder.addInterceptor(it) }
        settings.networkInterceptors?.forEach { builder.addNetworkInterceptor(it) }
        builder.addInterceptor(AcceptLanguageInterceptor())

        val client = builder.build()

        val endpoint = if (settings.specialServiceEndpoints.containsKey(service))
            fixEndpoint(settings.specialServiceEndpoints[service]!!)
        else
            fixEndpoint(settings.endpoint.toString())

        return Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(MenigaTransactionsConverter())
                .addConverterFactory(MenigaTransactionUpdateConverter())
                .addConverterFactory(MenigaFeedConverter(FeedItemFactory()))
                .addConverterFactory(MenigaCategoryConverter())
                .addConverterFactory(MenigaOfferConverter())
                .addConverterFactory(MenigaRedemptionConverter())
                .addConverterFactory(MenigaReimbursementAccountConverter())
                .addConverterFactory(MenigaIdNameKeyValConverter())
                .addConverterFactory(MenigaChallengesConverter(ChallengeItemFactory()))
                .addConverterFactory(MenigaFeedItemConverter(FeedItemFactory()))
                .addConverterFactory(MenigaBaseConverter<Any>())
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.getGsonBuilder()))
                .client(client)
                .build()
    }

    private fun fixEndpoint(endpoint: String): String {
        return if (!endpoint.endsWith("/")) endpoint + "/" else endpoint
    }
}
