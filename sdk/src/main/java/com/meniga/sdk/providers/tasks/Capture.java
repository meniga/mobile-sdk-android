package com.meniga.sdk.providers.tasks;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class Capture<T> {
	private T value;

	public Capture() {
	}

	public Capture(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}
}
