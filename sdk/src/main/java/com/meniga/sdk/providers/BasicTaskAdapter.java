package com.meniga.sdk.providers;

import com.meniga.sdk.adapters.TaskAdapter;

import kotlin.jvm.functions.Function1;
import retrofit2.Call;

import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.helpers.ResultMapper;
import com.meniga.sdk.providers.tasks.Continuation;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.webservices.MenigaWebException;

import org.jetbrains.annotations.NotNull;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class BasicTaskAdapter implements TaskAdapter {
	@Override
	public <T> Result<T> adapt(Call<T> call, Callback<T> callBack) {
		if (callBack == null) {
			callBack = new Callback<T>() {
				@Override
				public void onResponse(Call<T> call, Response<T> response) {
				}

				@Override
				public void onFailure(Call<T> call, Throwable t) {
				}
			};
		}
		final Callback<T> finalCall = callBack;
		final TaskCompletionSource<T> tcs = new TaskCompletionSource<>(call);
		call.enqueue(new Callback<T>() {
			@Override
			public void onResponse(Call<T> call, Response<T> response) {
				if (call.isCanceled()) {
					tcs.setCancelled();
				} else if (response.isSuccessful()) {
					tcs.setResult(response.body());
					finalCall.onResponse(call, response);
				} else {
					ErrorHandler.reportAndHandle(new Exception(response.message()));
					String msg = response.message();
					try {
						msg = response.errorBody().string();
					} catch (Exception ex) {
						// Do not crash
					}
					tcs.setError(new MenigaWebException(msg, response.code()));
				}
			}

			@Override
			public void onFailure(Call<T> call, Throwable error) {
				ErrorHandler.reportAndHandle(error);
				if (error == null && !tcs.getTask().isCancelled()) {
					tcs.setError(new Exception("[No error message provided]"));
				} else {
					if (call.isCanceled() && !tcs.getTask().isCompleted()) {
						tcs.setCancelled();
					} else if (!tcs.getTask().isCompleted()) {
						if (error != null) {
							tcs.setError(new MenigaWebException(error.getMessage(), -1));
						} else {
							tcs.setError(new MenigaWebException("[No error message provided]", -1));
						}
					}
				}
			}
		});

		return new MTask<>(tcs.getTask(), tcs);
	}

	@Override
	public <T> MTask<T> adapt(T value) {
		final TaskCompletionSource<T> tcs = new TaskCompletionSource<>(null);
		tcs.trySetResult(value);
		return new MTask<>(tcs.getTask(), tcs);
	}

	@Override
	public <T> Result<T> intercept(Result<T> request, final Interceptor<T> intercept) {
		final TaskCompletionSource<T> tcs = new TaskCompletionSource<>();
		final Task<T> res = request.getTask();
		final Result<T> finalRequest = request;
		res.continueWithTask(new Continuation<T, Task<T>>() {
			@Override
			public Task<T> then(Task<T> task) throws Exception {
				if (!task.isCancelled()) {
					if (task.isFaulted()) {
						tcs.setError(task.getError());
					} else {
						tcs.setResult(task.getResult());
					}
					intercept.onFinished(task.getResult(), task.isFaulted());
				}
				return tcs.getTask();
			}
		});

		return new Result<T>() {
			@Override
			public Task<T> getTask() {
				return res;
			}

			@Override
			public void cancel() {
				finalRequest.cancel();
			}

			@NotNull
			@Override
			public <R> Result<R> map(@NotNull Function1<? super T, ? extends R> mapper) {
				return finalRequest.map(mapper);
			}
		};
	}

	@Override
	public <T> Result<T> intercept(final Task<T> task, Interceptor<T> intercept) {
		final Result<T> result = new Result<T>() {

			@Override
			public Task<T> getTask() {
				return task;
			}

			@Override
			public void cancel() {
				// Not supported when intercepting with tasks
			}

			@NotNull
			@Override
			public <R> Result<R> map(@NotNull Function1<? super T, ? extends R> mapper) {
				return new ResultMapper<>(this, mapper);
			}
		};
		return intercept(result, intercept);
	}
}
