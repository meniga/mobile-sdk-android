package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaUser;
import com.meniga.sdk.webservices.requests.GetUsers;
import com.meniga.sdk.webservices.requests.RegisterUser;
import com.meniga.sdk.webservices.requests.SetCulture;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUserOperationsImp implements MenigaUserOperations {

	@Override
	public Result<List<MenigaUser>> getUsers() {
		return MenigaSDK.executor().getUsers(new GetUsers());
	}

	@Override
	public Result<Void> setCulture(String culture) {
		SetCulture setCulture = new SetCulture();
		setCulture.culture = culture;
		return MenigaSDK.executor().setCulture(setCulture);
	}

	@Override
	public Result<MenigaUser> registerUser(String email, String password, String culture) {
		RegisterUser register = new RegisterUser();
		register.email = email;
		register.password = password;
		register.culture = culture;
		return MenigaSDK.executor().registerUser(register);
	}
}
