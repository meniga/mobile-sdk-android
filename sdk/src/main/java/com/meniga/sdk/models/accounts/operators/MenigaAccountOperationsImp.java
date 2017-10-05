package com.meniga.sdk.models.accounts.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountBalanceHistory;
import com.meniga.sdk.models.accounts.MenigaAccountType;
import com.meniga.sdk.models.accounts.MenigaAuthorizationType;
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;
import com.meniga.sdk.webservices.requests.DeleteAccount;
import com.meniga.sdk.webservices.requests.GetAccount;
import com.meniga.sdk.webservices.requests.GetAccountBalanceHistory;
import com.meniga.sdk.webservices.requests.GetAccountMetadata;
import com.meniga.sdk.webservices.requests.GetAccountMetadataKeyVal;
import com.meniga.sdk.webservices.requests.GetAccountTypes;
import com.meniga.sdk.webservices.requests.GetAccounts;
import com.meniga.sdk.webservices.requests.GetAuthorizationTypes;
import com.meniga.sdk.webservices.requests.UpdateAccount;
import com.meniga.sdk.webservices.requests.UpdateAccountMetadata;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountOperationsImp implements MenigaAccountOperations {
	@Override
	public Result<MenigaAccount> getAccount(long id) {
		GetAccount req = new GetAccount();
		req.accountId = id;

		return MenigaSDK.executor().getAccount(req);
	}

	@Override
	public Result<List<MenigaAccount>> getAccounts(boolean includeHidden, boolean includeDisabled) {
		GetAccounts req = new GetAccounts();
		req.includeHidden = includeHidden;
		req.includeDisabled = includeDisabled;
		return MenigaSDK.executor().getAccounts(req);
	}

	@Override
	public Result<Void> updateAccount(MenigaAccount account) {
		UpdateAccount req = new UpdateAccount();
		req.id = account.getId();
		req.name = account.getName();
		req.isHidden = account.getIsHidden();
		req.isDisabled = account.getIsDisabled();
		req.emergencyFundBalanceLimit = account.getEmergencyFundBalanceLimit() == null ?
				null :
				account.getEmergencyFundBalanceLimit().doubleValue();

		return MenigaSDK.executor().updateAccount(req);
	}

	@Override
	public Result<Void> deleteAccount(long accountId) {
		DeleteAccount req = new DeleteAccount();
		req.accountId = accountId;

		return MenigaSDK.executor().deleteAccount(req);
	}

	@Override
	public Result<List<MenigaAccountType>> getAccountTypes() {
		GetAccountTypes req = new GetAccountTypes();
		return MenigaSDK.executor().getAccountTypes(req);
	}

	@Override
	public Result<List<MenigaAuthorizationType>> getAccountAuthorizationTypes() {
		GetAuthorizationTypes req = new GetAuthorizationTypes();
		return MenigaSDK.executor().getAccountAuthorizationTypes(req);
	}

	@Override
	public Result<List<KeyVal<String, String>>> getMetadata(long accountId) {
		GetAccountMetadata req = new GetAccountMetadata();
		req.id = accountId;
		return MenigaSDK.executor().getAccountMetadata(req);
	}

	@Override
	public Result<KeyVal<String, String>> updateMetadata(long accountId, KeyVal<String, String> keyVal) {
		UpdateAccountMetadata req = new UpdateAccountMetadata();
		req.id = accountId;
		req.key = keyVal.getKey();
		req.value = keyVal.getValue();
		return MenigaSDK.executor().updateAccountMetadata(req);
	}

	@Override
	public Result<KeyVal<String, String>> getMetadataKeyVal(long accId, String key) {
		GetAccountMetadataKeyVal req = new GetAccountMetadataKeyVal();
		req.id = accId;
		req.name = key;
		return MenigaSDK.executor().getAccountMetadataKeyVal(req);
	}

	@Override
	public Result<List<MenigaAccountBalanceHistory>> getBalanceHistory(long accId, DateTime from, DateTime to, AccountBalanceHistorySort sort) {
		GetAccountBalanceHistory req = new GetAccountBalanceHistory();
		req.id = accId;
		req.dateFrom = from;
		req.dateTo = to;
		req.sort = sort;
		return MenigaSDK.executor().getAccountBalanceHistory(req);
	}
}
