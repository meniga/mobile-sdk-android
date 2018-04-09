package com.meniga.sdk;

import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.user.ChallengeContentType;
import com.meniga.sdk.providers.tasks.Task;

import junit.framework.Assert;

import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

import static com.meniga.sdk.utils.MockResponseFactory.mockResponse;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaSyncTest {

	@Test
	public void test() throws Exception {
		// Create a MockWebServer. These are lean enough that you can create a new
		// instance for every unit test.
		MockWebServer server = new MockWebServer();

		// Schedule some responses.
		server.enqueue(mockResponse("syncstatus.json"));
		server.enqueue(mockResponse("syncresponse.json"));

		// Start the server.
		server.start();

		// Ask the server for its URL. You'll need this to make HTTP requests.
		HttpUrl baseUrl = server.url("/v1");
		MenigaSettings settings = new MenigaSettings.Builder().endpoint(baseUrl).build();
		MenigaSDK.init(settings);
		Task<MenigaSync> syncTask = MenigaSync.start(1000).getTask();
		syncTask.waitForCompletion();
		MenigaSync sync = syncTask.getResult();

		Assert.assertNotNull(sync);
		Assert.assertEquals(ChallengeContentType.TEXT, sync.getRealmSyncResponses().get(0).getAuthenticationChallenge().getContentType());
		// Shut down the server. Instances cannot be reused.
		server.shutdown();
	}
}
