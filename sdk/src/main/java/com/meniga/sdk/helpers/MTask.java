package com.meniga.sdk.helpers;

import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MTask<T> implements Result<T> {

	private final Task<T> task;
	private final TaskCompletionSource<T> tcs;

	public MTask(Task<T> taskIn, TaskCompletionSource<T> tcsIn) {
		task = taskIn;
		tcs = tcsIn;
	}

	@Override
	public Task<T> getTask() {
		return task;
	}

	@Override
	public void cancel() {
		tcs.setCancelled();
	}
}
