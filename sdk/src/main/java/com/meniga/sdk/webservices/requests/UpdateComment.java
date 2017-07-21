package com.meniga.sdk.webservices.requests;

public class UpdateComment extends QueryRequestObject {

	public transient long transactionId;
	public transient long commentId;
	public String comment;

	public UpdateComment(long transactionId, long commentId, String comment) {
		this.transactionId = transactionId;
		this.commentId = commentId;
		this.comment = comment;
	}

	@Override
	public long getValueHash() {
		return this.transactionId + this.commentId + this.comment.hashCode();
	}
}
