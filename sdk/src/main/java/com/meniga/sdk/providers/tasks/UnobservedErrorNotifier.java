package com.meniga.sdk.providers.tasks;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
class UnobservedErrorNotifier {
	private Task<?> task;

	UnobservedErrorNotifier(Task<?> task) {
		this.task = task;
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			Task faultedTask = this.task;
			if (faultedTask != null) {
				Task.UnobservedExceptionHandler ueh = Task.getUnobservedExceptionHandler();
				if (ueh != null) {
					ueh.unobservedException(faultedTask, new UnobservedTaskException(faultedTask.getError()));
				}
			}
		} finally {
			super.finalize();
		}
	}

	void setObserved() {
		task = null;
	}
}
