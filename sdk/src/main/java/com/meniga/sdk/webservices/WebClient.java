package com.meniga.sdk.webservices;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.meniga.sdk.MenigaSettings;
import com.meniga.sdk.converters.MenigaBaseConverter;
import com.meniga.sdk.converters.MenigaCategoryConverter;
import com.meniga.sdk.converters.MenigaChallengesConverter;
import com.meniga.sdk.converters.MenigaFeedConverter;
import com.meniga.sdk.converters.MenigaFeedItemConverter;
import com.meniga.sdk.converters.MenigaIdNameKeyValConverter;
import com.meniga.sdk.converters.MenigaOfferConverter;
import com.meniga.sdk.converters.MenigaRedemptionConverter;
import com.meniga.sdk.converters.MenigaReimbursementAccountConverter;
import com.meniga.sdk.converters.MenigaTransactionUpdateConverter;
import com.meniga.sdk.converters.MenigaTransactionsConverter;
import com.meniga.sdk.helpers.ChallengeItemFactory;
import com.meniga.sdk.helpers.FeedItemFactory;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.webservices.interceptors.AcceptLanguageInterceptor;

import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class WebClient {

	private static CookieJar cookieJar = new JavaNetCookieJar(new CookieManager());

	public static Map<Service, ?> createApiInterfaces(MenigaSettings settings) {
		// First do the default config
		Map<Service, Object> endpoints = new HashMap<>();

		// Create the default retrofit and client for all services.
		Retrofit defaultRetrofit = buildRetrofit(Service.ALL, settings);
		endpoints.put(Service.ALL, defaultRetrofit.create(MenigaAPI.class));

		Stream.of(Service.values())
				.forEach(service -> Optional.ofNullable(service.getServiceClass())
						.executeIfPresent(serviceClass -> endpoints.put(service, defaultRetrofit.create(serviceClass))));

		for (Service service : settings.getSpecialServiceEndpoints().keySet()) {
			Retrofit specialRetrofit = buildRetrofit(service, settings);
			Optional.ofNullable(service.getServiceClass())
					.executeIfPresent(objectClass -> endpoints.put(service, specialRetrofit.create(objectClass)))
					.executeIfAbsent(() -> endpoints.put(service, specialRetrofit.create(MenigaAPI.class)));
		}

		return endpoints;
	}

	private static String fixEndpoint(String endpoint) {
		return !endpoint.endsWith("/") ? endpoint + "/" : endpoint;
	}

	private static Retrofit buildRetrofit(Service service, MenigaSettings settings) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.cookieJar(WebClient.cookieJar)
				.readTimeout(settings.getTimeout(), TimeUnit.SECONDS)
				.writeTimeout(settings.getTimeout(), TimeUnit.SECONDS)
				.connectTimeout(settings.getTimeout(), TimeUnit.SECONDS);

		Optional.ofNullable(settings.getAuthenticator())
				.executeIfPresent(builder::authenticator);

		Optional.ofNullable(settings.getSslSocketFactory())
				.executeIfPresent(sslSocketFactory -> builder.sslSocketFactory(sslSocketFactory, settings.getX509TrustManager()));

		Stream.ofNullable(settings.getHttpInterceptors())
				.forEach(builder::addInterceptor);

		Stream.ofNullable(settings.getNetworkInterceptors())
				.forEach(builder::addNetworkInterceptor);

		builder.addInterceptor(new AcceptLanguageInterceptor());

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
