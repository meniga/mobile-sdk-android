package com.meniga.sdk.webservices;

import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.operators.CreateBudgetParameters;
import com.meniga.sdk.models.budget.operators.UpdateBudgetEntryParameters;
import com.meniga.sdk.webservices.requests.CreateBudgetEntryParameters;
import com.meniga.sdk.webservices.requests.UpdateBudgetParameters;
import com.meniga.sdk.webservices.requests.UpdateBudgetRules;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface BudgetService {

    @GET(APIConst.URL_BUDGETS)
    Call<List<MenigaBudget>> getBudgets(@QueryMap Map<String, String> query);

    @GET(APIConst.URL_BUDGETS)
    Call<MenigaBudget> getBudget(@QueryMap Map<String, String> query);

    @GET(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES)
    Call<List<MenigaBudgetEntry>> getBudgetEntries(@Path("id") String id, @QueryMap Map<String, String> query);

    @POST(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES)
    Call<List<MenigaBudgetEntry>> createBudgetEntry(@Path("id") String id, @Body CreateBudgetEntryParameters parameters);

    @DELETE(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES + "/{entryId}")
    Call<Void> deleteBudgetEntry(@Path("id") String id, @Path("entryId") String entryId);

    @GET(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES + "/{entryId}")
    Call<MenigaBudgetEntry> getBudgetEntry(@Path("id") String id, @Path("entryId") String entryId);

    @PUT(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES + "/{entryId}")
    Call<MenigaBudgetEntry> updateBudgetEntries(@Path("id") String id, @Path("entryId") String entryId, @Body UpdateBudgetEntryParameters updateBudgetEntry);

    @POST(APIConst.URL_BUDGETS)
    Call<MenigaBudget> createBudget(@Body CreateBudgetParameters req);

    @PUT(APIConst.URL_BUDGETS + "/{id}")
    Call<MenigaBudget> updateBudget(@Path("id") String id, @Body UpdateBudgetParameters req);

    @DELETE(APIConst.URL_BUDGETS + "/{id}")
    Call<Void> deleteBudget(@Path("id") String budgetId);

    @POST(APIConst.URL_BUDGETS + "/{id}/reset")
    Call<Void> resetBudget(@Path("id") String budgetId);

    @POST(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_RULES)
    Call<Void> createBudgetRules(@Path("id") String id, @Body UpdateBudgetRules req);
}
