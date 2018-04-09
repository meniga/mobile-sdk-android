/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.challenge

import com.meniga.sdk.models.challenges.MenigaChallenge
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface ChallengeService {

    @GET("challenges")
    fun getChallenges(@QueryMap query: Map<String, String>): Call<List<MenigaChallenge>>

    @GET("challenges/{id}")
    fun getChallenge(@Path("id") id: String): Call<MenigaChallenge>

    @DELETE("challenges/{id}")
    fun deleteChallenge(@Path("id") id: String): Call<Void>

    @POST("challenges")
    fun createChallenge(@Body req: CreateChallenge): Call<MenigaChallenge>

    @PUT("challenges/{id}")
    fun updateChallenge(@Path("id") id: String, @Body req: UpdateChallenge): Call<Void>

    @POST("challenges/{id}/accept")
    fun acceptChallenge(@Body req: AcceptChallenge, @Path("id") id: String): Call<MenigaChallenge>

    @POST("challenges/{id}/disable")
    fun disableChallenge(@Path("id") id: String): Call<Void>

    @POST("challenges/{id}/enable")
    fun enableChallenge(@Path("id") id: String): Call<Void>

    @GET("challenges/{id}/history")
    fun getChallengeHistory(@Path("id") id: String, @QueryMap query: Map<String, String>): Call<List<MenigaChallenge>>
}
