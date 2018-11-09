package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountPage;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountType;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountTypePage;

import java.io.IOException;
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
public class MenigaReimbursementAccountConverter extends MenigaConverter {
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfAccount = new TypeToken<MenigaReimbursementAccount>() {}.getType();
		Type typeOfAccounts = new TypeToken<MenigaReimbursementAccountPage>() {}.getType();
		Type typeOfAccountTypes = new TypeToken<MenigaReimbursementAccountTypePage>() {}.getType();

		if (typeOfAccount.equals(type)) {
			return new Converter<ResponseBody, MenigaReimbursementAccount>() {
				@Override
				public MenigaReimbursementAccount convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGson();
					return gson.fromJson(getAsObject(resBody.byteStream()), MenigaReimbursementAccount.class);
				}
			};
		} else if (typeOfAccounts.equals(type)) {
			return new Converter<ResponseBody, MenigaReimbursementAccountPage>() {
				@Override
				public MenigaReimbursementAccountPage convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGson();
					MenigaReimbursementAccountPage page = new MenigaReimbursementAccountPage();
					InputStreamReader isr = new InputStreamReader(resBody.byteStream());
					JsonElement jelement;
					try {
						jelement = new JsonParser().parse(isr);
					}
					finally {
						isr.close();
					}
					JsonObject jobject = jelement.getAsJsonObject();
					JsonArray arr = jobject.getAsJsonArray("data");

					Collections.addAll(page, gson.fromJson(arr, MenigaReimbursementAccount[].class));

					JsonObject meta = jobject.getAsJsonObject("meta");

					return MenigaConverter.mergeMeta(gson, page, meta);
				}
			};
		} else if (typeOfAccountTypes.equals(type)) {
			return new Converter<ResponseBody, MenigaReimbursementAccountTypePage>() {
				@Override
				public MenigaReimbursementAccountTypePage convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGson();
					MenigaReimbursementAccountTypePage page = new MenigaReimbursementAccountTypePage();

					InputStreamReader isr = new InputStreamReader(resBody.byteStream());
					JsonElement jelement;
					try {
						jelement = new JsonParser().parse(isr);
					}
					finally {
						isr.close();
					}
					JsonObject jobject = jelement.getAsJsonObject();
					JsonArray arr = jobject.getAsJsonArray("data");
					Collections.addAll(page, gson.fromJson(arr, MenigaReimbursementAccountType[].class));

					JsonObject meta = jobject.getAsJsonObject("meta");

					return MenigaConverter.mergeMeta(gson, page, meta);
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
