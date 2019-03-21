package com.meniga.sdk.models.offers;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaOfferEvent;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfoUK;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 21.3.2019.
 */
@RunWith(RobolectricTestRunner.class)
public class MenigaOfferAccountInfoUKTest {
	@Test
	public void testParcel() {
		MenigaOfferAccountInfoUK item = gson();

		Parcel parcel = Parcel.obtain();
		Assert.assertNotNull(item);
		item.writeToParcel(parcel, 0);

		parcel.setDataPosition(0);

		MenigaOfferAccountInfoUK createdFromParcel = MenigaOfferAccountInfoUK.CREATOR.createFromParcel(parcel);
		Assert.assertNotNull(item);
		Assert.assertEquals(item, createdFromParcel);

		parcel.recycle();
	}

	private MenigaOfferAccountInfoUK gson() {
		Gson gson = GsonProvider.getGson();
		try {
			return gson.fromJson(MenigaConverter.getAsObject(
					FileImporter.getInputStreamFromRaw("offer_account_info_uk.json")),
					MenigaOfferAccountInfoUK.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
