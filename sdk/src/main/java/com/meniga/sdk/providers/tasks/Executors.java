package com.meniga.sdk.providers.tasks;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
class Executors {

	private static final Executors INSTANCE = new Executors();

	private static boolean isAndroidRuntime() {
		String javaRuntimeName = System.getProperty("java.runtime.name");
		if (javaRuntimeName == null) {
			return false;
		}
		return javaRuntimeName.toLowerCase(Locale.US).contains("android");
	}

	private final ExecutorService background;
	private final ScheduledExecutorService scheduled;
	private final Executor immediate;

	private Executors() {
		background = !isAndroidRuntime()
				? java.util.concurrent.Executors.newCachedThreadPool()
				: AndroidExecutors.newCachedThreadPool();
		scheduled = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
		immediate = new ImmediateExecutor();
	}

	/**
	 * An {@link java.util.concurrent.Executor} that executes tasks in parallel.
	 */
	public static ExecutorService background() {
		return INSTANCE.background;
	}

	/* package */
	static ScheduledExecutorService scheduled() {
		return INSTANCE.scheduled;
	}

	/**
	 * An {@link java.util.concurrent.Executor} that executes tasks in the current thread unless
	 * the stack runs too deep, at which point it will delegate to {@link Executors#background}
	 * in order to trim the stack.
	 */
  /* package */
	static Executor immediate() {
		return INSTANCE.immediate;
	}

	/**
	 * An {@link java.util.concurrent.Executor} that runs a runnable inline (rather than scheduling it
	 * on a thread pool) as long as the recursion depth is less than MAX_DEPTH. If the executor has
	 * recursed too deeply, it will instead delegate to the {@link Task#BACKGROUND_EXECUTOR} in order
	 * to trim the stack.
	 */
	private static class ImmediateExecutor implements Executor {

		private static final int MAX_DEPTH = 15;
		private ThreadLocal<Integer> executionDepth = new ThreadLocal<>();

		/**
		 * Increments the depth.
		 *
		 * @return the new depth value.
		 */
		private int incrementDepth() {
			Integer oldDepth = executionDepth.get();
			if (oldDepth == null) {
				oldDepth = 0;
			}
			int newDepth = oldDepth + 1;
			executionDepth.set(newDepth);
			return newDepth;
		}

		/**
		 * Decrements the depth.
		 *
		 * @return the new depth value.
		 */
		private int decrementDepth() {
			Integer oldDepth = executionDepth.get();
			if (oldDepth == null) {
				oldDepth = 0;
			}
			int newDepth = oldDepth - 1;
			if (newDepth == 0) {
				executionDepth.remove();
			} else {
				executionDepth.set(newDepth);
			}
			return newDepth;
		}

		@Override
		public void execute(Runnable command) {
			int depth = incrementDepth();
			try {
				if (depth <= MAX_DEPTH) {
					command.run();
				} else {
					Executors.background().execute(command);
				}
			} finally {
				decrementDepth();
			}
		}
	}
}
