/*
 * Copyright 2019 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.user

import com.meniga.sdk.models.user.MenigaUser
import com.meniga.sdk.models.user.MenigaUserMetaData
import com.meniga.sdk.models.user.MenigaUserProfile
import com.meniga.sdk.webservices.requests.ForgotPassword
import com.meniga.sdk.webservices.requests.RegisterUser
import com.meniga.sdk.webservices.requests.ResetPasswordWithToken
import com.meniga.sdk.webservices.requests.SaveMetaData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap

internal interface UsersService {

    // TODO Download data as zip file, url then?

    @DELETE("me")
    fun delete(): Call<Void>

    @GET("me/profile")
    fun getUserProfile(): Call<MenigaUserProfile>

    @GET("me?includeAll=true")
    fun getUsers(): Call<List<MenigaUser>>

    @PUT("me/culture")
    fun setCulture(@QueryMap queryMap: Map<String, String>): Call<Void>

    @POST("me/register")
    fun registerUser(@Body newUser: RegisterUser): Call<MenigaUser>

    @PUT("me/email")
    fun updateEmail(@Body updateEmail: UpdateEmail): Call<Void>

    @PUT("me/password")
    fun changePassword(@Body updateEmail: ChangePassword): Call<Void>

    @POST("me/password/forgot")
    fun forgotPassword(@Body req: ForgotPassword): Call<Void>

    @GET("me/metadata")
    fun getUserMetaData(@QueryMap queryMap: Map<String, String>): Call<List<MenigaUserMetaData>>

    @PUT("me/metadata")
    fun saveUserMetaData(@Body metaData: SaveMetaData): Call<MenigaUserMetaData>

    @POST("me/password/reset")
    fun resetPassword(@Body resetPasswordWithToken: ResetPasswordWithToken): Call<Void>
}
