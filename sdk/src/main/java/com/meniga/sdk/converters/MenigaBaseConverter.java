package com.meniga.sdk.converters;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaBaseConverter<T> extends MenigaConverter {
	private Type typeOfObject = new TypeToken<Object>() {}.getType();

	@Override
	public Converter<ResponseBody, T> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
		return new Converter<ResponseBody, T>() {
			@Override
			public T convert(ResponseBody resBody) throws IOException {
				Gson gson = GsonProvider.getGsonBuilder();
				InputStreamReader isr = new InputStreamReader(resBody.byteStream());
				JsonParser parser = new JsonParser();
				JsonObject jsonObject = parser.parse(isr).getAsJsonObject();
				JsonElement jsonElement;

				Log.e("Derp", "type " + type.equals(typeOfObject));
				if (jsonObject.has("data") && !type.equals(typeOfObject)) {
					jsonElement = jsonObject.get("data");
				} else {
					jsonElement = jsonObject;
				}

				return gson.fromJson(jsonElement, type);
			}
		};
	}
}
