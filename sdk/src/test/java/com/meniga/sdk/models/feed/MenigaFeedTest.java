package com.meniga.sdk.models.feed;

import android.os.Parcel;

import com.meniga.sdk.models.MockClient;
import com.meniga.sdk.models.MockInterceptor;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.webservices.requests.GetEvent;
import com.meniga.sdk.webservices.requests.GetFeed;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import retrofit2.Call;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaFeedTest {

	private MockInterceptor interceptor = new MockInterceptor("feed.json");
	private MockInterceptor eventInterceptor = new MockInterceptor("event.json");
	private FeedFilter feedFilter = new FeedFilter.Builder()
			.from(DateTime.now())
			.to(DateTime.now())
			.itemsPerPage(20)
			.build();

	@Test
	public void testSerialization() throws IOException {
		GetFeed query = new GetFeed(feedFilter);
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		assertThat(feed).isNotNull();
	}

	@Test
	public void testParcelable() throws IOException {
		GetFeed query = new GetFeed(feedFilter);
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		feed.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaFeed createdFromParcel = com.meniga.sdk.models.feed.MenigaFeed.CREATOR.createFromParcel(parcel);
		assertThat(feed).isEqualTo(createdFromParcel);
	}

	@Test
	public void testTransactionCountEvent() throws IOException {
		GetFeed query = new GetFeed(feedFilter);

		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();

		assertThat(feed).isNotNull();
		assertThat(feed.get(1)).isInstanceOf(MenigaTransactionCountEvent.class);
	}

	@Test
	public void testTransactionEvent() throws IOException {
		GetFeed query = new GetFeed(feedFilter);
		Call<MenigaFeed> call = MockClient.getApi(interceptor).getFeed(query.toQueryMap());
		MenigaFeed feed = call.execute().body();
		assertThat(feed).isNotNull();
		assertThat(feed.get(6)).isInstanceOf(MenigaTransaction.class);
	}

	@Test
	public void testGetEvent() throws IOException {
		GetEvent query = new GetEvent();
		query.id = 10;
		Call<MenigaFeedItem> call = MockClient.getApi(eventInterceptor).getEvent(query.id);
		MenigaFeedItem event = call.execute().body();
		assertThat(event).isNotNull();
	}
}
