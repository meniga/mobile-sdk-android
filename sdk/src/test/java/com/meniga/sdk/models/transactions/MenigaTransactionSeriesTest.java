package com.meniga.sdk.models.transactions;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
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
public class MenigaTransactionSeriesTest{

	@Test
	public void testSerialization() throws IOException {
		List<MenigaTransactionSeries> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaTransactionSeries test = items.get(0);

		assertThat(test.getStatistics()).isNotNull();
		assertThat(test.getStatistics().getCurrentMonthTotal()).isEqualTo(new MenigaDecimal(-317));
		assertThat(test.getValues().size()).isGreaterThan(0);
		assertThat(test.getValues().get(0).getNettoAmount()).isEqualTo(new MenigaDecimal(-17));
	}

	@Test
	public void testCompare() {
		List<MenigaTransactionSeries> items1 = this.gson();
		List<MenigaTransactionSeries> items2 = this.gson();
		MenigaTransactionSeries item1 = items1.get(0);
		MenigaTransactionSeries item2 = items2.get(0);

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() {
		List<MenigaTransactionSeries> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaTransactionSeries item = items.get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaTransactionSeries createdFromParcel = MenigaTransactionSeries.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaTransactionSeries> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getJsonFileFromRaw("transactionseries.json")),
					MenigaTransactionSeries[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
