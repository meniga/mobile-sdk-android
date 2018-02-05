package com.meniga.sdk.converters;

import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@Ignore("I'm not sure if we should test converters in one place (it does not test all cases). It's also tested in other places implicitly, e.g. MenigaBudgetTest.java")
public class MenigaConvertersTest extends MenigaConverter {

	@Test
	public void testConvertStreamToString() throws IOException {
		InputStream inputStream = FileImporter.getInputStreamFromRaw("transactions.json");

		Type menigaTransactionListType = new TypeToken<List<MenigaTransaction>>(){}.getType();
		List<MenigaTransaction> fromTest = GsonProvider.getGsonBuilder().fromJson(
				MenigaConverter.getAsArray(inputStream),
				menigaTransactionListType);
		List<MenigaTransaction> comparativeResult = GsonProvider.getGsonBuilder().fromJson(
				MenigaConverter.getAsArray(inputStream),
				menigaTransactionListType);

		Assert.assertEquals(fromTest, comparativeResult);
	}
}
