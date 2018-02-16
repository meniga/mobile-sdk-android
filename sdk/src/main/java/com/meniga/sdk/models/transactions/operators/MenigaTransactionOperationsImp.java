package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionUpdate;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.webservices.requests.CreateTransaction;
import com.meniga.sdk.webservices.requests.DeleteTransaction;
import com.meniga.sdk.webservices.requests.DeleteTransactions;
import com.meniga.sdk.webservices.requests.GetSplitTransactions;
import com.meniga.sdk.webservices.requests.GetTransaction;
import com.meniga.sdk.webservices.requests.GetTransactions;
import com.meniga.sdk.webservices.requests.RecategorizeTransactions;
import com.meniga.sdk.webservices.requests.SplitTransaction;
import com.meniga.sdk.webservices.requests.UpdateSplits;
import com.meniga.sdk.webservices.requests.UpdateTransaction;
import com.meniga.sdk.webservices.requests.UpdateTransactions;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public final class MenigaTransactionOperationsImp implements MenigaTransactionOperations {

	@Override
	public Result<MenigaTransactionPage> getTransactions(TransactionsFilter transFilter) {
		GetTransactions req = new GetTransactions();
		req.filter = transFilter;

		return MenigaSDK.executor().getTransactions(req);
	}

	@Override
	public Result<MenigaTransaction> getTransaction(long transactionId) {
		GetTransaction req = new GetTransaction();
		req.id = transactionId;

		return MenigaSDK.executor().getTransaction(req);
	}

	@Override
	public Result<MenigaTransactionUpdate> updateTransaction(final MenigaTransaction trans) {
		UpdateTransaction update = new UpdateTransaction();
		update.transactionId = trans.getId();
		update.amount = trans.getAmount();
		update.categoryId = trans.getCategoryId();
		update.date = trans.getDate();
		update.hasUncertainCategorization = trans.getHasUncertainCategorization();
		update.useSubTextInRecat = true;
		update.text = trans.getText();
		update.isRead = trans.getIsRead();
		update.isFlagged = trans.getIsFlagged();
		update.userData = trans.getUserData();

		return MenigaSDK.executor().updateTransaction(update);
	}

	@Override
	public Result<MenigaTransactionUpdate> updateTransactions(
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
		UpdateTransactions req = new UpdateTransactions();
		req.transactionIds = transToUpdate;
		req.amount = amount;
		req.categoryId = categoryId;
		req.hasUncertainCategorization = hasUncertainCategorization;
		req.useSubTextInRecat = useSubTextInRecat;
		req.text = text;
		req.date = date;
		req.isRead = isRead;
		req.isFlagged = isFlagged;
		req.userData = userData;

		return MenigaSDK.executor().updateTransactions(req);
	}

	@Override
	public Result<List<MenigaTransaction>> updateSplits(long id, List<UpdateSplits> updates) {
		return MenigaSDK.executor().updateSplits(id, updates);
	}

	@Override
	public Result<List<MenigaTransaction>> fetchSplitTransactions(MenigaTransaction menigaTransaction) {
		GetSplitTransactions req = new GetSplitTransactions();
		req.parentId = menigaTransaction.getId();

		return MenigaSDK.executor().fetchSplitTransactions(req);
	}

	@Override
	public Result<List<MenigaTransaction>> splitTransaction(long parentId, MenigaDecimal amount, String text, long categoryId, boolean isFlagged) {
		SplitTransaction req = new SplitTransaction();

		req.amount = amount;
		req.text = text;
		req.categoryId = categoryId;
		req.isFlagged = isFlagged;
		req.transactionId = parentId;

		return MenigaSDK.executor().splitTransaction(req);
	}

	@Override
	public Result<MenigaTransaction> createTransaction(DateTime date, String text, MenigaDecimal amount, long categoryId) {
		CreateTransaction req = new CreateTransaction();
		req.date = date;
		req.text = text;
		req.amount = amount;
		req.categoryId = categoryId;
		req.setAsRead = true;

		return MenigaSDK.executor().createTransaction(req);
	}

	@Override
	public Result<Void> deleteTransaction(long id) {
		DeleteTransaction deleteTransaction = new DeleteTransaction(id);

		return MenigaSDK.executor().deleteTransaction(deleteTransaction);
	}

	@Override
	public Result<Void> deleteTransactions(List<Long> transactionIds) {
		DeleteTransactions deleteTransactions = new DeleteTransactions();
		deleteTransactions.transactionIds = transactionIds;

		return MenigaSDK.executor().deleteTransactions(deleteTransactions);
	}

	@Override
	public Result<Void> recategorize(List<String> transactionTexts, Boolean recategorizeUnreadOnly, Boolean useSubTextInRecat, Boolean markAsRead) {
		RecategorizeTransactions req = new RecategorizeTransactions();
		req.transactionTexts = transactionTexts;
		req.recategorizeUnreadOnly = recategorizeUnreadOnly;
		req.useSubTextInRecat = useSubTextInRecat;
		req.markAsRead = markAsRead;

		return MenigaSDK.executor().recategorizeTransactions(req);
	}
}
