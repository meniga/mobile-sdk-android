package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.offers.MenigaOffer;
import com.meniga.sdk.models.offers.MenigaOfferPage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferConverter extends MenigaConverter {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfOffers = new TypeToken<MenigaOfferPage>() {
		}.getType();
		Type typeOfOffer = new TypeToken<MenigaOffer>() {
		}.getType();

		if (typeOfOffers.equals(type)) {
			return new Converter<ResponseBody, MenigaOfferPage>() {
				@Override
				public MenigaOfferPage convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();
					MenigaOfferPage page = gson.fromJson(new JsonArray(), MenigaOfferPage.class);
					InputStreamReader isr = new InputStreamReader(resBody.byteStream());
					JsonElement element;
					try {
						element = new JsonParser().parse(isr);
					}
					finally {
						isr.close();
					}

					JsonObject object = element.getAsJsonObject();
					JsonArray arr = object.getAsJsonArray("data");

					Collections.addAll(page, gson.fromJson(arr, MenigaOffer[].class));

					JsonObject meta = object.getAsJsonObject("meta");

					return MenigaConverter.mergeMeta(gson, page, meta);
				}
			};
		} else if (typeOfOffer.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();
					return gson.fromJson(getAsObject(resBody.byteStream()), MenigaOffer.class);
				}
			};
		}

		return null;
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		return null;
	}
}
