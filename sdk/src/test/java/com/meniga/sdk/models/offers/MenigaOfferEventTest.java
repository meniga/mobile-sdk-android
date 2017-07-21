package com.meniga.sdk.models.offers;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaOfferEvent;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaOfferEventTest {

	@Test
	public void testParcel() {
		MenigaOfferEvent item = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaOfferEvent createdFromParcel = MenigaOfferEvent.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	@Test
	public void testEquals() {
		List<MenigaOfferEvent> list = gson();
		List<MenigaOfferEvent> list2 = gson();

		Assert.assertTrue(list != null && list2 != null && list.size() == 2 && list2.size() == 2);
		Assert.assertFalse(list.get(0).equals(list.get(1)));
		Assert.assertTrue(list.get(0).equals(list2.get(0)));
	}

	private List<MenigaOfferEvent> gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			MenigaOfferEvent[] events = gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getJsonFileFromRaw("offerevents.json")),
					MenigaOfferEvent[].class
			);
			List<MenigaOfferEvent> eventsList = new ArrayList<>();
			Collections.addAll(eventsList, events);
			return eventsList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
