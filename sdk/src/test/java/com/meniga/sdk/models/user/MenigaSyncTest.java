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
public class MenigaSyncTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaSync items = this.gson();

		assertThat(items).isNotNull();
		assertThat(items.getSynchronizationStatus().getSyncHistoryId()).isEqualTo(150L);
		assertThat(items.getSynchronizationStatus().getSyncSessionStartTime()).isNotNull();
		assertThat(items.getSynchronizationStatus().getIsSyncDone()).isTrue();
		assertThat(items.getSynchronizationStatus().getRealmSyncResponses().size()).isGreaterThan(0);
		assertThat(items.getSynchronizationStatus().getRealmSyncResponses().get(0).accountSyncStatuses.size()).isGreaterThan(0);
		assertThat(items.getSynchronizationStatus().getRealmSyncResponses().get(0).accountSyncStatuses.get(0).getTransactionsProcessed()).isEqualTo(230);
	}

	@Test
	public void testCompare() {
		MenigaSync item1 = this.gson();
		MenigaSync item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() throws IOException {
		MenigaSync syncResponse = this.gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		syncResponse.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaSync createdFromParcel = MenigaSync.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(syncResponse.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaSync gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getJsonFileFromRaw("syncresponse.json")),
					MenigaSync.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
