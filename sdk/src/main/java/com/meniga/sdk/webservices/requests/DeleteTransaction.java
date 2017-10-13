package com.meniga.sdk.webservices.requests;

public class DeleteTransaction extends QueryRequestObject {

	public long transactionId;

	public DeleteTransaction(long transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public long getValueHash() {
		return (int) (transactionId ^ (transactionId >>> 32));
	}
}
