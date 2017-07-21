package com.meniga.sdk.models.networth.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;
import com.meniga.sdk.webservices.requests.CreateNetWorthBalanceHistory;
import com.meniga.sdk.webservices.requests.DeleteNetWorthBalance;
import com.meniga.sdk.webservices.requests.UpdateHistoryBalance;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaNetWorthBalanceOperationsImp implements MenigaNetWorthBalanceOperations {
	@Override
	public Result<Void> updateBalance(long accountId, long balanceId, MenigaDecimal newBalance, DateTime balanceDate) {
		UpdateHistoryBalance req = new UpdateHistoryBalance();
		req.accountId = accountId;
		req.balanceId = balanceId;
		req.balance = newBalance;
		req.balanceDate = balanceDate;

		return MenigaSDK.executor().updateBalance(req);
	}

	@Override
	public Result<Void> deleteBalance(long accountId, long id) {
		DeleteNetWorthBalance req = new DeleteNetWorthBalance();
		req.id = id;
		req.accountId = accountId;

		return MenigaSDK.executor().deleteBalance(req);
	}

	@Override
	public Result<MenigaNetWorthBalance> createNetWorthBalanceHistory(long id, MenigaDecimal balance, DateTime balanceDate) {
		CreateNetWorthBalanceHistory req = new CreateNetWorthBalanceHistory();
		req.id = id;
		req.balance = balance;
		req.balanceDate = balanceDate;

		return MenigaSDK.executor().createNetWorthBalanceHistory(req);
	}
}
