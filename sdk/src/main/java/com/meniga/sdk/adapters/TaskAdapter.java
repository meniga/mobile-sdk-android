package com.meniga.sdk.adapters;

import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.providers.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface TaskAdapter {
	<T> Result<T> adapt(Call<T> call, Callback<T> callBack);

	<T> Result<T> adapt(T value);

	<T> Result<T> intercept(Result<T> call, Interceptor<T> intercept);

	<T> Result<T> intercept(Task<T> call, Interceptor<T> intercept);
}
