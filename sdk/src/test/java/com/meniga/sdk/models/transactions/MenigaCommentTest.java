package com.meniga.sdk.models.transactions;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MenigaCommentTest {

	@Test
	public void testSerialization() throws IOException {
		List<MenigaComment> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size()).isGreaterThan(0);
		MenigaComment test = items.get(0);

		assertThat(test.getComment()).isNotNull();
		assertThat(test.getCreatedDate().getYear()).isEqualTo(2016);
		assertThat(test.getCreatedDate().getMonthOfYear()).isEqualTo(4);
		assertThat(test.getCreatedDate().getDayOfMonth()).isEqualTo(20);
		assertThat(test.getId()).isEqualTo(206);
	}

	@Test
	public void testCompare() {
		List<MenigaComment> items1 = this.gson();
		List<MenigaComment> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setComment("Testcomment zero");

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testRevert() throws IOException {
		List<MenigaComment> comments = this.gson();
		assertThat(comments).isNotNull();
		assertThat(comments.size()).isGreaterThan(0);
		MenigaComment test = comments.get(0);

		String oldCOmment = test.getComment();
		test.setComment(test.getComment() + "Test");

		test.revert();

		assertThat(oldCOmment).isEqualTo(test.getComment());
	}

	@Test
	public void testClone() {
		List<MenigaComment> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaComment test = items.get(0);

		try {
			MenigaComment test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test2 != test).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testParcel() {
		MenigaComment category = this.gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		category.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaComment createdFromParcel = MenigaComment.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(category.equals(createdFromParcel));

		parcel.recycle();
	}

	private List<MenigaComment> gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		List<MenigaComment> categories = null;
		try {
			categories = Arrays.asList(
					gson.fromJson(MenigaConverter.getAsArray(
							FileImporter.getJsonFileFromRaw("comments.json")),
							MenigaComment[].class
					)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return categories;
	}
}
