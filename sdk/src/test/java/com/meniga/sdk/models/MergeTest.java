package com.meniga.sdk.models;

import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountTest;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import java.util.Collections;
import java.util.List;

import static com.meniga.sdk.models.accounts.MenigaAccountFactory.createAccount;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MergeTest {
	@Test
	public void testMerge() throws InitializationError, IllegalAccessException {
		MenigaAccount acc1 = createAccount(1);
		MenigaAccount acc2 = createAccount(2);

		String mergeName = "Test that merging works";
		acc2.setName(mergeName);

		Merge.merge(acc1, acc2);

		assertThat(acc1.getName()).isEqualTo(mergeName);
	}
}
