package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;

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
public class MenigaTransactionsConverter extends MenigaConverter {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, final Retrofit retrofit) {
		Type typeOfListTransactions = new TypeToken<List<MenigaTransaction>>() {
		}.getType();
		Type typeOfMenigaTransactionPage = new TypeToken<MenigaTransactionPage>() {
		}.getType();
		Type typeOfTransaction = new TypeToken<MenigaTransaction>() {
		}.getType();

		if (typeOfListTransactions.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();

					List<MenigaTransaction> menigaTransactions = gson.fromJson(getAsArray(resBody.byteStream()), new TypeToken<List<MenigaTransaction>>() {
					}.getType());
					for (MenigaTransaction trans : menigaTransactions) {
						if (trans.getComments() == null) {
							continue;
						}
						for (MenigaComment comm : trans.getComments()) {
							comm.setTransactionId(trans.getId());
						}
					}

					return menigaTransactions;
				}
			};
		} else if (typeOfTransaction.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();

					MenigaTransaction menigaTransaction = gson.fromJson(getAsObject(resBody.byteStream()), MenigaTransaction.class);
					if (menigaTransaction.getComments() != null) {
						for (MenigaComment comm : menigaTransaction.getComments()) {
							comm.setTransactionId(menigaTransaction.getId());
						}
					}

					return menigaTransaction;
				}
			};
		} else if (typeOfMenigaTransactionPage.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();

					InputStreamReader isr = new InputStreamReader(resBody.byteStream());
					JsonElement element;
					try {
						element = new JsonParser().parse(isr);
					} finally {
						isr.close();
					}

					List<MenigaTransaction> menigaTransactions = gson.fromJson(getAsArray(element), new TypeToken<List<MenigaTransaction>>() {
					}.getType());
					MenigaTransactionPage list = new MenigaTransactionPage();

					list.addAll(menigaTransactions);
					JsonObject object = element.getAsJsonObject();
					JsonObject meta = object.getAsJsonObject("meta");
					list.setTotalNumTransactions(meta.get("totalCount").getAsInt());

					return list;
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
