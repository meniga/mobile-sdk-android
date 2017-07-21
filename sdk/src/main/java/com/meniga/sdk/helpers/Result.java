package com.meniga.sdk.helpers;

import com.meniga.sdk.providers.tasks.Task;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface Result<T> {
	Task<T> getTask();

	void cancel();
}
