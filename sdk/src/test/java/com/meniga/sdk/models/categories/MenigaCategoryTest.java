package com.meniga.sdk.models.categories;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.categories.enums.CategoryType;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaCategoryTest {

	@Test
	public void testSerialization() throws IOException {
		List<MenigaCategory> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaCategory test = items.get(0);

		assertThat(test.getName()).isNotNull();
		assertThat(test.getName()).isEqualTo("Alcohol");
		assertThat(test.getParentCategoryId()).isEqualTo(70);
		assertThat(test.getIsPublic()).isTrue();
		assertThat(test.getCategoryType()).isEqualTo(CategoryType.EXPENSES);

		boolean incomeFound = false;
		for (MenigaCategory cat : items) {
			if (cat.getCategoryType() == CategoryType.INCOME) {
				incomeFound = true;
				break;
			}
		}
		assertThat(incomeFound).isTrue();
	}

	@Test
	public void testCompare() {
		List<MenigaCategory> items1 = this.gson();
		List<MenigaCategory> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		MenigaUserCategory subItem1 = new MenigaUserCategory(items1.get(0));
		MenigaUserCategory subItem2 = new MenigaUserCategory(items2.get(0));

		assertThat(subItem1.equals(subItem2)).isTrue();

		subItem2.setParentCategoryId(8367467);

		assertThat(subItem1.equals(subItem2)).isFalse();
	}

	@Test
	public void testRevert() throws IOException {
		List<MenigaCategory> categories = this.gson();
		assertThat(categories).isNotNull();
		assertThat(categories.size()).isGreaterThan(0);
		MenigaUserCategory test = new MenigaUserCategory(categories.get(0));

		String oldName = test.getName();
		test.setName(oldName + "Test");
		CategoryType oldCategoryType = test.getCategoryType();
		test.setCategoryType(CategoryType.EXCLUDED);
		boolean oldFixed = test.getIsFixedExpenses();
		test.setIsFixedExpenses(!oldFixed);
		long oldParentId = test.getParentCategoryId();
		test.setParentCategoryId(oldParentId + 1);

		test.revert();

		assertThat(oldName).isEqualTo(test.getName());
		assertThat(oldCategoryType).isEqualTo(test.getCategoryType());
		assertThat(oldFixed).isEqualTo(test.getIsFixedExpenses());
		assertThat(oldParentId).isEqualTo(test.getParentCategoryId());
	}

	@Test
	public void testParcel() {
		MenigaCategory category = this.gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		category.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaCategory createdFromParcel = MenigaCategory.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(category.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaCategory> gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		List<MenigaCategory> categories = null;
		try {
			categories = Arrays.asList(gson.fromJson(MenigaConverter.getAsArray(FileImporter.getJsonFileFromRaw("categories.json")), MenigaCategory[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return categories;
	}
}
