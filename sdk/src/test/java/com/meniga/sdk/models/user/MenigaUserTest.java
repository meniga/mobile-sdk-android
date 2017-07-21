package com.meniga.sdk.models.user;

import com.google.gson.Gson;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;



import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaUserTest{

	@Test
	public void testMenigaUserSerialization() throws IOException {
		Gson gson = GsonProvider.getGsonBuilder().create();
		MenigaUserProfile menigaUser = gson.fromJson(FileImporter.getJsonFileFromRaw("user.json"), MenigaUserProfile.class);
		assertThat(menigaUser).isNotNull();
	}
}
