package com.meniga.sdk.models.transactions;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaTransactionRuleTest{

	@Test
	public void testSerialization() throws IOException {
		List<MenigaTransactionRule> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaTransactionRule test = items.get(0);

		assertThat(test.getName()).isNotNull();
		assertThat(test.getName()).isEqualTo("Test Rule 1461078728805");
		assertThat(test.getTextCriteria()).isEqualTo("Meniga");
		assertThat(test.getRemoveAction()).isTrue();
	}

	@Test
	public void testCompare() {
		List<MenigaTransactionRule> items1 = this.gson();
		List<MenigaTransactionRule> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setAmountCriteria(new MenigaDecimal(937363));

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testClone() {
		List<MenigaTransactionRule> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaTransactionRule test = items.get(0);

		try {
			MenigaTransactionRule test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testRevert() throws IOException {
		List<MenigaTransactionRule> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaTransactionRule test = items.get(0);

		Boolean oldAcceptAction = test.getAcceptAction();
		test.setAcceptAction(!oldAcceptAction);
		String oldAccountCategoryCrit = test.getAccountCategoryCriteria();
		test.setAccountCategoryCriteria("pera");
		MenigaDecimal oldAmountCrit = test.getAmountCriteria();
		test.setAmountCriteria(new MenigaDecimal(100));
		List<MenigaTransactionRule.SplitAction> oldSplitAction = test.getSplitActions();
		List<MenigaTransactionRule.SplitAction> spl = new ArrayList<>();
		MenigaTransactionRule.SplitAction action = new MenigaTransactionRule.SplitAction();
		action.amount = new MenigaDecimal(300);
		action.categoryId = 345;
		action.id = 222;
		action.ratio = new MenigaDecimal(3);
		action.transactionRuleId = 82;
		spl.add(action);
		spl.add(action);
		test.setSplitActions(spl);

		test.revert();

		assertThat(oldAcceptAction).isEqualTo(test.getAcceptAction());
		assertThat(oldAccountCategoryCrit).isEqualTo(test.getAccountCategoryCriteria());
		assertThat(oldAmountCrit).isEqualTo(test.getAmountCriteria());
		assertThat(test.getSplitActions().size()).isEqualTo(1);
	}

	@Test
	public void testParcel() {
		MenigaTransactionRule category = this.gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		category.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaTransactionRule createdFromParcel = MenigaTransactionRule.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(category.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaTransactionRule> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		List<MenigaTransactionRule> categories = null;
		try {
			categories = Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(FileImporter.getJsonFileFromRaw("transactionrules.json")), MenigaTransactionRule[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return categories;
	}
}
