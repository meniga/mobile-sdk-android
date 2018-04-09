package com.meniga.sdk;

import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.providers.tasks.Task;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaFeedTest {

	@Test
	public void test() throws Exception {

		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(mockResponse("feed.json"));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		Task<MenigaFeed> feedTaskTask = MenigaFeed.fetch(new DateTime(), new DateTime()).getTask();
		feedTaskTask.waitForCompletion();
		MenigaFeed feed = feedTaskTask.getResult();
		Assert.assertNotNull(feed);
		Assert.assertEquals(12, feed.size());
		// Shut down the server. Instances cannot be reused.
		server.shutdown();

	}
}
