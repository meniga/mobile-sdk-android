package com.meniga.sdk.models.offers.reimbursementaccounts;

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
public class MenigaReimbursementAccountsTest{

	@Test
	public void testSerialization() throws IOException {
		MenigaReimbursementAccountPage accounts = GsonProvider.getGson().fromJson(MenigaConverter.getAsArray(
				FileImporter.getInputStreamFromRaw("reimbursementaccounts.json")),
				MenigaReimbursementAccountPage.class);
		Parcel parcel = Parcel.obtain();
		accounts.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaReimbursementAccountPage createdFromParcel = MenigaReimbursementAccountPage.CREATOR.createFromParcel(parcel);
		assertThat(createdFromParcel).isEqualTo(accounts);
	}

	@Test
	public void testTypesSerialization() throws IOException {
		MenigaReimbursementAccountTypePage types = GsonProvider.getGson().fromJson(MenigaConverter.getAsArray(
				FileImporter.getInputStreamFromRaw("reimbursementaccounttypes.json")),
				MenigaReimbursementAccountTypePage.class);
		Parcel parcel = Parcel.obtain();
		types.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaReimbursementAccountTypePage createdFromParcel = MenigaReimbursementAccountTypePage.CREATOR.createFromParcel(parcel);
		assertThat(createdFromParcel).isEqualTo(types);
	}
}
