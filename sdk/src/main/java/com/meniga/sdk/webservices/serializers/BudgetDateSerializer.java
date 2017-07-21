package com.meniga.sdk.webservices.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.helpers.BudgetDate;

import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BudgetDateSerializer implements JsonDeserializer<BudgetDate>, JsonSerializer<BudgetDate> {
	@Override
	public BudgetDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		int year = obj.get("YEAR").getAsInt();
		int month = obj.get("MonthOfYear").getAsInt();
		LocalDate localDate = new LocalDate()
				.withYear(year)
				.withMonthOfYear(month);

		return new BudgetDate(localDate);
	}

	@Override
	public JsonElement serialize(BudgetDate src, Type typeOfSrc, JsonSerializationContext context) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("YEAR", src.getLocalDate().getYear());
			jsonObject.put("MonthOfYear", src.getLocalDate().getMonthOfYear());
		} catch (JSONException e) {
			ErrorHandler.reportAndHandle(e);
		}
		return new JsonPrimitive(jsonObject.toString());
	}
}
