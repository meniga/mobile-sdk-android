package com.meniga.sdk.models.networth;

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
public class MenigaNetWorthTest {

	@Test
	public void testMenigaNetWorthSerialization() throws IOException {
		List<MenigaNetWorth> list = this.gson();
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);
		MenigaNetWorth test = list.get(0);

		assertThat(test.getName()).isNotNull();
		assertThat(test.getName()).isEqualTo("12are");
		assertThat(test.getCurrentBalance()).isEqualTo(new MenigaDecimal(70000));
		assertThat(test.getIsExcluded()).isFalse();
		assertThat(test.getHistory()).isNotNull();
		assertThat(test.getHistory().size()).isGreaterThan(0);
	}

	@Test
	public void testCompare() {
		List<MenigaNetWorth> items1 = this.gson();
		List<MenigaNetWorth> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setCurrentBalance(new MenigaDecimal(93748));

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testClone() {
		List<MenigaNetWorth> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaNetWorth test = items.get(0);

		try {
			MenigaNetWorth test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testMenigaNetWorthRevert() throws IOException {
		List<MenigaNetWorth> categories = this.gson();
		assertThat(categories).isNotNull();
		assertThat(categories.size()).isGreaterThan(0);

		MenigaNetWorth test = categories.get(0);

		String oldName = test.getName();
		test.setName(test.getName() + "Test");
		MenigaDecimal oldBala = test.getCurrentBalance();
		test.setCurrentBalance(test.getCurrentBalance().add(new MenigaDecimal(100)));
		boolean excl = test.getIsExcluded();
		test.setIsExcluded(!test.getIsExcluded());

		test.revert();

		assertThat(oldName).isEqualTo(test.getName());
		assertThat(oldBala).isEqualTo(test.getCurrentBalance());
		assertThat(excl).isEqualTo(test.getIsExcluded());
		assertThat(excl).isEqualTo(test.getIsExcluded());
	}

	@Test
	public void testParcel() {
		MenigaNetWorth item = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaNetWorth createdFromParcel = MenigaNetWorth.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaNetWorth> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getJsonFileFromRaw("networth.json")),
					MenigaNetWorth[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
