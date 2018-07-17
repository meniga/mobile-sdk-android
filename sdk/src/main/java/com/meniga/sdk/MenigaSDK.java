package com.meniga.sdk;

import com.meniga.sdk.interfaces.PersistenceProvider;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations;
import com.meniga.sdk.models.budget.MenigaBudget;
import com.meniga.sdk.models.budget.MenigaBudgetEntry;
import com.meniga.sdk.models.budget.MenigaBudgetRule;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.MenigaUserCategory;
import com.meniga.sdk.models.categories.operators.MenigaCategoryOperations;
import com.meniga.sdk.models.challenges.MenigaChallenge;
import com.meniga.sdk.models.challenges.operators.MenigaChallengesOperations;
import com.meniga.sdk.models.eventtracking.MenigaEventTracking;
import com.meniga.sdk.models.eventtracking.operators.MenigaEventTrackingOperations;
import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaScheduledEvent;
import com.meniga.sdk.models.feed.operators.MenigaFeedOperations;
import com.meniga.sdk.models.merchants.MenigaMerchant;
import com.meniga.sdk.models.merchants.MenigaTopMerchant;
import com.meniga.sdk.models.merchants.operators.MenigaMerchantOperations;
import com.meniga.sdk.models.merchants.operators.MenigaTopMerchantOperations;
import com.meniga.sdk.models.networth.MenigaNetWorth;
import com.meniga.sdk.models.networth.MenigaNetWorthBalance;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthBalanceOperations;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthOperations;
import com.meniga.sdk.models.offers.MenigaOffer;
import com.meniga.sdk.models.offers.MenigaOffersSettings;
import com.meniga.sdk.models.offers.operators.MenigaOfferOperations;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.models.offers.redemptions.operators.MenigaRedemptionsOperations;
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount;
import com.meniga.sdk.models.offers.reimbursementaccounts.operators.MenigaReimbursementAccountOperations;
import com.meniga.sdk.models.operators.MenigaClientModule;
import com.meniga.sdk.models.organizations.MenigaOrganization;
import com.meniga.sdk.models.organizations.MenigaRealm;
import com.meniga.sdk.models.organizations.operators.MenigaOrganizationOperations;
import com.meniga.sdk.models.organizations.operators.MenigaRealmOperations;
import com.meniga.sdk.models.serverpublic.MenigaPublicSettings;
import com.meniga.sdk.models.serverpublic.operators.MenigaPublicSettingsOperations;
import com.meniga.sdk.models.sync.MenigaSync;
import com.meniga.sdk.models.sync.operators.MenigaSyncOperations;
import com.meniga.sdk.models.terms.MenigaTerms;
import com.meniga.sdk.models.terms.operators.MenigaTermsOperations;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.models.transactions.MenigaTag;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionRule;
import com.meniga.sdk.models.transactions.MenigaTransactionSeries;
import com.meniga.sdk.models.transactions.operators.MenigaCommentOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTagOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionRuleOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionSeriesOperations;
import com.meniga.sdk.models.upcoming.MenigaUpcoming;
import com.meniga.sdk.models.upcoming.operators.MenigaUpcomingOperations;
import com.meniga.sdk.models.user.MenigaUser;
import com.meniga.sdk.models.user.MenigaUserProfile;
import com.meniga.sdk.models.user.operators.MenigaUserOperations;
import com.meniga.sdk.models.user.operators.MenigaUserProfileOperations;
import com.meniga.sdk.models.userevents.MenigaUserEvent;
import com.meniga.sdk.models.userevents.operators.MenigaUserEventsOperations;
import com.meniga.sdk.providers.BasicPersistanceProviderNone;
import com.meniga.sdk.webservices.PersistenceDelegate;
import com.meniga.sdk.webservices.WebClient;

import org.joda.time.DateTimeZone;

import javax.inject.Inject;

/**
 * Root of the Meniga SDK project.
 */
@SuppressWarnings("WeakerAccess")
public class MenigaSDK {
	private static PersistenceDelegate persistenceDelegate;

	private static MenigaSettings settings;

	@Inject
	WebClient webClient;
	@Inject
	MenigaTransactionOperations transactionOperator;
	@Inject
	MenigaAccountOperations accountOperator;
	@Inject
	MenigaTagOperations tagOperator;
	@Inject
	MenigaSyncOperations syncOperator;
	@Inject
	MenigaFeedOperations feedOperator;
	@Inject
	MenigaUserProfileOperations userProfileOperator;
	@Inject
	MenigaCategoryOperations categoryOperations;
	@Inject
	MenigaTransactionSeriesOperations transactionSeriesOperator;
	@Inject
	MenigaCommentOperations commentsOperator;
	@Inject
	MenigaUserOperations userOperator;
	@Inject
	MenigaOfferOperations offerOperator;
	@Inject
	MenigaNetWorthOperations netWorthOperator;
	@Inject
	MenigaTransactionRuleOperations transactionRuleOperator;
	@Inject
	MenigaNetWorthBalanceOperations netWorthBalanceOperations;
	@Inject
	MenigaMerchantOperations merchantOperations;
	@Inject
	MenigaTopMerchantOperations merchantTopOperations;
	@Inject
	MenigaPublicSettingsOperations publicOperations;
	@Inject
	MenigaTermsOperations termsOperations;
	@Inject
	MenigaUpcomingOperations upcomingOperations;
	@Inject
	MenigaBudgetOperations budgetOperations;
	@Inject
	MenigaChallengesOperations challengesOperations;
	@Inject
	MenigaRedemptionsOperations redemptionsOperations;
	@Inject
	MenigaReimbursementAccountOperations reimbursementAccountOperations;
	@Inject
	MenigaUserEventsOperations userEventsOperator;
	@Inject
	MenigaOrganizationOperations organizationsOperations;
	@Inject
	MenigaRealmOperations realmOperations;
	@Inject
	MenigaEventTrackingOperations eventTrackingOperations;

	/**
	 * Returns the current MenigaAPI client.
	 *
	 * @return current MenigaAPI client.
	 */
	public static PersistenceDelegate executor() {
		return MenigaSDK.persistenceDelegate;
	}

	private void initInjector() {
		MenigaClientModule.build().inject(getClass(), this);

		MenigaTransaction.setOperator(transactionOperator);
		MenigaAccount.setOperator(accountOperator);
		MenigaTag.setOperator(tagOperator);
		MenigaSync.setOperator(syncOperator);
		MenigaFeed.setOperator(feedOperator);
		MenigaUserProfile.setOperator(userProfileOperator);
		MenigaCategory.setOperator(categoryOperations);
		MenigaUserCategory.setOperator(categoryOperations);
		MenigaTransactionSeries.setOperator(transactionSeriesOperator);
		MenigaComment.setOperator(commentsOperator);
		MenigaUser.setOperator(userOperator);
		MenigaUserEvent.setOperator(userEventsOperator);
		MenigaOffer.setOperator(offerOperator);
		MenigaOffersSettings.setOperator(offerOperator);
		MenigaReimbursementAccount.setOperator(reimbursementAccountOperations);
		MenigaNetWorth.setOperator(netWorthOperator);
		MenigaTransactionRule.setOperator(transactionRuleOperator);
		MenigaNetWorthBalance.setOperator(netWorthBalanceOperations);
		MenigaMerchant.setOperator(merchantOperations);
		MenigaTopMerchant.setOperator(merchantTopOperations);
		MenigaPublicSettings.setOperator(publicOperations);
		MenigaTerms.setOperator(termsOperations);
		MenigaUpcoming.setOperator(upcomingOperations);
		MenigaRedemptions.setOperator(redemptionsOperations);
		MenigaBudget.setOperator(budgetOperations);
		MenigaBudgetEntry.setOperator(budgetOperations);
		MenigaBudgetRule.setOperator(budgetOperations);
		MenigaChallenge.setOperator(challengesOperations);
		MenigaOrganization.setOperator(organizationsOperations);
		MenigaRealm.setOperator(realmOperations);
		MenigaScheduledEvent.setOperator(feedOperator);
		MenigaEventTracking.setOperator(eventTrackingOperations);
	}

	/**
	 * This is the entry point to the SDK. Pass in a MenigaServerSettings and after initialization the SDK can be used.
	 *
	 * @param settings MenigaServerSettings object that has all the required settings set.
	 */
	public static void init(MenigaSettings settings) {
		MenigaSDK sdk = new MenigaSDK();
		sdk.initInjector();

		DateTimeZone.setDefault(DateTimeZone.UTC);

		MenigaSDK.settings = settings;
		MenigaSDK.persistenceDelegate = new PersistenceDelegate();
		PersistenceProvider provider = settings.getPersistenceProvider();
		if (provider == null) {
			provider = new BasicPersistanceProviderNone();
		}
		MenigaSDK.persistenceDelegate.setProvider(provider);
		MenigaSDK.persistenceDelegate.setApis(sdk.webClient.createApiInterfaces(settings));
	}

	/**
	 * Clears all settings and flat and resets the SDK.
	 * Only used internally.
	 */
	public static void clearSettings() {
		MenigaSDK.settings = null;
	}

	/**
	 * Returns the current Meniga settings used by the SDK.
	 *
	 * @return The current MenigaServerSettings object.
	 */
	public static MenigaSettings getMenigaSettings() {
		return MenigaSDK.settings;
	}
}
