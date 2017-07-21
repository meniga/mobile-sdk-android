package com.meniga.sdk.models.transactions.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.MenigaTag;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaTagOperations {
	Result<List<MenigaTag>> getTags();

	Result<MenigaTag> getTag(long id);
}
