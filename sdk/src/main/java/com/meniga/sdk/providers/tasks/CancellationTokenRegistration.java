package com.meniga.sdk.providers.tasks;

import java.io.Closeable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * <p>
 * Represents a callback delegate that has been registered with a {@link CancellationToken}.
 *
 * @see CancellationToken#register(Runnable)
 */
public class CancellationTokenRegistration implements Closeable {

	private final Object lock = new Object();
	private CancellationTokenSource tokenSource;
	private Runnable action;
	private boolean closed;

	/* package */ CancellationTokenRegistration(CancellationTokenSource tokenSource, Runnable action) {
		this.tokenSource = tokenSource;
		this.action = action;
	}

	/**
	 * Unregisters the callback runnable from the cancellation token.
	 */
	@Override
	public void close() {
		synchronized (lock) {
			if (closed) {
				return;
			}

			closed = true;
			tokenSource.unregister(this);
			tokenSource = null;
			action = null;
		}
	}

	/* package */ void runAction() {
		synchronized (lock) {
			throwIfClosed();
			action.run();
			close();
		}
	}

	private void throwIfClosed() {
		if (closed) {
			throw new IllegalStateException("Object already closed");
		}
	}

}
