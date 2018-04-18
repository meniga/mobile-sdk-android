/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.challenges.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.challenges.FetchChallengeFilter;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.challenges.NewChallenge;
import com.meniga.sdk.models.challenges.enums.ChallengeInterval;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public interface MenigaChallengesOperations {
	Result<List<MenigaChallenge>> getChallenges(FetchChallengeFilter filter);

	Result<MenigaChallenge> acceptChallenge(UUID id, MenigaDecimal targetAmount);

	Result<Void> disableChallenge(UUID id);

	Result<Void> enableChallenge(UUID id);

	Result<MenigaChallenge> createChallenge(NewChallenge challenge);

	Result<MenigaChallenge> getChallenge(UUID id);

	Result<Void> updateChallenge(MenigaChallenge challenge, boolean updateCategories);

	Result<Void> deleteChallenge(UUID id);

    Result<List<MenigaChallenge>> getChallengeHistory(UUID id, int page, int numPerPage);
}
