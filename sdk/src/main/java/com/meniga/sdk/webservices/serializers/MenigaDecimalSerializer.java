package com.meniga.sdk.webservices.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.meniga.sdk.helpers.MenigaDecimal;

import java.lang.reflect.Type;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDecimalSerializer implements JsonDeserializer<MenigaDecimal>, JsonSerializer<MenigaDecimal> {

	@Override
	public MenigaDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return new MenigaDecimal(json.getAsString());
	}

	@Override
	public JsonElement serialize(MenigaDecimal src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getBigDecimal().doubleValue());
	}
}
