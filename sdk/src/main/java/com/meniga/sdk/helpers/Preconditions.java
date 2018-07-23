package com.meniga.sdk.helpers;

import javax.annotation.Nullable;

public final class Preconditions {

	public static void checkState(boolean expression, @Nullable String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	private Preconditions() {
		throw new AssertionError("No instances.");
	}
}
