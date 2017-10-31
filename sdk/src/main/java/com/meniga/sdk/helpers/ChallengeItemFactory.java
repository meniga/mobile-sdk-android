package com.meniga.sdk.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.models.challenges.MenigaChallenge;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ChallengeItemFactory {

	private Gson gson;

	public MenigaChallenge getMenigaChallengeItem(JsonObject element) {
		if (gson == null) {
			gson = GsonProvider.getGsonBuilder();
		}

		MenigaChallenge topLevel = gson.fromJson(element, MenigaChallenge.class);
		if (element.has("typeData")) {
			JsonObject typeData = element.getAsJsonObject("typeData");
			MenigaChallenge meta = gson.fromJson(typeData, MenigaChallenge.class);
			topLevel.merge(meta);

			if (typeData.has("metaData")) {
				JsonElement metaStr = typeData.get("metaData");
				if (metaStr != null && metaStr.isJsonPrimitive() && metaStr.getAsString().length() > 0) {
					JsonObject metaData = new JsonParser().parse(metaStr.getAsString()).getAsJsonObject();
					MenigaChallenge subMeta = gson.fromJson(metaData, MenigaChallenge.class);
					topLevel.merge(subMeta);
				}
			}
		}

		return topLevel;
	}
}
