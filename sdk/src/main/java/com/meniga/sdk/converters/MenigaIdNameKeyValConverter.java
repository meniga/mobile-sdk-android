package com.meniga.sdk.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.KeyVal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaIdNameKeyValConverter extends MenigaConverter {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeListOfNetWorthType = new TypeToken<List<KeyVal<Long, String>>>() {
		}.getType();

		if (typeListOfNetWorthType.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody value) throws IOException {
					String body = MenigaIdNameKeyValConverter.this.convertStreamToString((value.byteStream()));

					List<KeyVal<Long, String>> list = new ArrayList<>();
					JsonArray arr = getAsArray(body);
					for (int i = 0; i < arr.size(); i++) {
						JsonObject entry = (JsonObject) arr.get(i);
						KeyVal<Long, String> item = new KeyVal<>(entry.get("id").getAsLong(), entry.get("name").getAsString());
						list.add(item);
					}

					return list;
				}
			};
		}

		return null;
	}

}
