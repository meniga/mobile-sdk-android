package com.meniga.sdk.helpers;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public abstract class Interceptor<T> {

	protected T data;

	public abstract void onFinished(T result, boolean failed);
}
