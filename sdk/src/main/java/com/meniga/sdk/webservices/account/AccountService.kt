/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.account

import com.meniga.sdk.helpers.KeyVal
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface AccountService {
    @GET("accounts")
    fun getAccounts(@QueryMap queryMap: Map<String, String>): Call<List<Account>>

    @GET("accounts/{id}")
    fun getAccount(@Path("id") accountId: Long): Call<Account>

    @PUT("accounts/{id}")
    fun updateAccount(@Path("id") accountId: Long, @Body req: UpdateAccount): Call<Void>

    @DELETE("accounts/{id}")
    fun deleteAccount(@Path("id") accountId: Long): Call<Void>

    @GET("accounts/accounttypes")
    fun getAccountTypes(): Call<List<AccountType>>

    @GET("accounts/accountcategories")
    fun getAccountCategories(): Call<List<AccountTypeCategory>>

    @GET("accounts/authorizationtypes")
    fun getAccountAuthorizationTypes(): Call<List<AuthorizationType>>

    @GET("accounts/{id}/metadata")
    fun getAccountMetadata(@Path("id") accountId: Long): Call<List<KeyVal<String, String>>>

    @PUT("accounts/{id}/metadata")
    fun updateAccountMetadata(
            @Path("id") accountId: Long,
            @Body req: UpdateAccountMetadata): Call<KeyVal<String, String>>

    @GET("accounts/{id}/metadata/{name}")
    fun getAccountMetadataKeyVal(
            @Path("id") accountId: Long,
            @Path("name") name: String): Call<KeyVal<String, String>>

    @GET("accounts/{id}/history")
    fun getAccountBalanceHistory(
            @Path("id") accountId: Long,
            @QueryMap query: Map<String, String>): Call<List<AccountBalanceHistory>>
}
