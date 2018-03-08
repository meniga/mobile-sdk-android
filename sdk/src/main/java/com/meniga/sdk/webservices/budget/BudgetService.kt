/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.budget

import com.meniga.sdk.models.budget.MenigaBudget
import com.meniga.sdk.models.budget.MenigaBudgetEntry
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface BudgetService {

    @GET("budgets")
    fun getBudgets(@QueryMap query: Map<String, String>): Call<List<MenigaBudget>>

    @GET("budgets/{id}")
    fun getBudget(
            @Path("id") id: String,
            @QueryMap query: Map<String, String>): Call<MenigaBudget>

    @GET("budgets/{id}/entries")
    fun getBudgetEntries(
            @Path("id") id: String,
            @QueryMap query: Map<String, String>): Call<List<MenigaBudgetEntry>>

    @POST("budgets/{id}/entries")
    fun createBudgetEntries(
            @Path("id") id: String,
            @Body parameters: CreateBudgetEntries): Call<List<MenigaBudgetEntry>>

    @DELETE("budgets/{id}/entries/{entryId}")
    fun deleteBudgetEntry(@Path("id") id: String, @Path("entryId") entryId: String): Call<Void>

    @GET("budgets/{id}/entries/{entryId}")
    fun getBudgetEntry(
            @Path("id") id: String,
            @Path("entryId") entryId: String): Call<MenigaBudgetEntry>

    @PUT("budgets/{id}/entries/{entryId}")
    fun updateBudgetEntries(
            @Path("id") id: String,
            @Path("entryId") entryId: String,
            @Body updateBudgetEntry: UpdateBudgetEntry): Call<MenigaBudgetEntry>

    @POST("budgets")
    fun createBudget(@Body req: CreateBudget): Call<MenigaBudget>

    @PUT("budgets/{id}")
    fun updateBudget(@Path("id") id: String, @Body req: UpdateBudget): Call<MenigaBudget>

    @DELETE("budgets/{id}")
    fun deleteBudget(@Path("id") budgetId: String): Call<Void>

    @POST("budgets/{id}/reset")
    fun resetBudget(@Path("id") budgetId: String): Call<Void>

    @POST("budgets/{id}/rules")
    fun createBudgetRules(@Path("id") id: String, @Body req: UpdateBudgetRules): Call<Void>
}
