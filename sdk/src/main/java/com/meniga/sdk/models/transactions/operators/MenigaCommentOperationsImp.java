package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.webservices.requests.CreateComment;
import com.meniga.sdk.webservices.requests.DeleteComment;
import com.meniga.sdk.webservices.requests.UpdateComment;
/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCommentOperationsImp implements MenigaCommentOperations {

	@Override
	public Result<MenigaComment> createComment(long transId, String comment) {
		CreateComment req = new CreateComment();
		req.transactionId = transId;
		req.comment = comment;

		return MenigaSDK.executor().createComment(req);
	}

	@Override
	public Result<Void> updateComment(final MenigaComment menigaComment) {
		UpdateComment req = new UpdateComment(menigaComment.getTransactionId(), menigaComment.getId(), menigaComment.getComment());

		return MenigaSDK.executor().updateComment(req);
	}

	@Override
	public Result<Void> deleteComment(long transactionId, long commentId) {
		DeleteComment req = new DeleteComment(transactionId, commentId);

		return MenigaSDK.executor().deleteComment(req);
	}
}
