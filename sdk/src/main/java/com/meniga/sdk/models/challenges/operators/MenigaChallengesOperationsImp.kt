/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.challenges.operators

import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.helpers.Result
import com.meniga.sdk.models.challenges.CategoryDefinition
import com.meniga.sdk.models.challenges.FetchChallengeFilter
import com.meniga.sdk.models.challenges.MenigaChallenge
import com.meniga.sdk.models.challenges.NewChallenge
import com.meniga.sdk.webservices.challenge.AcceptChallenge
import com.meniga.sdk.webservices.challenge.CreateChallenge
import com.meniga.sdk.webservices.challenge.DeleteChallenge
import com.meniga.sdk.webservices.challenge.GetChallenge
import com.meniga.sdk.webservices.challenge.GetChallengeHistory
import com.meniga.sdk.webservices.challenge.GetChallenges
import com.meniga.sdk.webservices.challenge.UpdateChallenge
import java.util.UUID

class MenigaChallengesOperationsImp : MenigaChallengesOperations {

    override fun getChallenges(includeExpired: Boolean,
                               excludeSuggested: Boolean,
                               excludeAccepted: Boolean): Result<List<MenigaChallenge>> {
        val req = GetChallenges()
        req.includeExpired = includeExpired
        req.excludeSuggested = excludeSuggested
        req.excludeAccepted = excludeSuggested
        return MenigaSDK.executor().getChallenges(req)
    }

    override fun getChallenges(filter: FetchChallengeFilter): Result<List<MenigaChallenge>> {
        val request = GetChallenges(
                filter.includeExpired,
                filter.excludeSuggested,
                filter.excludeAccepted,
                filter.includeDisabled)
        return MenigaSDK.executor().getChallenges(request)
    }

    override fun getChallenge(id: UUID): Result<MenigaChallenge> {
        val req = GetChallenge(id)
        return MenigaSDK.executor().getChallenge(req)
    }

    override fun acceptChallenge(id: UUID, targetAmount: MenigaDecimal): Result<MenigaChallenge> {
        val req = AcceptChallenge()
        req.id = id
        req.targetAmount = targetAmount
        return MenigaSDK.executor().acceptChallenge(req)
    }

    override fun disableChallenge(id: UUID): Result<Void> {
        return MenigaSDK.executor().disableChallenge(id)
    }

    override fun enableChallenge(id: UUID): Result<Void> {
        return MenigaSDK.executor().enableChallenge(id)
    }

    override fun createChallenge(challenge: NewChallenge): Result<MenigaChallenge> {
        val typeData = CreateChallenge.CreateChallengeTypeData()
        challenge.categoryDefinition.let {
            when (it) {
                is CategoryDefinition.CategoryList -> typeData.categoryIds = it.categoryIds
                is CategoryDefinition.Type -> typeData.categoryType = it.categoryType
            }
        }
        typeData.targetAmount = challenge.targetAmount
        typeData.recurringInterval = challenge.recurringInterval
        if (challenge.customChallengeColor != null) {
            typeData.metaData = "{\"color\": \"" + challenge.customChallengeColor!!.toColorString() + "\"}"
        }

        val req = CreateChallenge(
                challenge.title,
                challenge.description,
                challenge.startDate,
                challenge.endDate,
                challenge.iconUrl,
                typeData
        )
        return MenigaSDK.executor().createChallenge(req)
    }

    override fun updateChallenge(challenge: MenigaChallenge, updateCategoryIds: Boolean): Result<Void> {
        val req = UpdateChallenge()
        req.id = challenge.id

        req.title = challenge.title
        req.description = challenge.description
        req.startDate = challenge.startDate
        req.endDate = challenge.endDate
        req.iconUrl = challenge.iconUrl

        req.typeData = UpdateChallenge.TypeData()
        req.typeData!!.targetAmount = challenge.targetAmount

        if (updateCategoryIds) {
            req.typeData!!.categoryIds = challenge.categoryIds
        }
        if (challenge.customChallengeColor != null) {
            req.typeData!!.metaData = "{\"color\": \"" + challenge.customChallengeColor.toColorString() + "\"}"
        }

        return MenigaSDK.executor().updateChallenge(req)
    }

    override fun deleteChallenge(id: UUID): Result<Void> {
        val req = DeleteChallenge(id)
        return MenigaSDK.executor().deleteChallenge(req)
    }

    override fun getChallengeHistory(id: UUID, page: Int, numPerPage: Int): Result<List<MenigaChallenge>> {
        val skip = if (page == 0) 0 else numPerPage / page
        val req = GetChallengeHistory(id, skip, numPerPage)
        return MenigaSDK.executor().getChallengeHistory(req)
    }
}
