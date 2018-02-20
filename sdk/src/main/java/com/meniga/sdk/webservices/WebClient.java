package com.meniga.sdk.webservices;

import com.meniga.sdk.converters.MenigaBaseConverter;
import com.meniga.sdk.converters.MenigaCategoryConverter;
import com.meniga.sdk.converters.MenigaChallengesConverter;
import com.meniga.sdk.converters.MenigaFeedConverter;
import com.meniga.sdk.converters.MenigaFeedItemConverter;
import com.meniga.sdk.converters.MenigaIdNameKeyValConverter;
import com.meniga.sdk.converters.MenigaOfferConverter;
import com.meniga.sdk.converters.MenigaRedemptionConverter;
import com.meniga.sdk.converters.MenigaReimbursementAccountConverter;

import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.converters.MenigaTransactionUpdateConverter;
import com.meniga.sdk.converters.MenigaTransactionsConverter;
import com.meniga.sdk.helpers.ChallengeItemFactory;
import com.meniga.sdk.helpers.FeedItemFactory;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.webservices.interceptors.AcceptLanguageInterceptor;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class WebClient {

	private static Map<Service, Retrofit> retrofits = new HashMap<>();
	private static CookieJar cookieJar = new JavaNetCookieJar(new CookieManager());

	public static Map<Service, MenigaAPI> createApiInterfaces(MenigaSettings settings) {
		// First do the default config
		Map<Service, MenigaAPI> endpoints = new HashMap<>();

		// Create the default retrofit and client for all services.
		WebClient.retrofits.put(Service.ALL, buildRetrofit(Service.ALL, settings));
		endpoints.put(Service.ALL, WebClient.retrofits.get(Service.ALL).create(MenigaAPI.class));

		for (Service service : settings.getSpecialServiceEndpoints().keySet()) {
			WebClient.retrofits.put(service, buildRetrofit(service, settings));
			endpoints.put(service, WebClient.retrofits.get(service).create(MenigaAPI.class));
		}

		return endpoints;
	}

	private static String fixEndpoint(String endpoint) {
		if (!endpoint.endsWith("/")) {
			return endpoint + "/";
		}
		return endpoint;
	}

	private static Retrofit buildRetrofit(Service service, MenigaSettings settings) {
		OkHttpClient.Builder builder = new OkHttpClient
				.Builder()
				.cookieJar(WebClient.cookieJar)
				.readTimeout(settings.getTimeout(), TimeUnit.SECONDS)
				.writeTimeout(settings.getTimeout(), TimeUnit.SECONDS)
				.connectTimeout(settings.getTimeout(), TimeUnit.SECONDS);

		if (settings.getAuthenticator() != null) {
			builder.authenticator(settings.getAuthenticator());
		}

		if (settings.getSslSocketFactory() != null) {
			builder.sslSocketFactory(settings.getSslSocketFactory(), settings.getX509TrustManager());
		}

		for (Interceptor interceptor : settings.getHttpInterceptors()) {
			builder = builder.addInterceptor(interceptor);
		}

		for (Interceptor interceptor : settings.getNetworkInterceptors()) {
			builder = builder.addNetworkInterceptor(interceptor);
		}

		builder = builder.addInterceptor(new AcceptLanguageInterceptor());

		OkHttpClient client = builder.build();

		String endpoint = settings.getSpecialServiceEndpoints().containsKey(service) ?
				fixEndpoint(settings.getSpecialServiceEndpoints().get(service)) : fixEndpoint(settings.getEndpoint().toString());

		return new Retrofit.Builder()
				.baseUrl(endpoint)
				.addConverterFactory(new MenigaTransactionsConverter())
				.addConverterFactory(new MenigaTransactionUpdateConverter())
				.addConverterFactory(new MenigaFeedConverter(new FeedItemFactory()))
				.addConverterFactory(new MenigaCategoryConverter())
				.addConverterFactory(new MenigaOfferConverter())
				.addConverterFactory(new MenigaRedemptionConverter())
				.addConverterFactory(new MenigaReimbursementAccountConverter())
				.addConverterFactory(new MenigaIdNameKeyValConverter())
				.addConverterFactory(new MenigaChallengesConverter(new ChallengeItemFactory()))
				.addConverterFactory(new MenigaFeedItemConverter(new FeedItemFactory()))
				.addConverterFactory(new MenigaBaseConverter())
				.addConverterFactory(GsonConverterFactory.create(GsonProvider.getGsonBuilder()))
				.client(client)
				.build();
	}
}
