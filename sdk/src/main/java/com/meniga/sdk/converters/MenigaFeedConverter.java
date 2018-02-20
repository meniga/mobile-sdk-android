package com.meniga.sdk.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.FeedItemFactory;
import com.meniga.sdk.models.feed.MenigaFeed;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaFeedConverter extends MenigaConverter {
	private FeedItemFactory feedItemFactory;

	public MenigaFeedConverter(FeedItemFactory feedItemFactory) {
		this.feedItemFactory = feedItemFactory;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfFeed = new TypeToken<MenigaFeed>() {}.getType();

		if (typeOfFeed.equals(type)) {
			return new Converter<ResponseBody, MenigaFeed>() {
				@Override
				public MenigaFeed convert(ResponseBody resBody) throws IOException {
					MenigaFeed feed = new MenigaFeed();

					JsonArray arr = getAsArray(resBody.byteStream());
					for (JsonElement element : arr) {
						feed.add(feedItemFactory.getMenigaFeetItem((JsonObject) element));
					}

					try {
						InputStreamReader isr = new InputStreamReader(resBody.byteStream());
						JsonElement jelement = new JsonParser().parse(isr);
						JsonObject jobject = jelement.getAsJsonObject();
						if (jobject.has("meta")) {
							JsonObject meta = jobject.getAsJsonObject("meta");
							feed.setHasMoreData(meta.get("hasMoreData").getAsBoolean());
						} else {
							feed.setHasMoreData(true);
						}
					} catch (Exception ex) {
						feed.setHasMoreData(true);
					}

					return feed;
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
