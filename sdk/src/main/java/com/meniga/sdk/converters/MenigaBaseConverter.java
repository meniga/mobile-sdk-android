package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.helpers.GsonProvider;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaBaseConverter<T> extends MenigaConverter {

	@Override
	public Converter<ResponseBody, T> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
		return new Converter<ResponseBody, T>() {
			@Override
			public T convert(ResponseBody resBody) throws IOException {
				String body = MenigaBaseConverter.this.convertStreamToString((resBody.byteStream()));
				Gson gson = GsonProvider.getGsonBuilder().create();
				JsonParser parser = new JsonParser();
				JsonObject jsonObject = parser.parse(body).getAsJsonObject();
				JsonElement jsonElement = null;
				if (jsonObject.has("data"))
				{
					jsonElement = jsonObject.get("data");
				}

				return gson.fromJson(jsonElement, type);
			}
		};
	}
}
