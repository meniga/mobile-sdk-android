package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.models.offers.redemptions.MenigaScheduledReimbursement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRedemptionConverter extends MenigaConverter {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfRedemptions = new TypeToken<MenigaRedemptions>() {
		}.getType();

		if (typeOfRedemptions.equals(type)) {
			return new Converter<ResponseBody, MenigaRedemptions>() {
				@Override
				public MenigaRedemptions convert(ResponseBody resBody) throws IOException {
					InputStreamReader isr = new InputStreamReader(resBody.byteStream());
					JsonElement element;
					try {
						element = new JsonParser().parse(isr);
					} finally {
						isr.close();
					}


					Gson gson = GsonProvider.getGsonBuilder();
					MenigaRedemptions page = gson.fromJson(getAsArray(element), MenigaRedemptions.class);

					JsonObject object = element.getAsJsonObject();

					JsonObject meta = object.getAsJsonObject("meta");

					List<MenigaScheduledReimbursement> cashBackRaw = gson.fromJson(meta.getAsJsonArray("scheduledReimbursements"), new TypeToken<List<MenigaScheduledReimbursement>>() {
					}.getType());
					MenigaRedemptions main = MenigaConverter.mergeMeta(gson, page, meta);

					main.setScheduledReimbursement(cashBackRaw);
					return main;
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
