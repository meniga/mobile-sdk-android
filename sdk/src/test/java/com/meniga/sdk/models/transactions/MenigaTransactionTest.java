package com.meniga.sdk.models.transactions;

import android.os.Parcel;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.mocks.MenigaTransactionOperationsMock;
import com.meniga.sdk.providers.tasks.Continuation;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.utils.FileImporter;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@RunWith(RobolectricTestRunner.class)
public class MenigaTransactionTest{

	@Test
	public void testState() {
		MenigaTransaction trans = gson().get(0);

		Random rnd = new Random(System.currentTimeMillis());
		Boolean orgFlagged = trans.getIsFlagged();
		MenigaDecimal orgAmt = trans.getAmount();
		List<MenigaComment> orgComments = trans.getComments();
		MenigaComment comment = null;
		String orgCommentTxt = "";
		if (orgComments.size() > 0) {
			comment = orgComments.get(0);
			orgCommentTxt = comment.getComment();
		}
		DateTime orgDate = trans.getDate();
		Boolean orgIsRead = trans.getIsRead();

		trans.setIsFlagged(orgFlagged == null || !orgFlagged);
		trans.setAmount(new MenigaDecimal(667));

		if (comment != null) {
			comment.setComment("Comment comment comment " + System.currentTimeMillis());
		}
		trans.setDate(DateTime.now().minusDays(rnd.nextInt(30)));
		trans.setIsRead(orgIsRead == null || !orgIsRead);

		Assert.assertFalse(trans.getIsFlagged() == orgFlagged);
		Assert.assertFalse(trans.getAmount().toPlainString().equals(orgAmt.toPlainString()));
		if (comment != null) {
			Assert.assertFalse(orgCommentTxt.equals(comment.getComment()));
		}
		Assert.assertFalse(trans.getDate().getMillis() == orgDate.getMillis());

		trans.revert();

		Assert.assertTrue(trans.getIsFlagged() == orgFlagged);
		Assert.assertTrue(trans.getAmount().toPlainString().equals(orgAmt.toPlainString()));
		Assert.assertTrue(trans.getComments().size() == 0 ? comment == null : trans.getComments().get(0).getComment().equals(comment.getComment()));
		Assert.assertTrue(trans.getDate().getMillis() == orgDate.getMillis());
	}

	@Test
	public void testCompare() {
		List<MenigaTransaction> items1 = this.gson();
		List<MenigaTransaction> items2 = this.gson();

		assertThat(items1.get(0) == items2.get(0)).isFalse();

		assertThat(items1.get(0).equals(items2.get(0))).isTrue();

		items2.get(0).setAmount(new MenigaDecimal(937403));

		assertThat(items1.get(0).equals(items2.get(0))).isFalse();
	}

	@Test
	public void testClone() {
		List<MenigaTransaction> items = this.gson();
		assertThat(items).isNotNull();
		assertThat(items.size() > 0).isTrue();
		MenigaTransaction test = items.get(0);

		try {
			MenigaTransaction test2 = test.clone();
			assertThat(test2.equals(test)).isTrue();
			assertThat(test != test2).isTrue();
		} catch (CloneNotSupportedException e) {
			assertThat(false).isTrue();
		}
	}

	@Test
	public void testParcel() {
		MenigaTransaction transactionToTest = gson().get(0);

		// Obtain a Parcel object and write the parcelable object to it:
		Parcel parcel = Parcel.obtain();
		transactionToTest.writeToParcel(parcel, 0);

		// After you're done with writing, you need to reset the parcel for reading:
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		MenigaTransaction createdFromParcel = MenigaTransaction.CREATOR.createFromParcel(parcel);
		Assert.assertTrue(transactionToTest.equals(createdFromParcel));

		parcel.recycle();
	}

	@Test
	public void testFetch() {
		MenigaTransaction.setOperator(new MenigaTransactionOperationsMock());

		MenigaTransaction.fetch(245566).getTask()
				.onSuccess(new Continuation<MenigaTransaction, Void>() {
					@Override
					public Void then(Task<MenigaTransaction> task) throws Exception {
						Assert.assertEquals(245566, task.getResult().getId());
						return null;
					}
				});
	}

	@Test
	public void testCreateTransaction() throws UnsupportedEncodingException, JSONException {
		MenigaTransaction.setOperator(new MenigaTransactionOperationsMock());

		MenigaTransaction.create(DateTime.now(), "Hagkaup", new MenigaDecimal(5000), 45).getTask()
				.onSuccess(new Continuation<MenigaTransaction, Void>() {
					@Override
					public Void then(Task<MenigaTransaction> task) throws Exception {
						Assert.assertNotNull(task.getResult());
						return null;
					}
				});
	}

	private List<MenigaTransaction> gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		List<MenigaTransaction> transactions = null;
		try {
			transactions = Arrays.asList(
					gson.fromJson(MenigaConverter.getAsArray(FileImporter.getJsonFileFromRaw("transactions.json")), MenigaTransaction[].class)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactions;
	}
}
