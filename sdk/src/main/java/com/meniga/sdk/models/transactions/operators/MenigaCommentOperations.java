package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaComment;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaCommentOperations {
	Result<MenigaComment> createComment(long transId, String comment);

	Result<Void> updateComment(MenigaComment comment);

	Result<Void> deleteComment(long transactionId, long commentId);
}
