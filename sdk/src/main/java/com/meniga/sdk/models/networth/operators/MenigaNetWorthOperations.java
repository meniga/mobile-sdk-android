package com.meniga.sdk.models.networth.operators;

import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.networth.MenigaNetWorth;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaNetWorthOperations {

	Result<MenigaNetWorth> getNetWorth(long id);

	Result<List<MenigaNetWorth>> getNetWorth(DateTime startDate, DateTime endDate, boolean useInterpolation, int skip, int take);

	Result<MenigaNetWorth> createNetWorthAccount(MenigaDecimal initialBalance, MenigaDecimal balance, String accountIdendefier, String displayName, String networthType, DateTime initialBalanceDate);

	Result<MenigaNetWorthBalance> fetchFirstBalanceEntry(boolean excludeAccountsExcludedFromNetWorth);

	Result<Void> deleteNetWorthAccount(long idOfaccountToDelete);

	Result<Void> updateNetWorthAccount(Long tmpNWUpdateAccountId, Boolean isExcluded, String accountName);

	Result<List<KeyVal<Long, String>>> getNetWorthTypes();
}
