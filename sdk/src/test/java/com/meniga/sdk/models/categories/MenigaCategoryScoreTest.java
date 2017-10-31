package com.meniga.sdk.models.categories;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaCategoryScoreTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaCategoryScore items = this.gson();
		assertThat(items).isNotNull();

		assertThat(items.getCategoryId()).isEqualTo(45);
		assertThat(items.getScore()).isEqualTo(0.5);
	}

	@Test
	public void testCompare() {
		MenigaCategoryScore item1 = this.gson();
		MenigaCategoryScore item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() throws JSONException {
		MenigaCategoryScore categoryScore = this.gson();
		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		categoryScore.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaCategoryScore createdFromParcel = MenigaCategoryScore.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(categoryScore.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaCategoryScore gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		MenigaCategoryScore categoryScore = null;
		try {
			categoryScore = gson.fromJson(FileImporter.getJsonFileFromRaw("categoryscore.json"), MenigaCategoryScore.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return categoryScore;
	}
}
