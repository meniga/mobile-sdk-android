package com.meniga.sdk.models.accounts;


import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.accounts.enums.AccountCategory;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@RunWith(RobolectricTestRunner.class)
public class MenigaAccountTest {

	@Test
	public void testSerialization() throws IOException {
		List<MenigaAccount> account = this.gson();
		assertThat(account).isNotNull();
		assertThat(account.size() > 0).isTrue();

		MenigaAccount test = account.get(0);

		assertThat(test.getBalance().getBigDecimal()).isEqualTo(new MenigaDecimal("233").getBigDecimal());
		assertThat(test.getId()).isEqualTo(2528);
		assertThat(test.getName()).isEqualTo("Account1460734704700");
		assertThat(test.getAccountCategory()).isEqualTo(AccountCategory.WALLET);
	}

	@Test
	public void testCompare() {
		List<MenigaAccount> items1 = this.gson();
		List<MenigaAccount> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setIsHidden(true);

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testClone() {
		List<MenigaAccount> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaAccount test = items.get(0);

		try {
			MenigaAccount test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testRevert() throws IOException {
		List<MenigaAccount> account = this.gson();
		assertThat(account).isNotNull();
		assertThat(account.size() > 0).isTrue();
		MenigaAccount test = account.get(0);

		String oldName = test.getName();
		int oldOrderId = test.getOrderId();
		MenigaDecimal oldLimit = test.getEmergencyFundBalanceLimit();
		boolean oldHidden = test.getIsHidden();
		test.setName("NewName");
		test.setOrderId(oldOrderId + 1);
		test.setEmergencyFundBalanceLimit(oldLimit.add(new MenigaDecimal(10.0)));
		test.setIsHidden(!oldHidden);

		test.revert();

		assertThat(test.getName()).isEqualTo(oldName);
		assertThat(test.getOrderId()).isEqualTo(oldOrderId);
		assertThat(test.getEmergencyFundBalanceLimit()).isEqualTo(oldLimit);
		assertThat(test.getIsHidden()).isEqualTo(oldHidden);
	}

	@Test
	public void testParcel() {
		List<MenigaAccount> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaAccount account = items.get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		account.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaAccount createdFromParcel = MenigaAccount.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(account.equals(createdFromParcel));

		parcel.recycle();
	}

	public List<MenigaAccount> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(
					gson.fromJson(
							MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("accounts.json")),
							MenigaAccount[].class
					)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
