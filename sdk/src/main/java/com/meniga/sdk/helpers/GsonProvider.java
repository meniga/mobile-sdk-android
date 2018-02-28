package com.meniga.sdk.helpers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.categories.MenigaCategoryScore;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.webservices.serializers.BudgetDateSerializer;
import com.meniga.sdk.webservices.serializers.DateTimeSerializer;
import com.meniga.sdk.webservices.serializers.LocalDateSerializer;
import com.meniga.sdk.webservices.serializers.MenigaCategoryScoreSerializer;
import com.meniga.sdk.webservices.serializers.MenigaDecimalSerializer;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GsonProvider {

	private static Gson gson;

	public static Gson getGsonBuilder() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setFieldNamingStrategy(FieldNamingPolicy.IDENTITY);

			// Register custom de/serializers.
			gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
			gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
			gsonBuilder.registerTypeAdapter(MenigaDecimal.class, new MenigaDecimalSerializer());
			gsonBuilder.registerTypeAdapter(BudgetDate.class, new BudgetDateSerializer());
			gsonBuilder.registerTypeAdapter(MenigaCategoryScore.class, new MenigaCategoryScoreSerializer());

			gson = gsonBuilder.create();
		}
		return gson;
	}
}
