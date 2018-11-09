package com.meniga.sdk.models.transactions;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTransactionListTest{

	@Test
	public void testSerialization() throws IOException {
		MenigaTransactionPage items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);

		assertThat(items.size()).isGreaterThan(0);
		assertThat(items.get(0).getText()).isEqualTo("London Car Parking");
	}

	@Test
	public void testCompare() {
		MenigaTransactionPage item1 = this.gson();
		MenigaTransactionPage item2 = this.gson();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();
	}

	private MenigaTransactionPage gson() {
		Gson gson = GsonProvider.getGson();
		MenigaTransactionPage items = null;
		try {
			List<MenigaTransaction> list = Arrays.asList(
					gson.fromJson(
							MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("transactions.json")), MenigaTransaction[].class
					)
			);
			items = new MenigaTransactionPage();
			items.addAll(list);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return items;
	}
}
