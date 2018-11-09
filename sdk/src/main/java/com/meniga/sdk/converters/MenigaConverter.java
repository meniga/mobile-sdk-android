package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.annotations.MetaProperty;

import org.jetbrains.annotations.NonNls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import retrofit2.Converter;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public abstract class MenigaConverter extends Converter.Factory {
	protected String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			sb.append("\n");
		} catch (IOException ex) {
			ErrorHandler.reportAndHandle(ex);
			throw ex;
		} finally {
			try {
				is.close();
			} catch (IOException ex) {
				ErrorHandler.reportAndHandle(ex);
			}
		}

		return sb.toString();
	}

	public static JsonArray getAsArray(InputStream stream) throws IOException {
		JsonObject jsonObject = readJsonObject(stream);
		return jsonObject.getAsJsonArray("data");
	}

	public static JsonArray getAsArray(JsonElement element) {
		JsonObject jobject = element.getAsJsonObject();
		return jobject.getAsJsonArray("data");
	}

	public static JsonObject getAsObject(InputStream stream) throws IOException {
		InputStreamReader isr = new InputStreamReader(stream);
		JsonElement jelement;
		try {
			jelement = new JsonParser().parse(isr);
		} finally {
			isr.close();
		}
		JsonObject jobject = jelement.getAsJsonObject();
		return jobject.getAsJsonObject("data");
	}

	public static MenigaApiResponse.Object getAsObjectApiResponse(InputStream stream) throws IOException {
		JsonObject jsonObject = readJsonObject(stream);
		return new MenigaApiResponse.Object(
				jsonObject.getAsJsonObject("data"),
				jsonObject.getAsJsonObject("meta"),
				jsonObject.getAsJsonObject("included"));
	}

	public static MenigaApiResponse.Array getAsArrayApiResponse(InputStream stream) throws IOException {
		JsonObject jsonObject = readJsonObject(stream);
		return new MenigaApiResponse.Array(
				jsonObject.getAsJsonArray("data"),
				jsonObject.getAsJsonObject("meta"),
				jsonObject.getAsJsonObject("included"));
	}

	private static JsonObject readJsonObject(InputStream stream) {
		InputStreamReader reader = new InputStreamReader(stream);
		JsonElement jsonElement = new JsonParser().parse(reader);
		return jsonElement.getAsJsonObject();
	}

	@SuppressWarnings("unchecked")
	public static <T> T mergeMeta(@Nonnull Gson gson, @Nonnull T dest, @Nonnull JsonObject meta) throws JsonSyntaxException {
		Class<?> cl = dest.getClass();
		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(MetaProperty.class)) {
				String name = field.getName();
				MetaProperty metaProperty = field.getAnnotation(MetaProperty.class);
				String jsonProperty = (metaProperty.fromProperty().length() > 0) ? metaProperty.fromProperty() : name;
				if (meta.has(jsonProperty)) {
					boolean isAccessible = field.isAccessible();
					if (!isAccessible) {
						field.setAccessible(true);
					}
					try {
						Object data = gson.fromJson(meta.get(jsonProperty), field.getType());
						field.set(dest, data);
					} catch (IllegalAccessException e) {
						throw new JsonSyntaxException("Failed to merge response json meta property \"" + jsonProperty + "\" into: " + dest.getClass().getCanonicalName() + " and it's field " + name, e);
					}
					if (!isAccessible) {
						field.setAccessible(false);
					}
				}
			}
		}
		return dest;
	}

	public static <T> String metaAsString(T target) {
		String s = "[";
		for (Field f : target.getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(MetaProperty.class)) {
				try {
					f.setAccessible(true);
					s += f.getName() + "=" + f.get(target) + ",";
					f.setAccessible(false);
				} catch (IllegalAccessException e) {
					s += f.getName() + "= inaccessible,";
				}
			}
		}
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}
		return s + "]";
	}
}
