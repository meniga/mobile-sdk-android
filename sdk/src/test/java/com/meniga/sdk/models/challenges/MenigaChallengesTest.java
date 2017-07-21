package com.meniga.sdk.models.challenges;

import android.os.Parcel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.ChallengeItemFactory;

import static org.assertj.core.api.Assertions.assertThat;

import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaChallengesTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaChallenge test = gson().get(0);
		assertThat(test).isNotNull();

		assertThat(test.getDescription()).isNotNull();
		assertThat(test.getDescription()).isEqualTo("You can't handle the truth");
		assertThat(test.getCustomChallengeColor()).isEqualTo(CustomChallengeColor.YELLOW);
		assertThat(test.getCategoryIds()).isNotNull();
	}

	@Test
	public void testMerge() {
		MenigaChallenge test = gson().get(1);
		MenigaChallenge meta = gson("challengemetadata.json").get(0);

		test.merge(meta);

		assertThat(test.getCategoryIds()).isNotNull();
		assertThat(test.getCategoryIds().get(0)).isEqualTo(71);
		assertThat(test.getCustomChallengeColor()).isEqualTo(CustomChallengeColor.NAVY);
	}

	@Test
	public void testCompare() {
		MenigaChallenge items1 = gson().get(0);
		MenigaChallenge items2 = gson().get(0);

		assertThat(items1 == items2).isFalse();

		assertThat(items1.equals(items2)).isTrue();
	}

	@Test
	public void testParcel() {
		MenigaChallenge item = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaChallenge createdFromParcel = MenigaChallenge.CREATOR.createFromParcel(parcel);
		assertThat(item.equals(createdFromParcel)).isTrue();

		parcel.recycle();
	}

	private List<MenigaChallenge> gson() {
		return gson("challenges.json");
	}

	private List<MenigaChallenge> gson(String file) {
		ChallengeItemFactory factory = new ChallengeItemFactory();
		List<MenigaChallenge> items = new ArrayList<>();
		try {
			JsonArray arr = MenigaConverter.getAsArray(FileImporter.getJsonFileFromRaw(file));
			for (int i = 0; i < arr.size(); i++) {
				JsonObject item = arr.get(i).getAsJsonObject();
				items.add(factory.getMenigaChallengeItem(item));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return items;
	}
}
