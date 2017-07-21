package com.meniga.sdk.models.offers.reimbursementaccounts.operators;

import com.google.gson.Gson;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfo;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountPage;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountTypePage;
import com.meniga.sdk.webservices.requests.CreateReimbursementAccount;
import com.meniga.sdk.webservices.requests.GetReimbursementAccountById;
import com.meniga.sdk.webservices.requests.GetReimbursementAccountTypes;
import com.meniga.sdk.webservices.requests.GetReimbursementAccounts;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaReimbursementAccountOperationsImp implements MenigaReimbursementAccountOperations {

	@Override
    public Result<MenigaReimbursementAccount> createOfferAccount(String name, String accountType, MenigaOfferAccountInfo accountInfo) {
        CreateReimbursementAccount req = new CreateReimbursementAccount();

	    Gson gson = new Gson();

        req.name = name;
        req.accountType = accountType;
        req.accountInfo = gson.toJson(accountInfo);

        return MenigaSDK.executor().addReimbursementAccount(req);
    }

    @Override
    public Result<MenigaReimbursementAccountPage> getReimbursementAccounts(Boolean includeInactive) {
        GetReimbursementAccounts req = new GetReimbursementAccounts();
        req.includeInactive = includeInactive;
        return MenigaSDK.executor().getReimbursementAccounts(req);
    }

    @Override
    public Result<MenigaReimbursementAccountTypePage> getReimbursementAccountTypes(Integer skip, Integer take) {
        GetReimbursementAccountTypes req = new GetReimbursementAccountTypes();
        req.skip = skip;
        req.take = take;
        return MenigaSDK.executor().getReimbursementAccountTypes(req);
    }

    @Override
    public Result<MenigaReimbursementAccount> getReimbursementAccountById(int id) {
        GetReimbursementAccountById req = new GetReimbursementAccountById(id);
        return MenigaSDK.executor().getReimbursementAccountById(req);
    }
}
