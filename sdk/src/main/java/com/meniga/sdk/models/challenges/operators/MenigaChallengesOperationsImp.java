package com.meniga.sdk.models.challenges.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;
import com.meniga.sdk.webservices.requests.AcceptChallenge;
import com.meniga.sdk.webservices.requests.CreateChallenge;
import com.meniga.sdk.webservices.requests.CreateChallengeTypeData;
import com.meniga.sdk.webservices.requests.DeleteChallenge;
import com.meniga.sdk.webservices.requests.GetChallenge;
import com.meniga.sdk.webservices.requests.GetChallenges;
import com.meniga.sdk.webservices.requests.UpdateChallenge;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaChallengesOperationsImp implements MenigaChallengesOperations {

	@Override
	public Result<List<MenigaChallenge>> getChallenges(boolean includeExpired,
	                                                   boolean excludeSuggested,
	                                                   boolean excludeAccepted) {
		GetChallenges req = new GetChallenges();
		req.includeExpired = includeExpired;
		req.excludeSuggested = excludeSuggested;
		req.excludeAccepted = excludeSuggested;
		return MenigaSDK.executor().getChallenges(req);
	}

	@Override
	public Result<MenigaChallenge> getChallenge(UUID id) {
		GetChallenge req = new GetChallenge();
		req.id = id;

		return MenigaSDK.executor().getChallenge(req);
	}

	@Override
	public Result<MenigaChallenge> acceptChallenge(UUID id, MenigaDecimal targetAmount) {
		AcceptChallenge req = new AcceptChallenge();
		req.id = id;
		req.targetAmount = targetAmount;
		return MenigaSDK.executor().acceptChallenge(req);
	}

	@Override
	public Result<MenigaChallenge> createChallenge(String title, String description,
	                                                   DateTime startDate, DateTime endDate,
	                                                   List<Long> categoryIds, MenigaDecimal targetAmount,
	                                                   Long iconId, CustomChallengeColor color) {
		CreateChallenge req = new CreateChallenge();
		req.title = title;
		req.description = description;
		req.startDate = startDate;
		req.endDate = endDate;
		req.typeData = new CreateChallengeTypeData();
		req.typeData.categoryIds = categoryIds;
		req.typeData.targetAmount = targetAmount;
		req.iconId = iconId;
		if (color != null) {
			req.typeData.metaData = "{\"color\": \"" + color.toColorString() + "\"}";
		}

		return MenigaSDK.executor().createChallenge(req);
	}

	@Override
	public Result<Void> updateChallenge(MenigaChallenge challenge, boolean updateCategoryIds) {
		UpdateChallenge req = new UpdateChallenge();
		req.id = challenge.getId();

		/*req.title = challenge.getTitle();
		req.description = challenge.getDescription();
		req.startDate = challenge.getStartDate();
		req.endDate = challenge.getEndDate();
		req.iconUrl = challenge.getIconUrl();*/

		req.typeData = new UpdateChallenge.TypeData();
		req.typeData.targetAmount = challenge.getTargetAmount();

		if (updateCategoryIds) {
			//req.typeData.categoryIds = challenge.getCategoryIds();
		}
		if (challenge.getCustomChallengeColor() != null) {
			//req.typeData.metaData = "{\"color\": \"" + challenge.getCustomChallengeColor().toColorString() + "\"}";
		}

		return MenigaSDK.executor().updateChallenge(req);
	}

	@Override
	public Result<Void> deleteChallenge(UUID id) {
		DeleteChallenge req = new DeleteChallenge();
		req.id = id;
		return MenigaSDK.executor().deleteChallenge(req);
	}
}
