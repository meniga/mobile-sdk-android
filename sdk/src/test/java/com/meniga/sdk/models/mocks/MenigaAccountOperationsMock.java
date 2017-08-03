package com.meniga.sdk.models.mocks;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountBalanceHistory;
import com.meniga.sdk.models.accounts.MenigaAccountType;
import com.meniga.sdk.models.accounts.MenigaAuthorizationType;
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.utils.FileImporter;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccountOperationsMock implements MenigaAccountOperations {

	private static List<MenigaAccount> gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return Arrays.asList(
					gson.fromJson(
							MenigaConverter.getAsArray(FileImporter.getJsonFileFromRaw("accounts.json")),
							MenigaAccount[].class
					)
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Result<MenigaAccount> getAccount(long id) {
		TaskCompletionSource<MenigaAccount> task = new TaskCompletionSource<>();
		task.setResult(gson().get(0));
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaAccount>> getAccounts(boolean b, boolean b1) {
		TaskCompletionSource<List<MenigaAccount>> task = new TaskCompletionSource<>();
		task.setResult(gson());
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> updateAccount(MenigaAccount menigaAccount) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<Void> deleteAccount(long accountId) {
		TaskCompletionSource<Void> task = new TaskCompletionSource<>();
		task.setResult(null);
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<List<MenigaAccountType>> getAccountTypes() {
		return null;
	}

	@Override
	public Result<List<MenigaAuthorizationType>> getAccountAuthorizationTypes() {
		return null;
	}

	@Override
	public Result<List<KeyVal<String, String>>> getMetadata(long accId) {
		return null;
	}

	@Override
	public Result<KeyVal<String, String>> updateMetadata(long accId, KeyVal<String, String> keyVal) {
		return null;
	}

	@Override
	public Result<KeyVal<String, String>> getMetadataKeyVal(long accId, String key) {
		return null;
	}

	@Override
	public Result<List<MenigaAccountBalanceHistory>> getBalanceHistory(long accId, DateTime from, DateTime to, AccountBalanceHistorySort sort) {
		return null;
	}
}
