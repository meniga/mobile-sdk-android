package com.meniga.sdk.models.networth.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;

import org.joda.time.DateTime;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaNetWorthBalanceOperations {

	Result<Void> updateBalance(long accountId, long balanceId, MenigaDecimal amount, DateTime date);

	Result<Void> deleteBalance(long accountId, long balanceId);

	Result<MenigaNetWorthBalance> createNetWorthBalanceHistory(long accountId, MenigaDecimal balance, DateTime balanceDate);
}
