package com.meniga.sdk.models.networth.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.networth.MenigaNetWorth;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;
import com.meniga.sdk.webservices.requests.CreateNetWorthAccount;
import com.meniga.sdk.webservices.requests.DeleteNetWorthAccount;
import com.meniga.sdk.webservices.requests.GetNetWorth;
import com.meniga.sdk.webservices.requests.GetNetWorthFirstBalanceEntry;
import com.meniga.sdk.webservices.requests.GetNetWorthTypes;
import com.meniga.sdk.webservices.requests.GetNetWorths;
import com.meniga.sdk.webservices.requests.UpdatedNetWorthAccount;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaNetWorthOperationsImp implements MenigaNetWorthOperations {
	@Override
	public Result<MenigaNetWorth> getNetWorth(long id) {
		GetNetWorth req = new GetNetWorth();
		req.id = id;

		return MenigaSDK.executor().getNetWorth(req);
	}

	@Override
	public Result<List<MenigaNetWorth>> getNetWorth(DateTime startDate, DateTime endDate, boolean interpolate, int skip, int take) {
		GetNetWorths req = new GetNetWorths();
		req.startDate = startDate;
		req.endDate = endDate;
		req.useInterpolation = interpolate;
		req.skip = skip;
		req.take = take;

		return MenigaSDK.executor().getNetWorth(req);
	}

	@Override
	public Result<MenigaNetWorth> createNetWorthAccount(
			MenigaDecimal initialBalance,
			MenigaDecimal balance,
			String accountIdendefier,
			String displayName,
			String networthType,
			DateTime initialBalanceDate) {

		CreateNetWorthAccount req = new CreateNetWorthAccount();
		req.initialBalance = initialBalance;
		req.balance = balance;
		req.accountIdentifier = accountIdendefier;
		req.displayName = displayName;
		req.networthType = networthType;
		req.initialBalanceDate = initialBalanceDate;

		return MenigaSDK.executor().createNetWorthAccount(req);
	}

	@Override
	public Result<MenigaNetWorthBalance> fetchFirstBalanceEntry(boolean excludeAccountsExcludedFromNetWorth) {
		GetNetWorthFirstBalanceEntry req = new GetNetWorthFirstBalanceEntry();
		req.excludeAccountsExcludedFromNetWorth = excludeAccountsExcludedFromNetWorth;

		return MenigaSDK.executor().getNetWorthFirstBalanceEntry(req);
	}

	@Override
	public Result<Void> deleteNetWorthAccount(long idOfaccountToDelete) {
		DeleteNetWorthAccount deleteTransaction = new DeleteNetWorthAccount(idOfaccountToDelete);

		return MenigaSDK.executor().deleteNetWorthAccount(deleteTransaction);
	}

	@Override
	public Result<Void> updateNetWorthAccount(Long accountId, Boolean isExcluded, String accountName) {
		UpdatedNetWorthAccount req = new UpdatedNetWorthAccount();
		req.id = accountId;
		req.isExcluded = isExcluded;
		req.accountName = accountName;

		return MenigaSDK.executor().updateNetWorthAccount(req);
	}

	@Override
	public Result<List<KeyVal<Long, String>>> getNetWorthTypes() {
		return MenigaSDK.executor().getNetWorthTypes(new GetNetWorthTypes());
	}
}
