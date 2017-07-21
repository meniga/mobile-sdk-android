package com.meniga.sdk.models;

import com.meniga.sdk.utils.FileImporter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MockInterceptor implements Interceptor {
	private String jsonFile;

	public MockInterceptor(String jsonFile) {
		this.jsonFile = jsonFile;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		String json = FileImporter.getJsonFileFromRaw(jsonFile);
		return new Response.Builder()
				.code(200)
				.message(json)
				.request(chain.request())
				.protocol(Protocol.HTTP_1_0)
				.body(ResponseBody.create(MediaType.parse("application/json"), json.getBytes()))
				.addHeader("content-type", "application/json")
				.build();
	}
}
