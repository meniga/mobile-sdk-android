package com.meniga.sdk.models.transactions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class TransactionFilterTest{

	@Test
	public void testFilterCreation() {
		TransactionsFilter filter1 = new TransactionsFilter
				.Builder()
				.searchText("Example")
				.accountIds(Arrays.asList(100L, 200L))
				.amounts(new MenigaDecimal(0), new MenigaDecimal(5000))
				.categories(Arrays.asList(1L, 2L))
				.onlyFlagged(false)
				.onlyUnread(false)
				.onlyUncertain(false)
				.transactions(Arrays.asList(1L, 2L))
				.merchantIds(Arrays.asList(1L, 2L))
				.tags(Collections.singletonList("test"))
				.period(new DateTime("2012-08-16T07:00:00Z"), new DateTime("2012-08-16T23:00:00Z"))
				.build();
		TransactionsFilter filter2 = new TransactionsFilter.Builder()
				.merchantTexts(Collections.singletonList("Hagkaup"))
				.build();

		TransactionsFilter merged = new TransactionsFilter.Builder().mergeFilters(filter1, filter2).build();
		Assert.assertEquals(merged.merchantTexts, Collections.singletonList("Hagkaup"));
		Assert.assertTrue(merged.getSearchText().equals("Example"));

		JsonElement obj = GsonProvider.getGsonBuilder().toJsonTree(merged);
		JsonObject jsonFilter = obj.getAsJsonObject();
		Assert.assertNotNull(jsonFilter);
		Assert.assertEquals("Example", jsonFilter.get("searchText").getAsString());
		String transIds = jsonFilter.get("ids").getAsJsonArray().toString();
		Assert.assertEquals("[1,2]", transIds);
		Assert.assertEquals("[1,2]", jsonFilter.get("merchantIds").getAsJsonArray().toString());
		Assert.assertEquals("[\"Hagkaup\"]", jsonFilter.get("merchantTexts").getAsJsonArray().toString());
		MenigaDecimal amountTo = new MenigaDecimal(jsonFilter.get("amountTo").getAsDouble());
		Assert.assertEquals(new MenigaDecimal(5000.0), amountTo);
		MenigaDecimal assetFrom = new MenigaDecimal(jsonFilter.get("amountFrom").getAsDouble());
		Assert.assertEquals(MenigaDecimal.ZERO, assetFrom);
		Assert.assertEquals(false, jsonFilter.get("onlyUnread").getAsBoolean());
		Assert.assertEquals(false, jsonFilter.get("onlyUncertain").getAsBoolean());
		Assert.assertEquals(false, jsonFilter.get("onlyFlagged").getAsBoolean());
		assertThat(DateTime.parse(jsonFilter.get("periodFrom").getAsString())).isEqualTo(new DateTime("2012-08-16T07:00:00Z"));
		assertThat(DateTime.parse(jsonFilter.get("periodTo").getAsString())).isEqualTo(new DateTime("2012-08-16T23:00:00Z"));
	}
}
