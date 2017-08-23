package com.meniga.sdk.providers.tasks;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface Continuation<TTaskResult, TContinuationResult> {

	TContinuationResult then(Task<TTaskResult> task) throws Exception;
}
