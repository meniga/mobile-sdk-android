package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionUpdate;
import com.meniga.sdk.models.transactions.TransactionsFilter;
import com.meniga.sdk.webservices.requests.UpdateSplits;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaTransactionOperations {

	Result<MenigaTransaction> getTransaction(long id);

	Result<MenigaTransaction> createTransaction(DateTime date, String text, MenigaDecimal amount, long categoryId);

	Result<Void> deleteTransaction(long id);

	Result<MenigaTransactionUpdate> updateTransaction(MenigaTransaction menigaTransaction);

	Result<List<MenigaTransaction>> fetchSplitTransactions(MenigaTransaction menigaTransaction);

	Result<List<MenigaTransaction>> splitTransaction(long parentId, MenigaDecimal amount, String text, long categoryId, boolean isFlagged);

	Result<MenigaTransactionPage> getTransactions(TransactionsFilter transFilter);

	Result<Void> deleteTransactions(List<Long> transactionIds);

	Result<Void> recategorize(List<String> transactionTexts, Boolean recategorizeUnreadOnly, Boolean useSubTextInRecat, Boolean markAsRead);

	Result<MenigaTransactionUpdate> updateTransactions(
			List<Long> transToUpdate,
			MenigaDecimal amount,
			Long categoryId,
			Boolean hasUncertainCategorization,
			Boolean useSubTextInRecat,
			String text,
			DateTime date,
			Boolean isRead,
			Boolean isFlagged,
			String userData
	);

	Result<List<MenigaTransaction>> updateSplits(long id, List<UpdateSplits> updates);
}
