package com.meniga.sdk.models.serverpublic;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaPublicSettingsTest{

	@Test
	public void testSerialization() throws IOException {
		MenigaPublicSettings test = gson();
		assertThat(test).isNotNull();

		assertThat(test.getCurrencies()).isNotNull();
		assertThat(test.getCurrencies().size()).isEqualTo(10);
		assertThat(test.getCurrencyFormat()).isEqualTo("£ {0}");
		assertThat(test.getClusterNodeName()).isEqualTo(null);
	}

	@Test
	public void testCompare() {
		MenigaPublicSettings items1 = gson();
		MenigaPublicSettings items2 = gson();

		assertThat(items1 == items2).isFalse();

		assertThat(items1.equals(items2)).isTrue();
	}

	@Test
	public void testParcel() {
		MenigaPublicSettings item = gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaPublicSettings createdFromParcel = MenigaPublicSettings.CREATOR.createFromParcel(parcel);
		assertThat(item.equals(createdFromParcel)).isTrue();

		parcel.recycle();
	}

	private MenigaPublicSettings gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		MenigaPublicSettings item = null;
		try {
			item = gson.fromJson(FileImporter.getJsonFileFromRaw("publicsettings.json"), MenigaPublicSettings.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return item;
	}
}
