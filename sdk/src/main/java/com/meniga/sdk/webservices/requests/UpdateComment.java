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
		int result = (int) (transactionId ^ (transactionId >>> 32));
		result = 31 * result + (int) (commentId ^ (commentId >>> 32));
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}

}
