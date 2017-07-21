package com.meniga.sdk.models.networth;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaNetWorthAccountTypeTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaNetWorthAccountType items = this.gson();
		assertThat(items).isNotNull();

		assertThat(items.getName()).isNotNull();
		assertThat(items.getId()).isEqualTo(21);
		assertThat(items.getParentId()).isEqualTo(null);
	}

	@Test
	public void testCompare() {
		MenigaNetWorthAccountType item1 = this.gson();
		MenigaNetWorthAccountType item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() {
		MenigaNetWorthAccountType item = this.gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaNetWorthAccountType createdFromParcel = MenigaNetWorthAccountType.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaNetWorthAccountType gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(MenigaConverter.getAsObject(
					FileImporter.getJsonFileFromRaw("networthaccounttype.json")),
					MenigaNetWorthAccountType.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
