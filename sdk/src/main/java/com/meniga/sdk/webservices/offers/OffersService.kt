/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.offers

import com.meniga.sdk.models.offers.MenigaOffer
import com.meniga.sdk.models.offers.MenigaOfferMerchantLocationPage
import com.meniga.sdk.models.offers.MenigaOfferPage
import com.meniga.sdk.models.offers.MenigaSimilarBrandSpendingDetails
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountPage
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountTypePage
import com.meniga.sdk.webservices.requests.CreateReimbursementAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface OffersService {

    @GET("offers")
    fun getOffers(@QueryMap queryMap: Map<String, String>): Call<MenigaOfferPage>

    @GET("offers/{id}")
    fun getOffer(@Path("id") id: Long): Call<MenigaOffer>

    @GET("offers/{token}")
    fun getOffer(@Path("token") token: String): Call<MenigaOffer>

    @POST("offers/{id}/activate")
    fun activateOfferById(@Path("id") id: Long): Call<Void>

    @POST("offers/{token}/activate")
    fun activateOfferByToken(@Path("token") token: String): Call<Void>

    @DELETE("offers/{id}/activate")
    fun declineOffer(@Path("id") id: Long): Call<Void>

    @POST("offers/{id}/seen")
    fun markOfferAsSeen(@Path("id") id: Long): Call<Void>

    @POST("offers/acceptTermsAndConditions")
    fun acceptTermsAndConditions(): Call<Void>

    @POST("offers/enable")
    fun enableOffers(): Call<Void>

    @DELETE("offers/enable")
    fun disableOffers(): Call<Void>

    @GET("offers/{id}/similarBrandSpending")
    fun getSimilarBrandSpeningDetails(@Path("id") id: Long): Call<MenigaSimilarBrandSpendingDetails>

    @GET("offers/{id}/redemptions")
    fun getRedemptionsByOfferId(@Path("id") id: Long): Call<MenigaRedemptions>

    @GET("offers/{id}/merchantLocations")
    fun getMerchantLocationByOfferId(
            @Path("id") id: Long,
            @QueryMap queryMap: Map<String, String>): Call<MenigaOfferMerchantLocationPage>

    @GET("redemptions")
    fun getRedemptions(@QueryMap stringStringMap: Map<String, String>): Call<MenigaRedemptions>

    @GET("reimbursementAccounts")
    fun getReimbursementAccounts(@QueryMap stringStringMap: Map<String, String>): Call<MenigaReimbursementAccountPage>

    @POST("reimbursementAccounts")
    fun addReimbursementAccount(@Body req: CreateReimbursementAccount): Call<MenigaReimbursementAccount>

    @GET("reimbursementAccounts/{id}")
    fun getReimbursementAccountById(@Path("id") id: Int): Call<MenigaReimbursementAccount>

    @GET("reimbursementAccounts/types")
    fun getReimbursementAccountTypes(@QueryMap stringStringMap: Map<String, String>): Call<MenigaReimbursementAccountTypePage>
}
