package com.meniga.sdk.models.challenges.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaChallengesOperations {

	Result<List<MenigaChallenge>> getChallenges(boolean includeExpired,
	                                                boolean excludeSuggested,
	                                                boolean excludeAccepted);

	Result<MenigaChallenge> acceptChallenge(UUID id, MenigaDecimal targetAmount);

	Result<MenigaChallenge> createChallenge(String title,
	                                            String description,
	                                            DateTime startDate,
	                                            DateTime endDate,
	                                            List<Long> categoryIds,
	                                            MenigaDecimal targetAmount,
	                                            Long iconId,
	                                            CustomChallengeColor color);

	Result<MenigaChallenge> getChallenge(UUID id);

	Result<Void> updateChallenge(MenigaChallenge challenge, boolean updateCategories);

	Result<Void> deleteChallenge(UUID id);
}
