package com.meniga.sdk.models.user;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaSyncStatusTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaSyncStatus items = this.gson();

		assertThat(items).isNotNull();
		assertThat(items.getHasCompletedSyncSession()).isTrue();
	}

	@Test
	public void testCompare() {
		MenigaSyncStatus item1 = this.gson();
		MenigaSyncStatus item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() throws IOException {
		MenigaSyncStatus syncResponse = this.gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		syncResponse.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaSyncStatus createdFromParcel = MenigaSyncStatus.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(syncResponse.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaSyncStatus gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getJsonFileFromRaw("syncstatus.json")),
					MenigaSyncStatus.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

