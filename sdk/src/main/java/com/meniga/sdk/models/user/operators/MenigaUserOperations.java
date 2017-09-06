package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaUser;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaUserOperations {

	Result<List<MenigaUser>> getUsers();

	Result<Void> setCulture(String culture);

	Result<MenigaUser> registerUser(String email, String password, String culture);

    Result<Void> forgotPassword(String email);
}
