package com.meniga.sdk.models.networth;

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

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaNetWorthBalanceTest {

	@Test
	public void testSerialization() throws IOException {
		List<MenigaNetWorthBalance> list = this.gson();
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);
		MenigaNetWorthBalance test = list.get(0);

		assertThat(test.getId()).isEqualTo(4792);
		assertThat(test.getNetWorthId()).isEqualTo(2938);
		assertThat(test.getBalance()).isEqualTo(new MenigaDecimal(-255));
		assertThat(test.getBalanceDate().getYear()).isEqualTo(2016);
		assertThat(test.getBalanceDate().getMonthOfYear()).isEqualTo(3);
		assertThat(test.getBalanceDate().getDayOfMonth()).isEqualTo(1);
		assertThat(test.getIsDefault()).isFalse();
	}

	@Test
	public void testCompare() {
		List<MenigaNetWorthBalance> items1 = this.gson();
		List<MenigaNetWorthBalance> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setBalance(new MenigaDecimal(93748));

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testClone() {
		List<MenigaNetWorthBalance> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaNetWorthBalance test = items.get(0);

		try {
			MenigaNetWorthBalance test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testRevert() throws IOException {
		List<MenigaNetWorthBalance> list = this.gson();
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);
		MenigaNetWorthBalance test = list.get(0);

		DateTime orgDate = test.getBalanceDate();
		test.setBalanceDate(test.getBalanceDate().plusMonths(3));
		MenigaDecimal orgBala = test.getBalance();
		test.setBalance(test.getBalance().add(new MenigaDecimal(100)));

		test.revert();

		assertThat(test.getBalanceDate()).isEqualTo(orgDate);
		assertThat(test.getBalance()).isEqualTo(orgBala);
	}

	@Test
	public void testParcel() {
		MenigaNetWorthBalance item = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaNetWorthBalance createdFromParcel = MenigaNetWorthBalance.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaNetWorthBalance> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getInputStreamFromRaw("networthbalance.json")),
					MenigaNetWorthBalance[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
