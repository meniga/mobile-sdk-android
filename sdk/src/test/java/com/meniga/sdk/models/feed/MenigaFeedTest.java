package com.meniga.sdk.models.feed;

import android.os.Parcel;

import com.meniga.sdk.models.MockClient;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.webservices.requests.GetEvent;
import com.meniga.sdk.webservices.requests.GetFeed;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import retrofit2.Call;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaFeedTest {

	private MockInterceptor interceptor = new MockInterceptor("feed.json");
	private MockInterceptor eventInterceptor = new MockInterceptor("event.json");


	@Test
	public void testSerialization() throws IOException {
		GetFeed query = new GetFeed();
		query.dateFrom = DateTime.now();
		query.dateTo = DateTime.now();
		query.take = 20;
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		Assert.assertNotNull(feed);
	}

	@Test
	public void testParcelable() throws IOException {
		GetFeed query = new GetFeed();
		query.dateFrom = DateTime.now();
		query.dateTo = DateTime.now();
		query.take = 20;
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		feed.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaFeed createdFromParcel = com.meniga.sdk.models.feed.MenigaFeed.CREATOR.createFromParcel(parcel);
		Assert.assertEquals(feed, createdFromParcel);
	}

	@Test
	public void testTransactionCountEvent() throws IOException {
		GetFeed query = new GetFeed();
		query.dateFrom = DateTime.now();
		query.dateTo = DateTime.now();
		query.take = 20;

		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();

		Assert.assertNotNull(feed);
		Assert.assertEquals(MenigaTransactionCountEvent.class, feed.get(0).getClass());
	}

	@Test
	public void testTransactionEvent() throws IOException {
		GetFeed query = new GetFeed();
		query.dateFrom = DateTime.now();
		query.dateTo = DateTime.now();
		query.take = 20;
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		Assert.assertNotNull(feed);
		Assert.assertEquals(MenigaTransaction.class, feed.get(6).getClass());
	}

	@Test
	public void testGetEvent() throws IOException {
		GetEvent query = new GetEvent();
		query.id = 10;
		Call<MenigaFeedItem> call = MockClient.getApi(eventInterceptor).getEvent(query.id);
		MenigaFeedItem event = call.execute().body();
		Assert.assertNotNull(event);
	}
	
}
