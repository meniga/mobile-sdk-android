package com.meniga.sdk.models.offers;

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

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaOfferTest{

	@Test
	public void testParcel() {
		MenigaOfferPage item = gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaOfferPage createdFromParcel = MenigaOfferPage.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	private MenigaOfferPage gson() {
		Gson gson = GsonProvider.getGson();
		try {
			return gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getInputStreamFromRaw("offers.json")),
					MenigaOfferPage.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
