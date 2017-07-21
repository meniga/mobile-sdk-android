package com.meniga.sdk.models.challenges.enums;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum CustomChallengeColor {
	@SerializedName("secondary")
	NAVY,
	@SerializedName("success")
	GREEN,
	@SerializedName("warning")
	YELLOW,
	@SerializedName("danger")
	RED;

	public int toColorInt() {
		switch (this) {
			default:
			case NAVY:
				return Color.parseColor("#465861");
			case GREEN:
				return Color.parseColor("#81a773");
			case YELLOW:
				return Color.parseColor("#fec760");
			case RED:
				return Color.parseColor("#ea6555");
		}
	}

	public String toColorString() {
		switch (this) {
			default:
			case NAVY:
				return "secondary";
			case GREEN:
				return "success";
			case YELLOW:
				return "warning";
			case RED:
				return "danger";
		}
	}

	public static CustomChallengeColor getRandom() {
		Random rnd = new Random(System.currentTimeMillis());
		int len = CustomChallengeColor.values().length;
		int random = rnd.nextInt(len);
		while (CustomChallengeColor.values()[random] == NAVY) {
			random = rnd.nextInt(len);
		}
		return CustomChallengeColor.values()[random];
	}
}
