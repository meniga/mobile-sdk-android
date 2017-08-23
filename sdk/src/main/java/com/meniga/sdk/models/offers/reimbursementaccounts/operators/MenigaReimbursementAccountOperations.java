package com.meniga.sdk.models.offers.reimbursementaccounts.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfo;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountPage;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountTypePage;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaReimbursementAccountOperations {
	Result<MenigaReimbursementAccount> createOfferAccount(String name, String accountType, MenigaOfferAccountInfo accountInfo);

	Result<MenigaReimbursementAccountPage> getReimbursementAccounts(Boolean includeInactive);

	Result<MenigaReimbursementAccountTypePage> getReimbursementAccountTypes(Integer skip, Integer take);

	Result<MenigaReimbursementAccount> getReimbursementAccountById(int id);
}
