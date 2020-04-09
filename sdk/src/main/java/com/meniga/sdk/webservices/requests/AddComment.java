package com.meniga.sdk.webservices.requests;

import java.util.List;

public class AddComment extends QueryRequestObject {

	public List<Long> transactionIds;
	public String comment;

	public AddComment(List<Long> transactionIds, String comment) {
		this.transactionIds = transactionIds;
		this.comment = comment;
	}

	@Override
	public long getValueHash() {
		int result = transactionIds.hashCode();
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}
}
