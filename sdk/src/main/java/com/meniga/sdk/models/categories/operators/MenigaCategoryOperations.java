package com.meniga.sdk.models.categories.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;
import com.meniga.sdk.models.categories.enums.CategoryType;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaCategoryOperations {

	Result<List<MenigaCategory>> getCategories(Boolean publicOnly, String culture);

	Result<MenigaCategory> getCategoryById(long id);

	Result<MenigaUserCategory> createUserCategory(String name, boolean isFixedExpenses, CategoryType categoryType, Long parentId);

	Result<Void> deleteUserCategory(long id);

	Result<Void> updateUserCategory(long id, String name, Boolean isFixedExpenses, CategoryType categoryType, Long parentCategoryId);

	Result<List<MenigaCategory>> getCategoriesTree(Boolean publicOnly);
}
