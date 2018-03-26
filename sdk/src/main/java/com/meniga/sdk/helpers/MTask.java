package com.meniga.sdk.helpers;

import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;

import javax.annotation.Nonnull;

import kotlin.jvm.functions.Function1;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MTask<T> implements Result<T> {
	private final Task<T> task;
	private final TaskCompletionSource<T> tcs;

	public MTask(Task<T> taskIn, TaskCompletionSource<T> tcsIn) {
		task = requireNonNull(taskIn);
		tcs = requireNonNull(tcsIn);
	}

	@Nonnull
	@Override
	public Task<T> getTask() {
		return task;
	}

	@Override
	public void cancel() {
		tcs.setCancelled();
	}

	@Nonnull
	@Override
	public <R> Result<R> map(@Nonnull final Function1<? super T, ? extends R> mapper) {
        return new ResultMapper<>(this, mapper);
    }
}
