package com.meniga.sdk.converters;

import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */


public class MenigaConvertersTest extends MenigaConverter {

	@Test
	public void testConvertStreamToString() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("raw/transactions.json");
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);

		String comparison = FileImporter.getJsonFileFromRaw("transactions.json");

		List<MenigaTransaction> fromTest = GsonProvider.getGsonBuilder().fromJson(
				MenigaConverter.getAsArray(fin),
				new TypeToken<List<MenigaTransaction>>(){}.getType()
		);
		List<MenigaTransaction> comparativeResult = GsonProvider.getGsonBuilder().fromJson(
				MenigaConverter.getAsArray(fin),
				new TypeToken<List<MenigaTransaction>>(){}.getType()
		);

		Assert.assertEquals(fromTest, comparativeResult);
	}

	@Test(expected = IOException.class)
	public void testConvertStreamToStringException() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("raw/transactions.json");
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);
		fin.close();
		convertStreamToString(fin);
	}
}
