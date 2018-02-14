package com.meniga.sdk.providers.tasks;

import android.util.Log;

import com.meniga.sdk.webservices.MenigaWebException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class TaskCompletionSource<TResult> {

	private final Task<TResult> task;
	private final Call<TResult> call;

	/**
	 * Creates a TaskCompletionSource that orchestrates a Task. This allows the creator of a task to
	 * be solely responsible for its completion.
	 */
	public TaskCompletionSource() {
		task = new Task<>("");
		call = null;
	}

	/**
	 * Creates a TaskCompletionSource that orchestrates a Task. This allows the creator of a task to
	 * be solely responsible for its completion.
	 */
	public TaskCompletionSource(Call<TResult> callIn) {
		task = new Task<>(callIn == null ? "" : callIn.request().url().toString());
		call = callIn;
	}

	/**
	 * @return the Task associated with this TaskCompletionSource.
	 */
	public Task<TResult> getTask() {
		return task;
	}

	/**
	 * Sets the cancelled flag on the Task if the Task hasn't already been completed.
	 */
	boolean trySetCancelled() {
		if (call != null) {
			try {
				call.cancel();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return task.trySetCancelled();
	}

	/**
	 * Sets the result on the Task if the Task hasn't already been completed.
	 */
	public boolean trySetResult(TResult result) {
		return task.trySetResult(result);
	}

	/**
	 * Sets the error on the Task if the Task hasn't already been completed.
	 */
	private boolean trySetError(Exception error) {
		return task.trySetError(error);
	}

	/**
	 * Sets the cancelled flag on the task, throwing if the Task has already been completed.
	 */
	public void setCancelled() {
		if (!trySetCancelled()) {
			Log.e("Meniga", "Cannot cancel a completed task.");
		}
	}

	/**
	 * Sets the result of the Task, throwing if the Task has already been completed.
	 */
	public void setResult(TResult result) {
		if (!trySetResult(result)) {
			Log.e("Meniga", "Cannot set the result of a completed task.");
		}
	}

	/**
	 * Sets the error of the Task, throwing if the Task has already been completed.
	 */
	public void setError(Exception error) {
		if (!trySetError(error)) {
			Log.e("Meniga", "Cannot set the error on a completed task.");
		}
	}

	public <E> Task<E> createTask(Call<E> call) {
		final TaskCompletionSource<E> src = new TaskCompletionSource<>(call);
		final Task<E> task = src.getTask();

		call.enqueue(new Callback<E>() {
			@Override
			public void onResponse(Call<E> call, Response<E> response) {
				if (call.isCanceled()) {
					setCancelled();
				} else if (response.isSuccessful()) {
					src.setResult(response.body());
				} else {
					setError(new MenigaWebException(response.message(), response.code()));
				}
			}

			@Override
			public void onFailure(Call<E> call, Throwable t) {
				if (t == null && !task.isCancelled()) {
					setError(new Exception("[No error message provided]"));
				} else {
					if (call.isCanceled() && !task.isCompleted()) {
						setCancelled();
					} else if (!task.isCompleted() && t != null) {
						setError(new MenigaWebException(t.getMessage(), -1));
					}
				}
			}
		});

		return task;
	}
}
