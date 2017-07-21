package com.meniga.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.user.MenigaUser;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import static com.meniga.sdk.converters.MenigaConverter.getAsArray;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaUserTest extends MenigaConverter  {

	@Test
	public void testConvertStreamToString() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("raw/users.json");
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);
		String body = this.convertStreamToString(fin);
		Type typeOfMenigaAccountList = new TypeToken<List<MenigaAccount>>() {}.getType();
		Gson gson = GsonProvider.getGsonBuilder().create();
		List<MenigaUser> users = gson.fromJson(getAsArray(body), new TypeToken<List<MenigaUser>>(){}.getType());

	}

	@Test(expected = IOException.class)
	public void testConvertStreamToStringException() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("raw/users.json");
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);
		fin.close();
		convertStreamToString(fin);
	}
}
