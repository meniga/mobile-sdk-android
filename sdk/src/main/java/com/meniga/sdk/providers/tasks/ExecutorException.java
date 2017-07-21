package com.meniga.sdk.providers.tasks;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
class ExecutorException extends RuntimeException {

	ExecutorException(Exception e) {
		super("An exception was thrown by an Executor", e);
	}
}
