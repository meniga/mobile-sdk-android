package com.meniga.sdk.models.merchants;

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

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaMerchantTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaMerchant test = this.gson();
		assertThat(test).isNotNull();

		assertThat(test.getAddress()).isNotNull();
		assertThat(test.getAddress().getCountry()).isEqualTo("Iceland");
		assertThat(test.getName()).isEqualTo("T-Mobile Uk");
	}

	@Test
	public void testCompare() {
		MenigaMerchant items1 = this.gson();
		MenigaMerchant items2 = this.gson();

		assertThat(items1 == items2).isFalse();

		assertThat(items1.equals(items2)).isTrue();
	}

	@Test
	public void testParcel() {
		MenigaMerchant item = this.gson();

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaMerchant createdFromParcel = MenigaMerchant.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));

		parcel.recycle();
	}

	@Test
	public void testAddressWithNullsAndEmptyString() {
		MenigaMerchant item = gson("merchant2.json");
		Assert.assertNull(item.getAddress().getLatitude());
		Assert.assertNull(item.getAddress().getLongitude());
	}

	@Test
	public void testAddressWithTrueDoubleAndInt() {
		MenigaMerchant item = gson("merchant3.json");
		Assert.assertEquals(item.getAddress().getLatitude(), 50.0);
		Assert.assertEquals(item.getAddress().getLongitude(), 40.0);
	}

	private MenigaMerchant gson() {
		return gson("merchant.json");
	}

	private MenigaMerchant gson(String which) {
		Gson gson = GsonProvider.getGson();
		MenigaMerchant item = null;
		try {
			item = gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getInputStreamFromRaw(which)),
					MenigaMerchant.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return item;
	}
}

