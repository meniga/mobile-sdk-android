package com.meniga.sdk;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

import android.util.Log;

public class ErrorHandler {
	public static void reportAndHandle(Throwable ex) {
		if (ex == null) {
			return;
		}
		if(MenigaSDK.getMenigaSettings() != null && MenigaSDK.getMenigaSettings().getErrorHandler() != null) {
			MenigaSDK.getMenigaSettings().getErrorHandler().reportAndHandle(ex);
		} else {
			if (ErrorHandler.isDebuggable()) {
				Log.e("Meniga", ex.getMessage() + "\n" + Log.getStackTraceString(ex));
			}
		}
	}

	private static boolean isDebuggable() {
		return true;
	}
}
