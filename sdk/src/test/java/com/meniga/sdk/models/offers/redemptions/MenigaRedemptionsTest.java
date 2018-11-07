package com.meniga.sdk.models.offers.redemptions;

import android.os.Parcel;

import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaRedemptionsTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaRedemptions redemptions = GsonProvider.getGson().fromJson(MenigaConverter.getAsArray(
				FileImporter.getInputStreamFromRaw("redemptions.json")),
				MenigaRedemptions.class
		);
		Parcel parcel = Parcel.obtain();
		redemptions.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaRedemptions createdFromParcel = MenigaRedemptions.CREATOR.createFromParcel(parcel);
		assertThat(createdFromParcel).isEqualTo(redemptions);
	}
}
