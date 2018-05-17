package com.meniga.sdk.webservices.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class DateTimeSerializer implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
	@Override
	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		String dateAdString = json.getAsString();
		if (dateAdString.length() > 19) {
			dateAdString = dateAdString.substring(0, 19);
		}
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

		return format.parseDateTime(dateAdString);
	}

	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
		return new JsonPrimitive(formatter.print(src));
	}
}
