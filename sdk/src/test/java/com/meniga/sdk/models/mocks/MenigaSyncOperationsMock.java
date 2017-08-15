package com.meniga.sdk.models.mocks;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.models.sync.MenigaSyncStatus;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.operators.MenigaSyncOperations;
import com.meniga.sdk.utils.FileImporter;

import java.io.IOException;

public class MenigaSyncOperationsMock implements MenigaSyncOperations {
	@Override
	public Result<MenigaSync> startSync(long timeout) {
		TaskCompletionSource<MenigaSync> task = new TaskCompletionSource<>();
		task.setResult(this.gson());
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<MenigaSyncStatus> getSyncStatus() {
		TaskCompletionSource<MenigaSyncStatus> task = new TaskCompletionSource<>();
		task.setResult(this.gsonStatus());
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<MenigaSync> getSync(long syncHistoryId) {
		TaskCompletionSource<MenigaSync> task = new TaskCompletionSource<>();
		task.setResult(this.gson());
		return new MTask<>(task.getTask(), task);
	}

	private MenigaSync gson() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getJsonFileFromRaw("syncresponse.json")),
					MenigaSync.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MenigaSyncStatus gsonStatus() {
		Gson gson = GsonProvider.getGsonBuilder().create();
		try {
			return gson.fromJson(
					MenigaConverter.getAsObject(FileImporter.getJsonFileFromRaw("syncstatus.json")),
					MenigaSyncStatus.class
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
