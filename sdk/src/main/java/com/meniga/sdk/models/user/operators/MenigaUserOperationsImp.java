package com.meniga.sdk.models.user.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.MenigaUser;
import com.meniga.sdk.models.user.MenigaUserMetaData;
import com.meniga.sdk.webservices.requests.ForgotPassword;
import com.meniga.sdk.webservices.requests.GetUserMetaData;
import com.meniga.sdk.webservices.requests.GetUsers;
import com.meniga.sdk.webservices.requests.RegisterUser;
import com.meniga.sdk.webservices.requests.ResetPasswordWithToken;
import com.meniga.sdk.webservices.requests.SaveMetaData;
import com.meniga.sdk.webservices.requests.SetCulture;
import com.meniga.sdk.webservices.user.ChangePassword;
import com.meniga.sdk.webservices.user.UpdateEmail;

import org.jetbrains.annotations.NotNull;

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

	@Override
	public Result<Void> forgotPassword(String email) {
		ForgotPassword req = new ForgotPassword();
		req.email = email;
		return MenigaSDK.executor().forgotPassword(req);
	}

	@Override
	public Result<List<MenigaUserMetaData>> getUserMetaData(List<String> filter) {
		GetUserMetaData req = new GetUserMetaData();
		req.names = filter;
		return MenigaSDK.executor().getUserMetaData(req);
	}

	@Override
	public Result<MenigaUserMetaData> saveMetaData(String key, String value) {
		SaveMetaData req = new SaveMetaData(key, value);
		return MenigaSDK.executor().saveUserMetaData(req);
	}

	@Override
	public Result<Void> resetPassword(String resetPasswordToken, String email, String newPassword) {
		ResetPasswordWithToken req = new ResetPasswordWithToken(resetPasswordToken, email, newPassword);
		return MenigaSDK.executor().resetPassword(req);
	}

	@Override
	public Result<Void> updateEmail(@NotNull String newEmail, @NotNull String password) {
		UpdateEmail updateEmail = new UpdateEmail(newEmail, password);
		return MenigaSDK.executor().updateEmail(updateEmail);
	}

	@Override
	public Result<Void> changePassword(@NotNull String currentPassword, @NotNull String newPassword) {
		ChangePassword changePassword = new ChangePassword(currentPassword, newPassword);
		return MenigaSDK.executor().changePassword(changePassword);
	}

	@Override
	public Result<Void> delete() {
		return MenigaSDK.executor().deleteUser();
	}
}
