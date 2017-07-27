package com.meniga.sdk.models.categories.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;
import com.meniga.sdk.models.categories.enums.CategoryType;
import com.meniga.sdk.webservices.requests.CreateUserCategory;
import com.meniga.sdk.webservices.requests.DeleteUserCategory;
import com.meniga.sdk.webservices.requests.GetCategories;
import com.meniga.sdk.webservices.requests.GetCategoryById;
import com.meniga.sdk.webservices.requests.UpdateUserCategory;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCategoryOperationsImp implements MenigaCategoryOperations {

	@Override
	public Result<List<MenigaCategory>> getCategories(Boolean publicOnly, String culture) {
		GetCategories req = new GetCategories();
		req.isPublic = publicOnly;
		req.culture = culture;
		return MenigaSDK.executor().getCategories(req);
	}

	@Override
	public Result<List<MenigaCategory>> getCategoriesTree(Boolean publicOnly) {
		GetCategories req = new GetCategories();
		req.isPublic = publicOnly;
		req.flat = false;
		return MenigaSDK.executor().getCategories(req);
	}

	@Override
	public Result<MenigaCategory> getCategoryById(long id) {
		GetCategoryById req = new GetCategoryById();
		req.categoryId = id;
		return MenigaSDK.executor().getCategoryById(req);
	}

	@Override
	public Result<MenigaUserCategory> createUserCategory(String name, boolean isFixedExpenses, CategoryType categoryType, Long parentId) {
		CreateUserCategory req = new CreateUserCategory();
		req.name = name;
		req.isFixedExpenses = isFixedExpenses;
		req.categoryType = categoryType.ordinal();
		req.parentId = parentId;

		return MenigaSDK.executor().createUserCategory(req);
	}

	@Override
	public Result<Void> deleteUserCategory(long id) {
		DeleteUserCategory req = new DeleteUserCategory();
		req.id = id;
		req.deleteConnectedRules = true;
		req.newCategoryId = null;

		return MenigaSDK.executor().deleteCategory(req);
	}

	@Override
	public Result<Void> updateUserCategory(long id, String name, Boolean isFixedExpenses, CategoryType categoryType, Long parentCategoryId) {
		UpdateUserCategory req = new UpdateUserCategory();
		req.id = id;
		req.name = name;
		req.isFixedExpenses = isFixedExpenses;
		req.categoryType = categoryType.ordinal();
		req.parentId = parentCategoryId;

		return MenigaSDK.executor().updateUserCategory(req);
	}
}
