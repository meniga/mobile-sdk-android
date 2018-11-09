package com.meniga.sdk.models;

import com.meniga.sdk.converters.MenigaBaseConverter;
import com.meniga.sdk.converters.MenigaCategoryConverter;
import com.meniga.sdk.converters.MenigaChallengesConverter;
import com.meniga.sdk.converters.MenigaFeedConverter;
import com.meniga.sdk.converters.MenigaFeedItemConverter;
import com.meniga.sdk.converters.MenigaIdNameKeyValConverter;
import com.meniga.sdk.converters.MenigaOfferConverter;
import com.meniga.sdk.converters.MenigaRedemptionConverter;
import com.meniga.sdk.converters.MenigaReimbursementAccountConverter;
import com.meniga.sdk.converters.MenigaTransactionsConverter;
import com.meniga.sdk.helpers.ChallengeItemFactory;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MockFeedItemFactory;
import com.meniga.sdk.webservices.MenigaAPI;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MockClient {

	public static MenigaAPI getApi(Interceptor myInterceptor) {
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(myInterceptor)
				.build();
		final Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(new MenigaTransactionsConverter())
				.addConverterFactory(new MenigaFeedConverter(new MockFeedItemFactory()))
				.addConverterFactory(new MenigaCategoryConverter())
				.addConverterFactory(new MenigaOfferConverter())
				.addConverterFactory(new MenigaRedemptionConverter(GsonProvider.getGson()))
				.addConverterFactory(new MenigaReimbursementAccountConverter())
				.addConverterFactory(new MenigaIdNameKeyValConverter())
				.addConverterFactory(new MenigaChallengesConverter(new ChallengeItemFactory()))
				.addConverterFactory(new MenigaFeedItemConverter(new MockFeedItemFactory()))
				.addConverterFactory(new MenigaBaseConverter())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl("http://meniga.is/api/")
				.client(client)
				.build();

		return retrofit.create(MenigaAPI.class);
	}
}
