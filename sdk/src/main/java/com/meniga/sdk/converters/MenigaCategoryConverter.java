package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCategoryConverter extends MenigaConverter {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfMenigaCategoryList = new TypeToken<List<MenigaCategory>>() {
		}.getType();
		Type typeOfMenigaCategory = new TypeToken<MenigaCategory>() {
		}.getType();
		Type typeOfMenigaUserCategoryList = new TypeToken<List<MenigaUserCategory>>() {
		}.getType();
		Type typeOfMenigaUserCategory = new TypeToken<MenigaUserCategory>() {
		}.getType();

		if (typeOfMenigaCategoryList.equals(type) || typeOfMenigaUserCategoryList.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public List<MenigaCategory> convert(ResponseBody resBody) throws IOException {
					Gson gson = GsonProvider.getGsonBuilder();
					MenigaCategory[] catsRaw = gson.fromJson(getAsArray(resBody.byteStream()), MenigaCategory[].class);

					List<MenigaCategory> cats = new ArrayList<>();
					for (MenigaCategory cat : catsRaw) {
						if (cat.getIsPublic()) {
							cats.add(cat);
						} else {
							cats.add(new MenigaUserCategory(cat));
						}

						for (int i = 0; i < cat.getChildren().size(); i++) {
							MenigaCategory child = cat.getChildren().get(i);
							if (!child.getIsPublic()) {
								cat.getChildren().set(i, new MenigaUserCategory(child));
							}
						}
					}

					return cats;
				}
			};
		} else if (typeOfMenigaCategory.equals(type) || typeOfMenigaUserCategory.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public MenigaCategory convert(ResponseBody resBody) throws IOException {

					Gson gson = GsonProvider.getGsonBuilder();

					MenigaCategory catRaw = gson.fromJson(getAsObject(resBody.byteStream()), MenigaCategory.class);
					if (catRaw.getIsPublic()) {
						return catRaw;
					} else {
						return new MenigaUserCategory(catRaw);
					}
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
