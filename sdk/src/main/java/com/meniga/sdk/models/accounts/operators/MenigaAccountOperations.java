package com.meniga.sdk.models.accounts.operators;

import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountBalanceHistory;
import com.meniga.sdk.models.accounts.MenigaAccountType;
import com.meniga.sdk.models.accounts.MenigaAuthorizationType;
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaAccountOperations {
	Result<MenigaAccount> getAccount(long id);

	Result<List<MenigaAccount>> getAccounts(boolean includeHidden, boolean includeDisabled);

	Result<Void> updateAccount(MenigaAccount menigaAccount);

	Result<Void> deleteAccount(long accountId);

	Result<List<MenigaAccountType>> getAccountTypes();

	Result<List<MenigaAuthorizationType>> getAccountAuthorizationTypes();

	Result<List<KeyVal<String, String>>> getMetadata(long accId);

	Result<KeyVal<String, String>> updateMetadata(long accId, KeyVal<String, String> keyVal);

	Result<KeyVal<String, String>> getMetadataKeyVal(long accId, String key);

	Result<List<MenigaAccountBalanceHistory>> getBalanceHistory(long accId, DateTime from, DateTime to, AccountBalanceHistorySort sort);
}
