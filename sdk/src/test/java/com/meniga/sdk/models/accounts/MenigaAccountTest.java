/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.ParcelExtensions;
import com.meniga.sdk.models.accounts.enums.AccountCategory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static com.meniga.sdk.models.accounts.MenigaAccountFactory.createAccount;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MenigaAccountTest {

	@Test
	public void testSerialization() throws IOException {
		MenigaAccount test = createAccount(2528);

		assertThat(test.getBalance().getBigDecimal()).isEqualTo(new MenigaDecimal("233").getBigDecimal());
		assertThat(test.getId()).isEqualTo(2528);
		assertThat(test.getName()).isEqualTo("AccountName" + 2528);
		assertThat(test.getAccountCategory()).isEqualTo(AccountCategory.WALLET);
	}

	@Test
	public void testCompare() {
		MenigaAccount item1 = createAccount();
		MenigaAccount item2 = createAccount();

		assertThat(item1 == item2).isFalse();

		assertThat(item1.equals(item2)).isTrue();

		item2.setHidden(true);

		assertThat(item1.equals(item2)).isFalse();
	}

	@Test
	public void testClone() throws CloneNotSupportedException {
		MenigaAccount test = createAccount();

		MenigaAccount test2 = test.clone();
		assertThat(test2.equals(test)).isTrue();
		assertThat(test != test2).isTrue();
	}

	@Test
	public void testParcel() {
		MenigaAccount account = createAccount();

		MenigaAccount createdFromParcel = ParcelExtensions.testParcel(account);
		assertThat(createdFromParcel).isEqualTo(account);
	}
}
