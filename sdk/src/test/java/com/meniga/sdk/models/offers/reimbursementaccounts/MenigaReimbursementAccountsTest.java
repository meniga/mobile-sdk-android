package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;

import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.models.MockClient;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.webservices.requests.GetReimbursementAccountTypes;
import com.meniga.sdk.webservices.requests.GetReimbursementAccounts;

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
public class MenigaReimbursementAccountsTest{

	private MockInterceptor interceptor = new MockInterceptor("reimbursementaccounts.json");

	private MockInterceptor typesInterceptor = new MockInterceptor("reimbursementaccounttypes.json");

	@Test
	public void testSerialization() throws IOException {
		GetReimbursementAccounts req = new GetReimbursementAccounts();
		Call<MenigaReimbursementAccountPage> call =  MockClient.getApi(interceptor).getReimbursementAccounts(req.toQueryMap());
		MenigaReimbursementAccountPage accounts = call.execute().body();
		Parcel parcel = Parcel.obtain();
		accounts.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaReimbursementAccountPage createdFromParcel = MenigaReimbursementAccountPage.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(accounts.equals(createdFromParcel));
	}

	@Test
	public void testTypesSerialization() throws IOException {
		GetReimbursementAccountTypes req = new GetReimbursementAccountTypes();
		Call<MenigaReimbursementAccountTypePage> call =  MockClient.getApi(typesInterceptor).getReimbursementAccountTypes(req.toQueryMap());
		MenigaReimbursementAccountTypePage types = call.execute().body();
		Parcel parcel = Parcel.obtain();
		types.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		int n = types.getTotalCount();
		// Reconstruct object from parcel and asserts:
		MenigaReimbursementAccountTypePage createdFromParcel = MenigaReimbursementAccountTypePage.CREATOR.createFromParcel(parcel);
		int n2 = types.getTotalCount();
		Assert.assertTrue(types.equals(createdFromParcel));
	}
}
