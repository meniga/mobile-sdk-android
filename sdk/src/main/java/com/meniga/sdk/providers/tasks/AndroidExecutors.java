package com.meniga.sdk.providers.tasks;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AndroidExecutors {

	private static final AndroidExecutors INSTANCE = new AndroidExecutors();

	private final Executor uiThread;

	private AndroidExecutors() {
		uiThread = new UIThreadExecutor();
	}

	/**
	 * Nexus 5: Quad-Core
	 * Moto X: Dual-Core
	 *
	 * AsyncTask:
	 *   CORE_POOL_SIZE = CPU_COUNT + 1
	 *   MAX_POOL_SIZE = CPU_COUNT * 2 + 1
	 *
	 * https://github.com/android/platform_frameworks_base/commit/719c44e03b97e850a46136ba336d729f5fbd1f47
	 */
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	/* package */ static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	/* package */ static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
	/* package */ static final long KEEP_ALIVE_TIME = 1L;

	/**
	 * Creates a proper Cached Thread Pool. Tasks will reuse cached threads if available
	 * or create new threads until the core pool is full. tasks will then be queued. If an
	 * task cannot be queued, a new thread will be created unless this would exceed max pool
	 * size, then the task will be rejected. Threads will time out after 1 second.
	 *
	 * Core thread timeout is only available on android-9+.
	 *
	 * @return the newly created thread pool
	 */
	public static ExecutorService newCachedThreadPool() {
		ThreadPoolExecutor executor =  new ThreadPoolExecutor(
				CORE_POOL_SIZE,
				MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());

		allowCoreThreadTimeout(executor, true);

		return executor;
	}

	/**
	 * Creates a proper Cached Thread Pool. Tasks will reuse cached threads if available
	 * or create new threads until the core pool is full. tasks will then be queued. If an
	 * task cannot be queued, a new thread will be created unless this would exceed max pool
	 * size, then the task will be rejected. Threads will time out after 1 second.
	 *
	 * Core thread timeout is only available on android-9+.
	 *
	 * @param threadFactory the factory to use when creating new threads
	 * @return the newly created thread pool
	 */
	public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
		ThreadPoolExecutor executor =  new ThreadPoolExecutor(
				CORE_POOL_SIZE,
				MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(),
				threadFactory);

		allowCoreThreadTimeout(executor, true);

		return executor;
	}

	/**
	 * Compatibility helper function for
	 * {@link java.util.concurrent.ThreadPoolExecutor#allowCoreThreadTimeOut(boolean)}
	 *
	 * Only available on android-9+.
	 *
	 * @param executor the {@link java.util.concurrent.ThreadPoolExecutor}
	 * @param value true if should time out, else false
	 */
	@SuppressLint("NewApi")
	public static void allowCoreThreadTimeout(ThreadPoolExecutor executor, boolean value) {
		executor.allowCoreThreadTimeOut(value);
	}

	/**
	 * An {@link java.util.concurrent.Executor} that executes tasks on the UI thread.
	 */
	public static Executor uiThread() {
		return INSTANCE.uiThread;
	}

	/**
	 * An {@link java.util.concurrent.Executor} that runs tasks on the UI thread.
	 */
	private static class UIThreadExecutor implements Executor {
		@Override
		public void execute(Runnable command) {
			new Handler(Looper.getMainLooper()).post(command);
		}
	}
}
