package com.meniga.sdk.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.ChallengeItemFactory;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.challenges.enums.ChallengeType;

import java.io.IOException;
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
public class MenigaChallengesConverter extends MenigaConverter {

	private final ChallengeItemFactory factory;

	public MenigaChallengesConverter(ChallengeItemFactory factory) {
		this.factory = factory;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		Type typeOfChallenge = new TypeToken<MenigaChallenge>() {
		}.getType();
		Type typeOfChallenges = new TypeToken<List<MenigaChallenge>>() {
		}.getType();

		if (typeOfChallenge.equals(type)) {
			return new Converter<ResponseBody, MenigaChallenge>() {
				@Override
				public MenigaChallenge convert(ResponseBody resBody) throws IOException {
					String body = convertStreamToString((resBody.byteStream()));

					return factory.getMenigaChallengeItem(getAsObject(body));
				}
			};
		} else if (typeOfChallenges.equals(type)) {
			return new Converter<ResponseBody, Object>() {
				@Override
				public Object convert(ResponseBody resBody) throws IOException {
					String body = convertStreamToString((resBody.byteStream()));

					JsonArray arr = getAsArray(body);
					List<MenigaChallenge> challenges = new ArrayList<>();
					for (JsonElement element : arr) {
						MenigaChallenge challenge = factory.getMenigaChallengeItem((JsonObject) element);
						if (challenge.getTitle().equals("GlobalSpendingMeterMarker")) {
							challenge.setType(ChallengeType.METER);
						}
						challenges.add(challenge);
					}

					return challenges;
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
