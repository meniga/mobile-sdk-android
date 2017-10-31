package com.meniga.sdk.models.mocks;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionOperations;
import com.meniga.sdk.utils.FileImporter;
import com.meniga.sdk.webservices.requests.UpdateSplits;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionOperationsMock implements MenigaTransactionOperations {
	private final MenigaTransactionPage transactionPage;

	public MenigaTransactionOperationsMock() {
		this.transactionPage = this.gson();
	}

	@Override
	public Result<MenigaTransaction> getTransaction(long id) {
		TaskCompletionSource<MenigaTransaction> task = new TaskCompletionSource<>();
		task.setResult(this.transactionPage.get(0));
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<MenigaTransaction> createTransaction(DateTime date, String text, MenigaDecimal amount, long categoryId) {
		TaskCompletionSource<MenigaTransaction> task = new TaskCompletionSource<>();
		task.setResult(this.transactionPage.get(0));
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> deleteTransaction(long id) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> updateTransaction(MenigaTransaction menigaTransaction) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<MenigaTransactionPage> getTransactions(TransactionsFilter transFilter) {
		TaskCompletionSource<MenigaTransactionPage> task = new TaskCompletionSource<>();
		task.setResult(this.transactionPage);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> deleteTransactions(List<Long> transactionIds) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> recategorize(List<String> transactionTexts, Boolean recategorizeUnreadOnly, Boolean useSubTextInRecat, Boolean markAsRead) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaTransaction>> updateTransactions(
			List<Long> transToUpdate,
			MenigaDecimal amount,
			Long categoryId,
			Boolean hasUncertainCategorization,
			Boolean useSubTextInRecat,
			String text,
			DateTime date,
			Boolean isRead,
			Boolean isFlagged,
			String userData) {
		TaskCompletionSource<List<MenigaTransaction>> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaTransaction>> updateSplits(long id, List<UpdateSplits> updates) {
		TaskCompletionSource<List<MenigaTransaction>> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaTransaction>> fetchSplitTransactions(MenigaTransaction menigaTransaction) {
		TaskCompletionSource<List<MenigaTransaction>> task = new TaskCompletionSource<>();
		List<MenigaTransaction> list = new ArrayList<>();
		list.add(menigaTransaction);
		task.setResult(list);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaTransaction>> splitTransaction(long parentId, MenigaDecimal amount, String text, long categoryId, boolean isFlagged) {
		TaskCompletionSource<List<MenigaTransaction>> task = new TaskCompletionSource<>();
		task.setResult(new MenigaTransactionPage());
		return new MTask<>(task.getTask(), task);
	}

	private MenigaTransactionPage gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		MenigaTransactionPage transactionPage = null;
		try {
			String body = FileImporter.getJsonFileFromRaw("transactions.json");
			MenigaTransaction[] arr = gson.fromJson(MenigaConverter.getAsArray(body), MenigaTransaction[].class);
			transactionPage = new MenigaTransactionPage();
			transactionPage.addAll(Arrays.asList(arr));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactionPage;
	}
}
