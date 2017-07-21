package com.meniga.sdk.helpers;

import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountTest;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MergeTest {
	@Test
	public void testMerge() throws InitializationError, IllegalAccessException {
		MenigaAccountTest test = new MenigaAccountTest();
		List<MenigaAccount> accs = test.gson();

		assertThat(accs.size()).isGreaterThan(1);

		MenigaAccount acc1 = accs.get(0);
		MenigaAccount acc2 = accs.get(1);

		String mergeName = "Test that merging works";
		acc2.setName(mergeName);

		Merge.merge(acc1, acc2);

		assertThat(acc1.getName()).isEqualTo(mergeName);
	}
}
