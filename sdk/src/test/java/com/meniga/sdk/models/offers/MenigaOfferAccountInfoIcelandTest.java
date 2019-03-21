package com.meniga.sdk.models.offers;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfoIceland;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

/**
 * Copyright 2019 Meniga Iceland Inc.
 * Created by agustk on 21.3.2019.
 */
@RunWith(RobolectricTestRunner.class)
public class MenigaOfferAccountInfoIcelandTest  {
	@Test
	public void testParcel() {
		MenigaOfferAccountInfoIceland item = gson();

		Parcel parcel = Parcel.obtain();
		org.junit.Assert.assertNotNull(item);
		item.writeToParcel(parcel, 0);

		parcel.setDataPosition(0);

		MenigaOfferAccountInfoIceland createdFromParcel = MenigaOfferAccountInfoIceland.CREATOR.createFromParcel(parcel);
		org.junit.Assert.assertNotNull(item);
		Assert.assertEquals(item, createdFromParcel);

		parcel.recycle();
	}

	@Test
	public void testToJson() {
		MenigaOfferAccountInfoIceland item = gson();

		org.junit.Assert.assertNotNull(item);
		String json = item.toJson();

		Assert.assertEquals(json, "{\"bankNumber\":\"0512\",\"ledger\":\"26\",\"bankAccountNumber\":\"123456\",\"socialSecurityNumber\":\"1111114449\"}");
	}

	private MenigaOfferAccountInfoIceland gson() {
		Gson gson = GsonProvider.getGson();
		try {
			return gson.fromJson(MenigaConverter.getAsObject(
					FileImporter.getInputStreamFromRaw("offer_account_info_iceland.json")),
					MenigaOfferAccountInfoIceland.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
