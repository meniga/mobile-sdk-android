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
public class MenigaReimbursementAccountsTest {
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
	public void testGetAccountInfoIceland() throws IOException {
		MenigaReimbursementAccountPage accounts = GsonProvider.getGson().fromJson(MenigaConverter.getAsArray(
				FileImporter.getInputStreamFromRaw("reimbursementaccounts.json")),
				MenigaReimbursementAccountPage.class);

		MenigaOfferAccountInfoIceland accountInfo = accounts.get(0).getAccountInfo(MenigaOfferAccountInfoIceland.class);
		assertThat(accountInfo).isNotNull();
		assertThat(accountInfo.getBankNumber()).isEqualTo("1234");
		assertThat(accountInfo.getLedger()).isEqualTo("12");
		assertThat(accountInfo.getBankAccountNumber()).isEqualTo("123456");
		assertThat(accountInfo.getSocialSecurityNumber()).isEqualTo("1234567890");
	}

	@Test
	public void testGetAccountInfoUK() throws IOException {
		MenigaReimbursementAccountPage accounts = GsonProvider.getGson().fromJson(MenigaConverter.getAsArray(
				FileImporter.getInputStreamFromRaw("reimbursementaccounts.json")),
				MenigaReimbursementAccountPage.class);

		MenigaOfferAccountInfoUK accountInfo = accounts.get(1).getAccountInfo(MenigaOfferAccountInfoUK.class);
		assertThat(accountInfo).isNotNull();
		assertThat(accountInfo.getBankName()).isEqualTo("NatWest");
		assertThat(accountInfo.getAccountName()).isEqualTo("Karlsson");
		assertThat(accountInfo.getSortcode()).isEqualTo("123456");
		assertThat(accountInfo.getBankAccountNumber()).isEqualTo("12345678");
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
