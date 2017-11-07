package com.meniga.sdk.webservices;

import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.MenigaAccountBalanceHistory;
import com.meniga.sdk.models.accounts.MenigaAccountType;
import com.meniga.sdk.models.accounts.MenigaAuthorizationType;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.operators.CreateBudget;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.merchants.MenigaMerchant;
import com.meniga.sdk.models.merchants.MenigaTopMerchant;
import com.meniga.sdk.models.networth.MenigaNetWorth;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;
import com.meniga.sdk.models.offers.MenigaOffer;
import com.meniga.sdk.models.offers.MenigaOfferMerchantLocationPage;
import com.meniga.sdk.models.offers.MenigaOfferPage;
import com.meniga.sdk.models.offers.MenigaSimilarBrandSpendingDetails;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountPage;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountTypePage;
import com.meniga.sdk.models.organizations.MenigaOrganization;
import com.meniga.sdk.models.organizations.MenigaRealmAccount;
import com.meniga.sdk.models.organizations.MenigaRealmAuthResponse;
import com.meniga.sdk.models.serverpublic.MenigaPublicSettings;
import com.meniga.sdk.models.terms.MenigaTermType;
import com.meniga.sdk.models.terms.MenigaTerms;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.models.transactions.MenigaTag;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.upcoming.MenigaUpcoming;
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.MenigaSyncStatus;
import com.meniga.sdk.models.user.MenigaUser;
import com.meniga.sdk.models.user.MenigaUserProfile;
import com.meniga.sdk.models.userevents.MenigaUserEvent;
import com.meniga.sdk.webservices.requests.AcceptChallenge;
import com.meniga.sdk.webservices.requests.CreateChallenge;
import com.meniga.sdk.webservices.requests.CreateComment;
import com.meniga.sdk.webservices.requests.CreateNetWorthAccount;
import com.meniga.sdk.webservices.requests.CreateNetWorthBalanceHistory;
import com.meniga.sdk.webservices.requests.CreateReimbursementAccount;
import com.meniga.sdk.webservices.requests.CreateTransaction;
import com.meniga.sdk.webservices.requests.CreateTransactionRule;
import com.meniga.sdk.webservices.requests.CreateUpcoming;
import com.meniga.sdk.webservices.requests.CreateUserCategory;
import com.meniga.sdk.webservices.requests.ForgotPassword;
import com.meniga.sdk.webservices.requests.GetRealmAuthMethod;
import com.meniga.sdk.webservices.requests.GetTopMerchants;
import com.meniga.sdk.webservices.requests.GetTransactionSeries;
import com.meniga.sdk.webservices.requests.RecategorizeTransactions;
import com.meniga.sdk.webservices.requests.RegisterUser;
import com.meniga.sdk.webservices.requests.SetSubscription;
import com.meniga.sdk.webservices.requests.SetSubscriptionSettings;
import com.meniga.sdk.webservices.requests.SplitTransaction;
import com.meniga.sdk.webservices.requests.StartSync;
import com.meniga.sdk.webservices.requests.UpdateAccount;
import com.meniga.sdk.webservices.requests.UpdateAccountMetadata;
import com.meniga.sdk.webservices.requests.UpdateBudget;
import com.meniga.sdk.webservices.requests.UpdateChallenge;
import com.meniga.sdk.webservices.requests.UpdateComment;
import com.meniga.sdk.webservices.requests.UpdateHistoryBalance;
import com.meniga.sdk.webservices.requests.UpdateSplits;
import com.meniga.sdk.webservices.requests.UpdateTransaction;
import com.meniga.sdk.webservices.requests.UpdateTransactionRule;
import com.meniga.sdk.webservices.requests.UpdateTransactions;
import com.meniga.sdk.webservices.requests.UpdateUpcoming;
import com.meniga.sdk.webservices.requests.UpdateUserCategory;
import com.meniga.sdk.webservices.requests.UpdatedNetWorthAccount;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.OPTIONS;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaAPI {

	// --
	// Merchant
	// --
	@GET(APIConst.URL_MERCHANT + "/{id}")
	Call<MenigaMerchant> getMerchant(@Path("id") long id);

	@GET(APIConst.URL_MERCHANT)
	Call<List<MenigaMerchant>> getMerchants(@QueryMap Map<String, String> queryMap);

	@POST(APIConst.URL_TOP_MERCHANT)
	Call<List<MenigaTopMerchant>> getTopMerchants(@Body GetTopMerchants req);

	// --
	// Transactions
	// --
	@DELETE(APIConst.URL_TRANSACTIONS)
	Call<Void> deleteTransactions(@QueryMap Map<String, String> queryMap);

	@GET(APIConst.URL_TRANSACTIONS)
	Call<MenigaTransactionPage> getTransactions(@QueryMap Map<String, String> queryMap);

	@POST(APIConst.URL_TRANSACTIONS)
	Call<MenigaTransaction> createTransaction(@Body CreateTransaction body);

	@PUT(APIConst.URL_TRANSACTIONS)
	Call<List<MenigaTransaction>> updateTransactions(@QueryMap Map<String, String> transactionIds, @Body UpdateTransactions req);

	@GET(APIConst.URL_TRANSACTIONS + "/{id}")
	Call<MenigaTransaction> getTransaction(@Path("id") long transId);

	@PUT(APIConst.URL_TRANSACTIONS + "/{id}")
	Call<Void> updateTransaction(@Path("id") long transactionId, @Body UpdateTransaction req);

	@DELETE(APIConst.URL_TRANSACTIONS + "/{id}")
	Call<Void> deleteTransaction(@Path("id") long transactionId);

	@POST(APIConst.URL_TRANSACTIONS_SERIES)
	Call<List<MenigaTransactionSeries>> getTransactionSeries(@Body GetTransactionSeries req);

	@POST(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.COMMENTS)
	Call<MenigaComment> createComment(@Path("id") long transactionId, @Body CreateComment comment);

	@PUT(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.COMMENTS + "/{commentId}")
	Call<Void> updateComment(@Path("id") long transactionId, @Path("commentId") long commentId, @Body UpdateComment comment);

	@DELETE(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.COMMENTS + "/{commentId}")
	Call<Void> deleteComment(@Path("id") long transactionId, @Path("commentId") long commentId);

	@GET(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.SPLIT)
	Call<List<MenigaTransaction>> fetchSplitTransactions(@Path("id") long parentId);

	@POST(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.SPLIT)
	Call<List<MenigaTransaction>> splitTransaction(@Path("id") long transactionId, @Body SplitTransaction req);

	@PUT(APIConst.URL_TRANSACTIONS + "/{id}/" + APIConst.SPLIT)
	Call<List<MenigaTransaction>> updateSplits(@Path("id") long transactionId, @Body List<UpdateSplits> req);

	@POST(APIConst.URL_TRANSACTIONS + "/recategorize")
	Call<Void> recategorizeTransactions(@Body RecategorizeTransactions req);

	// --
	// Transaction Rules
	// --
	@GET(APIConst.URL_TRANSACTIONS + "/" + APIConst.RULES)
	Call<List<MenigaTransactionRule>> getTransactionRules();

	@POST(APIConst.URL_TRANSACTIONS + "/" + APIConst.RULES)
	Call<MenigaTransactionRule> createTransactionRule(@QueryMap Map<String, String> queryMap, @Body CreateTransactionRule req);

	@DELETE(APIConst.URL_TRANSACTIONS + "/" + APIConst.RULES + "/{id}")
	Call<Void> deleteTransactionRule(@Path("id") long id);

	@GET(APIConst.URL_TRANSACTIONS + "/" + APIConst.RULES + "/{id}")
	Call<MenigaTransactionRule> getTransactionRule(@Path("id") long id);

	@PUT(APIConst.URL_TRANSACTIONS + "/" + APIConst.RULES + "/{id}")
	Call<Void> updateTransactionRule(@Path("id") long id, @QueryMap Map<String, String> queryMap, @Body UpdateTransactionRule req);

	// --
	// Accounts
	// --
	@GET(APIConst.URL_ACCOUNTS)
	Call<List<MenigaAccount>> getAccounts(@QueryMap Map<String, String> queryMap);

	@GET(APIConst.URL_ACCOUNTS + "/{id}")
	Call<MenigaAccount> getAccount(@Path("id") long accountId);

	@PUT(APIConst.URL_ACCOUNTS + "/{id}")
	Call<Void> updateAccount(@Path("id") long accountId, @Body UpdateAccount req);

	@DELETE(APIConst.URL_ACCOUNTS + "/{id}")
	Call<Void> deleteAccount(@Path("id") long accountId);

	@GET(APIConst.URL_ACCOUNTS + "/" + APIConst.ACCOUNT_TYPES)
	Call<List<MenigaAccountType>> getAccountTypes();

	@GET(APIConst.URL_ACCOUNTS + "/" + APIConst.ACCOUNT_AUTHORIZATION_TYPES)
	Call<List<MenigaAuthorizationType>> getAccountAuthorizationTypes();

	@GET(APIConst.URL_ACCOUNTS + "/{id}/" + APIConst.ACCOUNT_METADATA)
	Call<List<KeyVal<String, String>>> getAccountMetadata(@Path("id") long accountId);

	@PUT(APIConst.URL_ACCOUNTS + "/{id}/" + APIConst.ACCOUNT_METADATA)
	Call<KeyVal<String, String>> updateAccountMetadata(@Path("id") long accountId, @Body UpdateAccountMetadata req);

	@GET(APIConst.URL_ACCOUNTS + "/{id}/" + APIConst.ACCOUNT_METADATA + "/{name}")
	Call<KeyVal<String, String>> getAccountMetadataKeyVal(@Path("id") long accountId, @Path("name") String name);

	@GET(APIConst.URL_ACCOUNTS + "/{id}/" + APIConst.ACCOUNT_BALANCE_HISTORY)
	Call<List<MenigaAccountBalanceHistory>> getAccountBalanceHistory(@Path("id") long accountId, @QueryMap Map<String, String> query);

	// --
	// Tags
	// --
	@GET(APIConst.URL_TAGS)
	Call<List<MenigaTag>> getTags();

	@GET(APIConst.URL_TAGS + "/{id}")
	Call<MenigaTag> getTag(@Path("id") long tagId);

	// --
	// Sync
	// --
	@POST(APIConst.URL_SYNC)
	Call<MenigaSync> startSync(@Body StartSync req);

	@GET(APIConst.URL_SYNC)
	Call<MenigaSyncStatus> getSyncStatus();

	@GET(APIConst.URL_SYNC + "/{id}")
	Call<MenigaSync> getSync(@Path("id") long id);

	@POST(APIConst.URL_SYNC + APIConst.REALM + "/{id}" + APIConst.AUTH)
	Call<MenigaRealmAuthResponse> getRealmAuthMethod(@Path("id") long id, @Body GetRealmAuthMethod req, @QueryMap Map<String, String> query);

	@GET(APIConst.URL_SYNC + APIConst.ACCOUNTS + "/{id}")
	Call<List<MenigaRealmAccount>> getRealmAccounts(@Path("id") long id);

	@POST(APIConst.URL_SYNC + APIConst.ACCOUNTS + "/{id}" + APIConst.AUTHORIZE)
	Call<List<MenigaRealmAccount>> addRealmAccountsToMeniga(@Path("id") long realmUserId, @Body List<MenigaRealmAccount> realmAccounts, @QueryMap Map<String, String> query);

	// --
	// UserEvents
	// --
	@GET(APIConst.URL_USER_EVENTS + APIConst.UE_DETAILS)
	Call<List<MenigaUserEvent>> getUserEvents();

	@PUT(APIConst.URL_USER_EVENTS)
	Call<Void> setSubscription(@Body SetSubscription req);

	@PUT(APIConst.URL_USER_EVENTS + APIConst.UE_DETAILS)
	Call<Void> updateSettings(@Body SetSubscriptionSettings req);

	// --
	// Feed
	// --
	@GET(APIConst.URL_FEED)
	Call<MenigaFeed> getFeed(@QueryMap Map<String, String> query);

	@GET(APIConst.URL_EVENT + "/{id}")
	Call<MenigaFeedItem> getEvent(@Path("id") long id);

	// --
	// Me
	// --
	@GET(APIConst.URL_ME + APIConst.PROFILE)
	Call<MenigaUserProfile> getUserProfile();

	@GET(APIConst.URL_ME + "?includeAll=true")
	Call<List<MenigaUser>> getUsers();

	@PUT(APIConst.URL_ME + APIConst.CULTURE)
	Call<Void> setCulture(@QueryMap Map<String, String> queryMap);

	@POST(APIConst.URL_ME + APIConst.REGISTER)
	Call<MenigaUser> registerUser(@Body RegisterUser newUser);

	@POST(APIConst.URL_ME + APIConst.PASSWORD + APIConst.FORGOT)
	Call<Void> forgotPassword(@Body ForgotPassword req);

	// --
	// Categories
	// --
	@GET(APIConst.URL_CATEGORIES)
	Call<List<MenigaCategory>> getCategories(@QueryMap Map<String, String> queryMap);

	@GET(APIConst.URL_CATEGORIES + "/{id}")
	Call<MenigaCategory> getCategoryById(@Path("id") long categoryId, @QueryMap Map<String, String> queryMap);

	@POST(APIConst.URL_CATEGORIES)
	Call<MenigaUserCategory> createUserCategory(@Body CreateUserCategory req);

	@DELETE(APIConst.URL_CATEGORIES + "/{id}")
	Call<Void> deleteCategory(@Path("id") long id, @QueryMap Map<String, String> stringStringMap);

	@PUT(APIConst.URL_CATEGORIES + "/{id}")
	Call<Void> updateUserCategory(@Path("id") long id, @Body UpdateUserCategory req);

	// --
	// Offers
	// --
	@GET(APIConst.URL_OFFERS)
	Call<MenigaOfferPage> getOffers(@QueryMap Map<String, String> queryMap);

	@GET(APIConst.URL_OFFERS + "/{id}")
	Call<MenigaOffer> getOffer(@Path("id") long id);

	@GET(APIConst.URL_OFFERS + "/{token}")
	Call<MenigaOffer> getOffer(@Path("token") String token);

	@GET(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_TRANSACTIONS)
	Call<List<MenigaTransaction>> getOfferTransaction(@Path("id") long id);

	@POST(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_ACTIVATE)
	Call<Void> activateOfferById(@Path("id") long id);

	@POST(APIConst.URL_OFFERS + "/{token}" + APIConst.OF_ACTIVATE)
	Call<Void> activateOfferByToken(@Path("token") String token);

	@DELETE(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_ACTIVATE)
	Call<Void> declineOffer(@Path("id") long id);

	@POST(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_SEEN)
	Call<Void> markOfferAsSeen(@Path("id") long id);

	@POST(APIConst.URL_OFFERS + APIConst.OF_ACCEPT_TERMS_AND_CONDITIONS)
	Call<Void> acceptTermsAndConditions();

	@POST(APIConst.URL_OFFERS + APIConst.OF_ENABLE)
	Call<Void> enableOffers();

	@DELETE(APIConst.URL_OFFERS + APIConst.OF_ENABLE)
	Call<Void> disableOffers();

	@GET(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_SIMILAR_BRAND_SPENING_DETAILS)
	Call<MenigaSimilarBrandSpendingDetails> getSimilarBrandSpeningDetails(@Path("id") long id);

	@GET(APIConst.URL_OFFERS + "/{id}" + APIConst.URL_REDEMPTIONS)
	Call<MenigaRedemptions> getRedemptionsByOfferId(@Path("id") long id);

	@GET(APIConst.URL_OFFERS + "/{id}" + APIConst.OF_MERCHANT_LOCATIONS)
	Call<MenigaOfferMerchantLocationPage> getMerchantLocationByOfferId(@Path("id") long id);

	// --
	// Redemptions
	// --
	@GET(APIConst.URL_REDEMPTIONS)
	Call<MenigaRedemptions> getRedemptions(@QueryMap Map<String, String> stringStringMap);

	// --
	// Reimbursement accounts
	// --
	@GET(APIConst.URL_REIMBURSEMENT_ACCOUNTS)
	Call<MenigaReimbursementAccountPage> getReimbursementAccounts(@QueryMap Map<String, String> stringStringMap);

	@POST(APIConst.URL_REIMBURSEMENT_ACCOUNTS)
	Call<MenigaReimbursementAccount> addReimbursementAccount(@Body CreateReimbursementAccount req);

	@GET(APIConst.URL_REIMBURSEMENT_ACCOUNTS + "/{id}")
	Call<MenigaReimbursementAccount> getReimbursementAccountById(@Path("id") int id);

	@GET(APIConst.URL_REIMBURSEMENT_ACCOUNTS + APIConst.RA_TYPES)
	Call<MenigaReimbursementAccountTypePage> getReimbursementAccountTypes(@QueryMap Map<String, String> stringStringMap);

	// --
	// Net worth
	// --
	@GET(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}")
	Call<MenigaNetWorth> getNetWorth(@Path("id") long id);

	@GET(APIConst.URL_NETWORTH)
	Call<List<MenigaNetWorth>> getNetWorths(@QueryMap Map<String, String> queryMap);

	@PUT(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}" + APIConst.NW_BALANCEHISTORY + "/{historyid}")
	Call<Void> updateBalance(@Path("id") long id, @Path("historyid") long historyid, @Body UpdateHistoryBalance req);

	@DELETE(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}" + APIConst.NW_BALANCEHISTORY + "/{historyid}")
	Call<Void> deleteBalance(@Path("id") long accountId, @Path("historyid") long balanceId);

	@GET(APIConst.URL_NETWORTH + APIConst.NW_FIRST_BALANCE)
	Call<MenigaNetWorthBalance> getNetWorthFirstBalanceEntry(@QueryMap Map<String, String> queryMap);

	@POST(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS)
	Call<MenigaNetWorth> createNetWorthAccount(@Body CreateNetWorthAccount body);

	@DELETE(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}")
	Call<Void> deleteNetWorthAccount(@Path("id") long id);

	@PUT(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}")
	Call<Void> updateNetWorthAccount(@Path("id") long id, @Body UpdatedNetWorthAccount req);

	@POST(APIConst.URL_NETWORTH + APIConst.NW_ACCOUNTS + "/{id}" + APIConst.NW_BALANCEHISTORY)
	Call<MenigaNetWorthBalance> createNetWorthBalanceHistory(@Path("id") long id, @Body CreateNetWorthBalanceHistory req);

	@GET(APIConst.URL_NETWORTH + APIConst.NW_TYPES)
	Call<List<KeyVal<Long, String>>> getNetWorthTypes();

	// --
	// Public
	// --
	@GET(APIConst.URL_PUBLIC + APIConst.SETTINGS)
	Call<MenigaPublicSettings> getPublicSettings();

	// --
	// Terms
	// --
	@GET(APIConst.URL_TERMS)
	Call<List<MenigaTerms>> getTerms(@Header("Accept-Language") String culture);

	@GET(APIConst.URL_TERMS + "/{typeId}")
	Call<MenigaTerms> getTerm(@Header("Accept-Language") String culture, @Path("typeId") long typeId);

	@GET(APIConst.URL_TERMS + APIConst.TYPES)
	Call<List<MenigaTermType>> getTermTypes(@Header("Accept-Language") String culture);

	@POST(APIConst.URL_TERMS + "/{typeId}" + APIConst.ACCEPT)
	Call<Void> acceptTerms(@Path("typeId") long typeId);

	@POST(APIConst.URL_TERMS + "/{typeId}" + APIConst.DECLINE)
	Call<Void> declineTerms(@Path("typeId") long typeId);

	// --
	// Upcoming
	// --
	@GET(APIConst.URL_UPCOMING)
	Call<List<MenigaUpcoming>> getUpcoming(@QueryMap Map<String, String> queryMap);

	@GET(APIConst.URL_UPCOMING + "/{id}")
	Call<MenigaUpcoming> getUpcoming(@Path("id") long id);

	@POST(APIConst.URL_UPCOMING)
	Call<List<MenigaUpcoming>> createUpcoming(@Body CreateUpcoming req);

	@PUT(APIConst.URL_UPCOMING + "/{id}")
	Call<Void> updateUpcoming(@Path("id") long id, @Body UpdateUpcoming req, @QueryMap Map<String, String> queryMap);

	@DELETE(APIConst.URL_UPCOMING + "/{id}")
	Call<Void> deleteUpcoming(@Path("id") long id);

	@DELETE(APIConst.URL_UPCOMING + APIConst.UPCOMING_RECURRINC + "/{id}")
	Call<Void> deleteUpcomingSeries(@Path("id") long id);

	// --
	// Budget
	// --
	@GET(APIConst.URL_BUDGETS)
	Call<List<MenigaBudget>> getBudgets(@QueryMap Map<String, String> query);

	@GET(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_ENTRIES)
	Call<List<MenigaBudgetEntry>> getBudgetEntries(@Path("id") String id, @QueryMap Map<String, String> query);

	@POST(APIConst.URL_BUDGETS)
	Call<Void> createBudget(@Body CreateBudget req);

	@POST(APIConst.URL_BUDGETS + "/{id}" + APIConst.BUDGET_RULES)
	Call<Void> updateBudget(@Path("id") String id, @Body UpdateBudget req);

	// --
	// Challenges
	// --
	@GET(APIConst.URL_CHALLENGES)
	Call<List<MenigaChallenge>> getChallenges(@QueryMap Map<String, String> query);

	@GET(APIConst.URL_CHALLENGES + "/{id}")
	Call<MenigaChallenge> getChallenge(@Path("id") String id);

	@POST(APIConst.URL_CHALLENGES + "/{id}" + APIConst.CHALLENGES_ACCEPT)
	Call<MenigaChallenge> acceptChallenge(@Body AcceptChallenge req, @Path("id") String id);

	@DELETE(APIConst.URL_CHALLENGES + "/{id}")
	Call<Void> deleteChallenge(@Path("id") String id);

	@POST(APIConst.URL_CHALLENGES)
	Call<MenigaChallenge> createChallenge(@Body CreateChallenge req);

	@PUT(APIConst.URL_CHALLENGES + "/{id}")
	Call<Void> updateChallenge(@Path("id") String id, @Body UpdateChallenge req);

	// --
	// Organizations
	// --
	@GET(APIConst.URL_ORGANIZATIONS)
	Call<List<MenigaOrganization>> getOrganizations();

	// Generic
	@GET
	Call<Object> genericGet(@Url String path, @QueryMap Map<String, String> query);

	@HEAD
	Call<Object> genericHead(@Url String path, @QueryMap Map<String, String> query);

	@OPTIONS
	Call<Object> genericOptions(@Url String path, @QueryMap Map<String, String> query);

	@POST
	Call<Object> genericPost(@Url String path, @Body String body, @QueryMap Map<String, String> query);

	@PUT
	Call<Object> genericPut(@Url String path, @Body String body, @QueryMap Map<String, String> query);

	@DELETE
	Call<Object> genericDelete(@Url String path, @QueryMap Map<String, String> query);
}
