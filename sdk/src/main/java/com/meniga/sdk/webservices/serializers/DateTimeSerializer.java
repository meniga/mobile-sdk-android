package com.meniga.sdk.webservices.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
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
		String str = json.getAsString().substring(0, 19);
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

		return format.parseDateTime(str);
	}

	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		DateTime dateTime = new LocalDateTime(src.getMillis()).toDateTime(DateTimeZone.UTC);
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return new JsonPrimitive(fmt.print(dateTime));
	}
}
