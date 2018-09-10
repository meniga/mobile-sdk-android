package com.meniga.sdk.models.upcoming;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.MockClient;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.models.upcoming.enums.CronDayOfWeek;
import com.meniga.sdk.models.upcoming.enums.CronMonth;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@RunWith(RobolectricTestRunner.class)
public class MenigaUpcomingTest {

	private MockInterceptor interceptor = new MockInterceptor("upcoming_single.json");

	@Test
	public void testSerialization() throws IOException {
		List<MenigaUpcoming> list = gson();
		assertThat(list).isNotNull();

		assertThat(list.size() > 1).isEqualTo(true);

		MenigaUpcoming test = list.get(0);
		assertThat(test.getText()).isEqualTo("Heating bill");
		assertThat(test.getCurrencyCode()).isEqualTo("ISK");

		assertThat(test.getRecurringPattern() == null).isEqualTo(false);
	}

	@Test
	public void testCompare() {
		List<MenigaUpcoming> items1 = gson();
		List<MenigaUpcoming> items2 = gson();

		assertThat(items1.size() > 1).isEqualTo(true);
		assertThat(items2.size() > 1).isEqualTo(true);

		MenigaUpcoming item1 = items1.get(0);
		MenigaUpcoming item2 = items2.get(0);

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	@Test
	public void testParcel() {
		List<MenigaUpcoming> items = gson();

		assertThat(items.size() > 0).isEqualTo(true);
		MenigaUpcoming item = items.get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaUpcoming createdFromParcel = MenigaUpcoming.CREATOR.createFromParcel(parcel);
		assertThat(item.equals(createdFromParcel)).isEqualTo(true);

		parcel.recycle();
	}

	@Test
	public void testSerializationWithMockClient() throws IOException {
		Call<MenigaUpcoming> call = MockClient.getApi(interceptor).getUpcoming(14);
		MenigaUpcoming item = call.execute().body();
		Assert.assertNotNull(item);
	}

	@Test
	public void testParcelableWithMockClient() throws IOException {
		Call<MenigaUpcoming> call = MockClient.getApi(interceptor).getUpcoming(14);
		MenigaUpcoming item = call.execute().body();

		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);

		parcel.setDataPosition(0);

		MenigaUpcoming createdFromParcel = MenigaUpcoming.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(item.equals(createdFromParcel));
	}

	@Test
	public void testParcelableRecurringPattern() {
		MenigaUpcomingRecurringPattern pattern = new MenigaUpcomingRecurringPattern();
		pattern.setAmountInCurrency(new MenigaDecimal(300));
		MenigaUpcomingCronExpression cron = new MenigaUpcomingCronExpression();
		cron.setDayOfMonth("1");
		cron.setDayOfMonthInterval(2);
		cron.setDayOfWeek("Saturday");
		cron.setDayOfWeekInterval(3);
		cron.setMonth(CronMonth.APRIL);
		pattern.setPattern(cron);
		DateTime date = DateTime.now().plusYears(1);
		pattern.setRepeatUntil(date);

		Parcel parcel = Parcel.obtain();
		pattern.writeToParcel(parcel, 0);
		parcel.setDataPosition(0);
		MenigaUpcomingRecurringPattern pattern2 = MenigaUpcomingRecurringPattern.CREATOR.createFromParcel(parcel);

		Assert.assertEquals(pattern.getAmountInCurrency().intValue(), pattern2.getAmountInCurrency().intValue());
		Assert.assertEquals(pattern.getPattern(), pattern2.getPattern());
		Assert.assertEquals(pattern.getRepeatUntil(), pattern2.getRepeatUntil());

		MenigaUpcomingCronExpression cron2 = pattern2.getPattern();
		Assert.assertEquals(cron.getDayOfMonth(), cron2.getDayOfMonth());
		Assert.assertEquals(cron.getDayOfMonthInterval(), cron2.getDayOfMonthInterval());
		Assert.assertEquals(cron.getDayOfWeek(), cron2.getDayOfWeek());
		Assert.assertEquals(cron.getDayOfWeekInterval(), cron2.getDayOfWeekInterval());
		Assert.assertEquals(cron.getDayOfWeekInterval(), cron2.getDayOfWeekInterval());
		Assert.assertEquals(cron.getMonth(), cron2.getMonth());
	}


	private List<MenigaUpcoming> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		MenigaUpcoming[] items = null;
		try {
			items = gson.fromJson(
					MenigaConverter.getAsArray(
							FileImporter.getInputStreamFromRaw("upcoming.json")),
					MenigaUpcoming[].class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Arrays.asList(items);
	}
}
