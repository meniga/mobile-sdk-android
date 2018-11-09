package com.meniga.sdk.models.feed;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

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
public class MenigaScheduledTest {


	@Test
	public void testMenigaScheduledSerialization() throws IOException {
		List<MenigaScheduledEvent> list = this.gson();
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);
		MenigaScheduledEvent test = list.get(0);

		assertThat(test.getBody()).isNotNull();
		assertThat(test.getTopicName()).isEqualTo("Info");
		assertThat(test.getTotalExpenses().doubleValue()).isEqualTo(-70280.0);
		assertThat(test.getExpensesPerCategory().size()).isEqualTo(8);
		assertThat(test.getExpensesPerCategory().get(14L).doubleValue()).isEqualTo(-55130.0);
	}

	@Test
	public void testCompare() {
		List<MenigaScheduledEvent> items1 = this.gson();
		List<MenigaScheduledEvent> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();
	}

	@Test
	public void testClone() {
		List<MenigaScheduledEvent> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaScheduledEvent test = items.get(0);

		try {
			MenigaScheduledEvent test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testParcel() {
		MenigaScheduledEvent item = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaScheduledEvent createdFromParcel = MenigaScheduledEvent.CREATOR.createFromParcel(parcel);
		assertThat(item.equals(createdFromParcel)).isTrue();

		parcel.recycle();
	}

	private List<MenigaScheduledEvent> gson() {
		Gson gson = GsonProvider.getGson();
		try {
			return Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(
					FileImporter.getInputStreamFromRaw("scheduledevents.json")),
					MenigaScheduledEvent[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
