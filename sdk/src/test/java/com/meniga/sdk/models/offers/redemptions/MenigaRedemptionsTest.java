package com.meniga.sdk.models.offers.redemptions;

import android.os.Parcel;

import com.meniga.sdk.models.MockClient;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.webservices.requests.GetRedemptions;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import retrofit2.Call;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaRedemptionsTest {

	private MockInterceptor interceptor = new MockInterceptor("redemptions.json");

	@Test
	public void testSerialization() throws IOException {
		GetRedemptions req = new GetRedemptions();
		Call<MenigaRedemptions> call =  MockClient.getApi(interceptor).getRedemptions(req.toQueryMap());
		MenigaRedemptions redemptions = call.execute().body();
		Parcel parcel = Parcel.obtain();
		redemptions.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaRedemptions createdFromParcel = MenigaRedemptions.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(redemptions.equals(createdFromParcel));
	}
}
