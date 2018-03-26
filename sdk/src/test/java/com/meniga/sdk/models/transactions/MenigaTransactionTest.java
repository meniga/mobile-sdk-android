/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.transactions;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MenigaTransactionTest {

	@Test
	public void testCompare() {
		List<MenigaTransaction> items1 = this.gson();
		List<MenigaTransaction> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setAmount(new MenigaDecimal(937403));

		assertThat(items1.get(0)).isNotEqualTo(items2.get(0));
	}

	@Test
	public void testClone() {
		List<MenigaTransaction> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaTransaction test = items.get(0);

		try {
			MenigaTransaction test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testParcel() {
		MenigaTransaction transactionToTest = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		transactionToTest.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaTransaction createdFromParcel = MenigaTransaction.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(transactionToTest.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaTransaction> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		List<MenigaTransaction> transactions = null;
		try {
			transactions = Arrays.asList(
					gson.fromJson(MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("transactions.json")), MenigaTransaction[].class)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactions;
	}
}
