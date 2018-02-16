package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.FeedItemFactory;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaFeedItem;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaFeedItemConverter extends MenigaConverter {
	private FeedItemFactory feedItemFactory;

	public MenigaFeedItemConverter(FeedItemFactory feedItemFactory) {
		this.feedItemFactory = feedItemFactory;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfMenigaFeedItem = new TypeToken<MenigaFeedItem>() {}.getType();

		if (typeOfMenigaFeedItem.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					JsonObject obj = getAsObject(resBody.byteStream());
					return feedItemFactory.getMenigaFeetItem(obj);
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
