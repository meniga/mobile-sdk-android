package com.meniga.sdk.webservices.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.meniga.sdk.models.categories.MenigaCategoryScore;

import java.lang.reflect.Type;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCategoryScoreSerializer implements JsonDeserializer<MenigaCategoryScore> {

	@Override
	public MenigaCategoryScore deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Gson gson = new Gson();
		return gson.fromJson(json, MenigaCategoryScore.class);
	}
}
