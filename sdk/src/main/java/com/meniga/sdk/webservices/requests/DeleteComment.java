package com.meniga.sdk.webservices.requests;

public class DeleteComment extends QueryRequestObject {

	public transient long transactionId;
	public transient long commentId;

	public DeleteComment(long transactionId, long commentId) {
		this.transactionId = transactionId;
		this.commentId = commentId;
	}

	@Override
	public long getValueHash() {
		int result = (int) (transactionId ^ (transactionId >>> 32));
		result = 31 * result + (int) (commentId ^ (commentId >>> 32));
		return result;
	}
}
