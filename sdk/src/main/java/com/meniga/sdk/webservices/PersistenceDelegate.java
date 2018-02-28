package com.meniga.sdk.webservices;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.adapters.TaskAdapter;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.interfaces.PersistenceProvider;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.feed.MenigaScheduledEvent;
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
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.MenigaSyncStatus;
import com.meniga.sdk.models.terms.MenigaTermType;
import com.meniga.sdk.models.terms.MenigaTerms;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.models.transactions.MenigaTag;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.transactions.MenigaTransactionUpdate;
import com.meniga.sdk.models.upcoming.MenigaUpcoming;
import com.meniga.sdk.models.user.MenigaUser;
import com.meniga.sdk.models.user.MenigaUserMetaData;
import com.meniga.sdk.models.user.MenigaUserProfile;
import com.meniga.sdk.models.userevents.MenigaUserEvent;
import com.meniga.sdk.webservices.account.Account;
import com.meniga.sdk.webservices.account.AccountBalanceHistory;
import com.meniga.sdk.webservices.account.AccountTypeCategory;
import com.meniga.sdk.webservices.account.AccountService;
import com.meniga.sdk.webservices.account.AccountType;
import com.meniga.sdk.webservices.account.AuthorizationType;
import com.meniga.sdk.webservices.account.UpdateAccount;
import com.meniga.sdk.webservices.account.UpdateAccountMetadata;
import com.meniga.sdk.webservices.budget.BudgetService;
import com.meniga.sdk.webservices.budget.CreateBudget;
import com.meniga.sdk.webservices.budget.CreateBudgetEntries;
import com.meniga.sdk.webservices.budget.GetBudget;
import com.meniga.sdk.webservices.budget.GetBudgetEntries;
import com.meniga.sdk.webservices.budget.GetBudgetEntryById;
import com.meniga.sdk.webservices.budget.GetBudgets;
import com.meniga.sdk.webservices.budget.UpdateBudget;
import com.meniga.sdk.webservices.budget.UpdateBudgetEntry;
import com.meniga.sdk.webservices.budget.UpdateBudgetRules;
import com.meniga.sdk.webservices.requests.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meniga.sdk.helpers.Objects.requireNonNull;
import static java.util.Collections.unmodifiableMap;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class PersistenceDelegate {

	private Map<Service, ?> services = Collections.emptyMap();
	private PersistenceProvider provider;

	public void setApis(Map<Service, ?> clients) {
		this.services = unmodifiableMap(requireNonNull(clients));
	}

	public void setProvider(PersistenceProvider provider) {
		this.provider = provider;
	}

	private <E> Result<E> persist(final QueryRequestObject key, Call<E> call) {
		return MenigaSDK.getMenigaSettings().getTaskAdapter().adapt(call, new Callback<E>() {
			@Override
			public void onResponse(Call<E> call, Response<E> response) {
				if (!call.isCanceled() && response.isSuccessful()) {
					provider.save(key, response.body());
				}
			}

			@Override
			public void onFailure(Call<E> call, Throwable t) {

			}
		});
	}

	private <E> Result<E> call(Call<E> call) {
		return MenigaSDK.getMenigaSettings().getTaskAdapter().adapt(call, null);
	}

	@SuppressWarnings("unchecked")
	private <T> Result<T> createTask(Object value) {
		return MenigaSDK.getMenigaSettings().getTaskAdapter().adapt((T) value);
	}

	// --
	// Merchants
	// --
	public Result<MenigaMerchant> getMerchant(GetMerchant req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.MERCHANTS).getMerchant(req.id));
	}

	public Result<List<MenigaMerchant>> getMerchants(GetMerchants req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.MERCHANTS).getMerchants(req.toQueryMap()));
	}

	public Result<List<MenigaTopMerchant>> getTopMerchants(GetTopMerchants req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.MERCHANTS).getTopMerchants(req));
	}

	// --
	// Sync
	// --
	public Result<List<Account>> getAccounts(GetAccounts req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccounts(req.toQueryMap()));
	}

	public Result<Account> getAccount(GetAccount req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccount(req.accountId));
	}

	public Result<List<AccountType>> getAccountTypes(GetAccountTypes req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccountTypes());
	}

	public Result<List<AuthorizationType>> getAccountAuthorizationTypes(GetAuthorizationTypes req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccountAuthorizationTypes());
	}

	public Result<List<AccountTypeCategory>> getAccountCategories() {
		return call(getService(AccountService.class).getAccountCategories());
	}

	public Result<List<KeyVal<String, String>>> getAccountMetadata(GetAccountMetadata req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccountMetadata(req.id));
	}

	public Result<KeyVal<String, String>> updateAccountMetadata(UpdateAccountMetadata req) {
		return persist(req, getService(AccountService.class).updateAccountMetadata(req.id, req));
	}

	public Result<KeyVal<String, String>> getAccountMetadataKeyVal(GetAccountMetadataKeyVal req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccountMetadataKeyVal(req.id, req.name));
	}

	public Result<List<AccountBalanceHistory>> getAccountBalanceHistory(GetAccountBalanceHistory req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(AccountService.class).getAccountBalanceHistory(req.id, req.toQueryMap()));
	}

	public Result<Void> updateAccount(UpdateAccount req) {
		return persist(req, getService(AccountService.class).updateAccount(req.id, req));
	}

	public Result<Void> deleteAccount(DeleteAccount req) {
		return persist(req, getService(AccountService.class).deleteAccount(req.accountId));
	}

	// --
	// Tags
	// --
	public Result<List<MenigaTag>> getTags() {
		GetTags req = new GetTags();
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TAGS).getTags());
	}

	public Result<MenigaTag> getTag(GetTag req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TAGS).getTag(req.id));
	}

	// --
	// Transaction
	// --
	public Result<MenigaTransactionPage> getTransactions(GetTransactions req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getTransactions(req.toQueryMap()));
	}

	public Result<MenigaTransaction> createTransaction(CreateTransaction req) {
		// Will never exist in the persistence store a priori
		return persist(req, getClient(Service.TRANSACTIONS).createTransaction(req));
	}

	public Result<MenigaTransaction> getTransaction(GetTransaction req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getTransaction(req.id));
	}

	public Result<MenigaTransactionUpdate> updateTransaction(UpdateTransaction req) {
		return persist(req, getClient(Service.TRANSACTIONS).updateTransaction(req.transactionId, req));
	}


	public Result<MenigaTransactionUpdate> updateTransactions(UpdateTransactions req) {
		return persist(req, getClient(Service.TRANSACTIONS).updateTransactions(req.toQueryMap(), req));
	}

	// --
	// Sync
	// --
	public Result<MenigaSync> startSync(StartSync req) {
		TaskAdapter taskAdapter = MenigaSDK.getMenigaSettings().getTaskAdapter();
		return taskAdapter.adapt(getClient(Service.SYNC).startSync(req), null);
	}

	public Result<MenigaSyncStatus> getSyncStatus() {
		TaskAdapter taskAdapter = MenigaSDK.getMenigaSettings().getTaskAdapter();
		return taskAdapter.adapt(getClient(Service.SYNC).getSyncStatus(), null);
	}

	public Result<MenigaRealmAuthResponse> performBankAuthenticationStep(GetRealmAuthMethod req) {
		return persist(req, getClient(Service.SYNC).getRealmAuthMethod(req.id, req));
	}

	public Result<List<MenigaRealmAccount>> getRealmAccounts(GetRealmAccounts req) {
		return persist(req, getClient(Service.SYNC).getRealmAccounts(req.realmUserId, req.toQueryMap()));
	}

	public Result<List<MenigaRealmAccount>> addRealmAccountsToMeniga(AddRealmAccountsToMeniga req) {
		return persist(req, getClient(Service.SYNC).addRealmAccountsToMeniga(req.realmUserId, req.realmAccounts, req.toQueryMap()));
	}

	// --
	// User Events
	// --

	public Result<List<MenigaUserEvent>> getUserEvents() {
		GetUserEvents req = new GetUserEvents();
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.USER_EVENTS).getUserEvents());
	}

	public Result<Void> setSubscription(SetSubscription req) {
		return persist(req, getClient(Service.USER_EVENTS).setSubscription(req));
	}

	public Result<Void> updateSettings(SetSubscriptionSettings req) {
		return persist(req, getClient(Service.USER_EVENTS).updateSettings(req));
	}

	public Result<MenigaSync> getSync(GetSync req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.SYNC).getSync(req.syncHistoryId));
	}

	// --
	// Transactions
	// --
	public Result<List<MenigaTransaction>> fetchSplitTransactions(GetSplitTransactions req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).fetchSplitTransactions(req.parentId));
	}

	public Result<List<MenigaTransaction>> splitTransaction(SplitTransaction req) {
		return persist(req, getClient(Service.TRANSACTIONS).splitTransaction(req.transactionId, req));
	}

	public Result<List<MenigaTransaction>> updateSplits(long id, List<UpdateSplits> req) {
		return persist(req.get(0), getClient(Service.TRANSACTIONS).updateSplits(id, req));
	}

	public Result<Void> deleteTransaction(DeleteTransaction req) {
		return persist(req, getClient(Service.TRANSACTIONS).deleteTransaction(req.transactionId));
	}

	public Result<Void> deleteTransactions(DeleteTransactions req) {
		return persist(req, getClient(Service.TRANSACTIONS).deleteTransactions(req.toQueryMap()));
	}

	public Result<List<MenigaTransactionSeries>> getTransactionSeries(GetTransactionSeries req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getTransactionSeries(req));
	}

	public Result<MenigaComment> createComment(CreateComment req) {
		return persist(req, getClient(Service.TRANSACTIONS).createComment(req.transactionId, req));
	}

	public Result<Void> updateComment(UpdateComment req) {
		return persist(req, getClient(Service.TRANSACTIONS).updateComment(req.transactionId, req.commentId, req));
	}

	public Result<List<MenigaTransaction>> getOfferTransactions(GetOfferTransactions req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getOfferTransaction(req.id));
	}

	public Result<Void> deleteComment(DeleteComment req) {
		return persist(req, getClient(Service.TRANSACTIONS).deleteComment(req.transactionId, req.commentId));
	}

	public Result<Void> recategorizeTransactions(RecategorizeTransactions req) {
		return persist(req, getClient(Service.TRANSACTIONS).recategorizeTransactions(req));
	}

	public Result<List<MenigaTransactionRule>> getTransactionRules(GetTransactionRules req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getTransactionRules());
	}

	public Result<MenigaTransactionRule> createTransactionRule(CreateTransactionRule req) {
		return persist(req, getClient(Service.TRANSACTIONS).createTransactionRule(req.toQueryMap(), req));
	}

	public Result<Void> deleteTransactionRule(DeleteTransactionRule req) {
		return persist(req, getClient(Service.TRANSACTIONS).deleteTransactionRule(req.id));
	}

	public Result<MenigaTransactionRule> getTransactionRule(GetTransactionRule req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TRANSACTIONS).getTransactionRule(req.id));
	}

	public Result<Void> updateTransactionRule(UpdateTransactionRule req) {
		return persist(req, getClient(Service.TRANSACTIONS).updateTransactionRule(req.id, req.toQueryMap(), req));
	}

	// --
	// Feed
	// --
	public Result<MenigaFeed> getFeed(GetFeed req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.FEED).getFeed(req.toQueryMap()));
	}

	public Result<MenigaScheduledEvent> getScheduledEvent(GetScheduledEvent req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.FEED).getScheduledEvent(req.type, req.id));
	}

	// --
	// User events
	// --

	public Result<MenigaFeedItem> getEvent(GetEvent req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.USER_EVENTS).getEvent(req.id));
	}

	// --
	// Categories
	// --
	public Result<List<MenigaCategory>> getCategories(GetCategories req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.CATEGORIES).getCategories(req.toQueryMap()));
	}

	public Result<MenigaCategory> getCategoryById(GetCategoryById req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.CATEGORIES).getCategoryById(req.categoryId, req.toQueryMap()));
	}

	public Result<MenigaUserCategory> createUserCategory(CreateUserCategory req) {
		return persist(req, getClient(Service.CATEGORIES).createUserCategory(req));
	}

	public Result<Void> deleteCategory(DeleteUserCategory req) {
		return persist(req, getClient(Service.CATEGORIES).deleteCategory(req.id, req.toQueryMap()));
	}

	public Result<Void> updateUserCategory(UpdateUserCategory req) {
		return persist(req, getClient(Service.CATEGORIES).updateUserCategory(req.id, req));
	}

	// --
	// Users
	// --
	public Result<MenigaUserProfile> getUserProfile() {
		GetUserProfile req = new GetUserProfile();
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.USERS).getUserProfile());
	}

	public Result<List<MenigaUser>> getUsers(GetUsers req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.USERS).getUsers());
	}

	public Result<Void> setCulture(SetCulture req) {
		return persist(req, getClient(Service.USERS).setCulture(req.toQueryMap()));
	}

	public Result<MenigaUser> registerUser(RegisterUser req) {
		return persist(req, getClient(Service.USERS).registerUser(req));
	}

	public Result<Void> forgotPassword(ForgotPassword req) {
		return persist(req, getClient(Service.USERS).forgotPassword(req));
	}

	public Result<List<MenigaUserMetaData>> getUserMetaData(GetUserMetaData req) {
		return persist(req, getClient(Service.USERS).getUserMetaData(req.toQueryMap()));
	}

	// --
	// Offers
	// --
	public Result<MenigaOfferPage> getOffers(GetOffers req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}

		return persist(req, getClient(Service.OFFERS).getOffers(req.toQueryMap()));
	}

	public Result<MenigaOffer> getOfferByToken(GetOfferByToken req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}

		return persist(req, getClient(Service.OFFERS).getOffer(req.token));
	}

	public Result<MenigaOffer> getOfferById(GetOfferById req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}

		return persist(req, getClient(Service.OFFERS).getOffer(req.id));
	}

	public Result<MenigaRedemptions> getRedemptions(GetRedemptions req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}

		return persist(req, getClient(Service.OFFERS).getRedemptions(req.toQueryMap()));
	}

	public Result<Void> activateOfferById(ActivateOfferById req) {
		return persist(req, getClient(Service.OFFERS).activateOfferById(req.id));
	}

	public Result<Void> activateOfferByToken(ActivateOfferByToken req) {
		return persist(req, getClient(Service.OFFERS).activateOfferByToken(req.validationToken));
	}

	public Result<Void> declineOffer(DeclineOffer req) {
		return persist(req, getClient(Service.OFFERS).declineOffer(req.id));
	}

	public Result<Void> acceptTermsAndConditions(AcceptOffersTermsAndConditions req) {
		return persist(req, getClient(Service.OFFERS).acceptTermsAndConditions());
	}

	public Result<Void> enableOffers(EnableOffers req) {
		return persist(req, getClient(Service.OFFERS).enableOffers());
	}

	public Result<Void> disableOffers(DisableOffers req) {
		return persist(req, getClient(Service.OFFERS).disableOffers());
	}

	public Result<Void> markOfferAsSeen(MarkOfferAsSeen req) {
		return persist(req, getClient(Service.OFFERS).markOfferAsSeen(req.id));
	}

	public Result<MenigaSimilarBrandSpendingDetails> getSimilarBrandSpendingDetails(GetSimilarBrandSpendingDetails req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.OFFERS).getSimilarBrandSpeningDetails(req.id));
	}

	public Result<MenigaRedemptions> getRedemptionsByOfferId(GetRedemptionsByOfferId req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.OFFERS).getRedemptionsByOfferId(req.offerId));
	}

	public Result<MenigaOfferMerchantLocationPage> getMerchantLocationsByOfferId(GetMerchantLocationsByOfferId req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.OFFERS).getMerchantLocationByOfferId(req.offerId));
	}

	public Result<MenigaReimbursementAccount> addReimbursementAccount(CreateReimbursementAccount req) {
		return persist(req, getClient(Service.OFFERS).addReimbursementAccount(req));
	}

	public Result<MenigaReimbursementAccountPage> getReimbursementAccounts(GetReimbursementAccounts req) {
		return persist(req, getClient(Service.OFFERS).getReimbursementAccounts(req.toQueryMap()));
	}

	public Result<MenigaReimbursementAccountTypePage> getReimbursementAccountTypes(GetReimbursementAccountTypes req) {
		return persist(req, getClient(Service.OFFERS).getReimbursementAccountTypes(req.toQueryMap()));
	}

	public Result<MenigaReimbursementAccount> getReimbursementAccountById(GetReimbursementAccountById req) {
		return persist(req, getClient(Service.OFFERS).getReimbursementAccountById(req.id));
	}

	// --
	// Net worth
	// --
	public Result<List<MenigaNetWorth>> getNetWorth(GetNetWorths req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.NET_WORTH).getNetWorths(req.toQueryMap()));
	}

	public Result<MenigaNetWorth> getNetWorth(GetNetWorth req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.NET_WORTH).getNetWorth(req.id));
	}

	public Result<Void> updateBalance(UpdateHistoryBalance req) {
		return persist(req, getClient(Service.NET_WORTH).updateBalance(req.accountId, req.balanceId, req));
	}

	public Result<Void> deleteBalance(DeleteNetWorthBalance req) {
		return persist(req, getClient(Service.NET_WORTH).deleteBalance(req.accountId, req.id));
	}

	public Result<MenigaNetWorthBalance> getNetWorthFirstBalanceEntry(GetNetWorthFirstBalanceEntry req) {
		return persist(req, getClient(Service.NET_WORTH).getNetWorthFirstBalanceEntry(req.toQueryMap()));
	}

	public Result<MenigaNetWorth> createNetWorthAccount(CreateNetWorthAccount req) {
		return persist(req, getClient(Service.NET_WORTH).createNetWorthAccount(req));
	}

	public Result<Void> deleteNetWorthAccount(DeleteNetWorthAccount req) {
		return persist(req, getClient(Service.NET_WORTH).deleteNetWorthAccount(req.id));
	}

	public Result<Void> updateNetWorthAccount(UpdatedNetWorthAccount req) {
		return persist(req, getClient(Service.NET_WORTH).updateNetWorthAccount(req.id, req));
	}

	public Result<MenigaNetWorthBalance> createNetWorthBalanceHistory(CreateNetWorthBalanceHistory req) {
		return persist(req, getClient(Service.NET_WORTH).createNetWorthBalanceHistory(req.id, req));
	}

	public Result<List<KeyVal<Long, String>>> getNetWorthTypes(GetNetWorthTypes req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.NET_WORTH).getNetWorthTypes());
	}

	// --
	// Upcoming
	// --
	public Result<List<MenigaUpcoming>> getUpcoming(GetUpcoming req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.UPCOMING).getUpcoming(req.toQueryMap()));
	}

	public Result<MenigaUpcoming> getUpcoming(GetUpcomingById req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.UPCOMING).getUpcoming(req.id));
	}

	public Result<List<MenigaUpcoming>> createUpcoming(CreateUpcoming req) {
		return persist(req, getClient(Service.UPCOMING).createUpcoming(req));
	}

	public Result<Void> updateUpcoming(UpdateUpcoming req) {
		return persist(req, getClient(Service.UPCOMING).updateUpcoming(req.id, req, req.toQueryMap()));
	}

	public Result<Void> deleteUpcoming(DeleteUpcoming req) {
		return persist(req, getClient(Service.UPCOMING).deleteUpcoming(req.id));
	}

	public Result<Void> deleteUpcomingSeries(DeleteUpcomingSeries req) {
		return persist(req, getClient(Service.UPCOMING).deleteUpcomingSeries(req.id));
	}

	// --
	// Challenges
	// --
	public Result<List<MenigaChallenge>> getChallenges(GetChallenges req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.CHALLENGES).getChallenges(req.toQueryMap()));
	}

	public Result<MenigaChallenge> getChallenge(GetChallenge req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.CHALLENGES).getChallenge(req.id.toString()));
	}

	public Result<MenigaChallenge> acceptChallenge(AcceptChallenge req) {
		return persist(req, getClient(Service.CHALLENGES).acceptChallenge(req, req.id.toString()));
	}

	public Result<Void> deleteChallenge(DeleteChallenge req) {
		return persist(req, getClient(Service.CHALLENGES).deleteChallenge(req.id.toString()));
	}

	public Result<MenigaChallenge> createChallenge(CreateChallenge req) {
		return persist(req, getClient(Service.CHALLENGES).createChallenge(req));
	}

	public Result<Void> updateChallenge(UpdateChallenge req) {
		return persist(req, getClient(Service.CHALLENGES).updateChallenge(req.id.toString(), req));
	}

	public Result<List<MenigaChallenge>> getChallengeHistory(GetChallengeHistory req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.CHALLENGES).getChallengeHistory(req.id.toString(), req.toQueryMap()));
	}

	// --
	// Budget
	// --
	public Result<List<MenigaBudget>> getBudgets(GetBudgets req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(BudgetService.class).getBudgets(req.toQueryMap()));
	}

	public Result<MenigaBudget> getBudget(GetBudget parameters) {
		if (provider.hasKey(parameters)) {
			return createTask(provider.fetch(parameters));
		}
		return persist(parameters, getService(BudgetService.class).getBudget(Long.toString(parameters.getId()), parameters.toQueryMap()));
	}

	public Result<List<MenigaBudgetEntry>> getBudgetEntries(GetBudgetEntries req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getService(BudgetService.class).getBudgetEntries(Long.toString(req.getId()), req.toQueryMap()));
	}

	public Result<List<MenigaBudgetEntry>> createBudgetEntries(long budgetId, CreateBudgetEntries parameters) {
		return persist(parameters, getService(BudgetService.class).createBudgetEntries(Long.toString(budgetId), parameters));
	}

	public Result<Void> deleteBudgetEntry(long budgetId, long entryId) {
		return call(getService(BudgetService.class).deleteBudgetEntry(Long.toString(budgetId), Long.toString(entryId)));
	}

	public Result<MenigaBudgetEntry> getBudgetEntry(GetBudgetEntryById request) {
		if (provider.hasKey(request)) {
			return createTask(provider.fetch(request));
		}
		return persist(request, getService(BudgetService.class).getBudgetEntry(Long.toString(request.getBudgetId()), Long.toString(request.getEntryId())));
	}

	public Result<MenigaBudgetEntry> updateBudgetEntry(long budgetId, long entryId, UpdateBudgetEntry updateBudgetEntry) {
		return persist(updateBudgetEntry, getService(BudgetService.class).updateBudgetEntries(Long.toString(budgetId), Long.toString(entryId), updateBudgetEntry));
	}

	public Result<MenigaBudget> createBudget(CreateBudget req) {
		return persist(req, getService(BudgetService.class).createBudget(req));
	}

	public Result<MenigaBudget> updateBudget(long budgetId, UpdateBudget parameters) {
		return persist(parameters, getService(BudgetService.class).updateBudget(Long.toString(budgetId), parameters));
	}

	public Result<Void> updateBudgetRules(long budgetId, UpdateBudgetRules req) {
		return call(getService(BudgetService.class).createBudgetRules(Long.toString(budgetId), req));
	}

	public Result<Void> deleteBudget(long budgetId) {
		return call(getService(BudgetService.class).deleteBudget(Long.toString(budgetId)));
	}

	public Result<Void> resetBudget(long budgetId) {
		return call(getService(BudgetService.class).resetBudget(Long.toString(budgetId)));
	}

	// --
	// Organizations
	// --
	public Result<List<MenigaOrganization>> getOrganizations() {
		return persist(new GetOrganizations(), getClient(Service.ORGANIZATIONS).getOrganizations());
	}

	// --
	// Terms
	// --
	public Result<List<MenigaTerms>> getTerms(GetTerms req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TERMS).getTerms(req.culture));
	}

	public Result<MenigaTerms> getTerm(GetTerm req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TERMS).getTerm(req.culture, req.typeId));
	}

	public Result<List<MenigaTermType>> getTermTypes(GetTermTypes req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.TERMS).getTermTypes(req.culture));
	}

	public Result<Void> acceptTerms(AcceptTerms req) {
		return persist(req, getClient(Service.TERMS).acceptTerms(req.typeId));
	}

	public Result<Void> declineTerms(DeclineTerms req) {
		return persist(req, getClient(Service.TERMS).declineTerms(req.typeId));
	}

	// --
	// Generic
	// --
	Result<Object> genericRequest(HttpMethod method, String path, String body, Map<String, String> query) {
		TaskAdapter taskAdapter = MenigaSDK.getMenigaSettings().getTaskAdapter();
		switch (method) {
			case HEAD:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericHead(path, query), null);
			case OPTIONS:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericOptions(path, query), null);
			case POST:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericPost(path, body, query), null);
			case PUT:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericPut(path, body, query), null);
			case DELETE:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericDelete(path, query), null);
			case GET:
			default:
				return taskAdapter.adapt(getClient(Service.BYPASS).genericGet(path, query), null);
		}
	}

	public Result<MenigaPublicSettings> getPublicSettings(GetPublicSettings req) {
		if (provider.hasKey(req)) {
			return createTask(provider.fetch(req));
		}
		return persist(req, getClient(Service.PUBLIC).getPublicSettings());
	}

	private MenigaAPI getClient(Service forService) {
		return !services.containsKey(forService)
				? (MenigaAPI) services.get(Service.ALL)
				: (MenigaAPI) services.get(forService);
	}

	@SuppressWarnings("unchecked")
	private <T> T getService(Class<T> serviceClass) {
		return (T) services.get(Service.from(serviceClass));
	}

	Map<Service, ?> getApis() {
		return services;
	}
}
