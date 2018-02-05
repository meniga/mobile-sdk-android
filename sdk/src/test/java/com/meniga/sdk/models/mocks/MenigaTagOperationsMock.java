package com.meniga.sdk.models.mocks;

import com.google.gson.Gson;
import com.meniga.sdk.converters.MenigaConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTag;
import com.meniga.sdk.models.transactions.operators.MenigaTagOperations;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.utils.FileImporter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenigaTagOperationsMock implements MenigaTagOperations {
	@Override
	public Result<List<MenigaTag>> getTags() {
		TaskCompletionSource<List<MenigaTag>> task = new TaskCompletionSource<>();
		task.setResult(this.gson());
		return new MTask<>(task.getTask(), task);
	}

	@Override
	public Result<MenigaTag> getTag(long id) {
		TaskCompletionSource<MenigaTag> task = new TaskCompletionSource<>();
		task.setResult(this.gson().get(0));
		return new MTask<>(task.getTask(), task);
	}

	private List<MenigaTag> gson() {
		Gson gson = GsonProvider.getGsonBuilder();
		try {
			return Arrays.asList(gson.fromJson(
					MenigaConverter.getAsArray(FileImporter.getInputStreamFromRaw("tags.json")),
					MenigaTag[].class
			));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
