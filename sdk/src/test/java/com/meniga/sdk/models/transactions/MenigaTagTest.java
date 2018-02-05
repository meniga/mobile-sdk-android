package com.meniga.sdk.models.transactions;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
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
public class MenigaTagTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaTag menigaTag = this.gson().get(0);

		assertThat(menigaTag).isNotNull();
		assertThat(menigaTag.getId()).isEqualTo(39);
		assertThat(menigaTag.getName()).isEqualTo("newtag");
	}

	@Test
	public void testCompare() {
		List<MenigaTag> items1 = this.gson();
		List<MenigaTag> items2 = this.gson();
		MenigaTag item1 = items1.get(0);
		MenigaTag item2 = items2.get(0);

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();

		assertThat(item1.equals(items1.get(1))).isFalse();
	}

	@Test
	public void testParcel() throws IOException {
		MenigaTag menigaTag = this.gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		menigaTag.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaTag createdFromParcel = (MenigaTag) MenigaTag.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(menigaTag.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaTag> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(
					MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("tags.json")),
					MenigaTag[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
