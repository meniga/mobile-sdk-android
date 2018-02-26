package com.meniga.sdk.models.userevents;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Assert;
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
public class UserEventsTest {

	private MockInterceptor interceptor = new MockInterceptor("userevents.json");

	@Test
	public void testSerialization() throws IOException {
		MenigaUserEvent menigaUserEvent = this.gson().get(0);

		assertThat(menigaUserEvent).isNotNull();
		assertThat(menigaUserEvent.getSubscriptions().size()).isEqualTo(3);
		assertThat(menigaUserEvent.getChildren().size()).isEqualTo(1);
		assertThat(menigaUserEvent.getSettings().size()).isEqualTo(0);
	}

	@Test
	public void testParcel() {
		MenigaUserEvent menigaUserEvent = this.gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		menigaUserEvent.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaUserEvent createdFromParcel = MenigaUserEvent.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(menigaUserEvent.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaUserEvent> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(
					MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("userevents.json")),
					MenigaUserEvent[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
