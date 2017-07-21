package com.meniga.sdk.models.user;

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
public class MenigaSyncStatusTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaSync.MenigaSyncStatus items = this.gson();

		assertThat(items).isNotNull();
		assertThat(items.getHasCompletedSyncSession()).isTrue();
	}

	@Test
	public void testCompare() {
		MenigaSync.MenigaSyncStatus item1 = this.gson();
		MenigaSync.MenigaSyncStatus item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() throws IOException {
		MenigaSync.MenigaSyncStatus syncResponse = this.gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		syncResponse.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaSync.MenigaSyncStatus createdFromParcel = MenigaSync.MenigaSyncStatus.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(syncResponse.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaSync.MenigaSyncStatus gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getJsonFileFromRaw("syncstatus.json")),
					MenigaSync.MenigaSyncStatus.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

