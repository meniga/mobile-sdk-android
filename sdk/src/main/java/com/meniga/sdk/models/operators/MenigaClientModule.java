package com.meniga.sdk.models.operators;

import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations;
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperationsImp;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperationsImp;
import com.meniga.sdk.models.categories.operators.MenigaCategoryOperations;
import com.meniga.sdk.models.categories.operators.MenigaCategoryOperationsImp;
import com.meniga.sdk.models.challenges.operators.MenigaChallengesOperations;
import com.meniga.sdk.models.challenges.operators.MenigaChallengesOperationsImp;
import com.meniga.sdk.models.feed.operators.MenigaFeedOperations;
import com.meniga.sdk.models.feed.operators.MenigaFeedOperationsImp;
import com.meniga.sdk.models.merchants.operators.MenigaMerchantOperations;
import com.meniga.sdk.models.merchants.operators.MenigaMerchantOperationsImp;
import com.meniga.sdk.models.merchants.operators.MenigaTopMerchantOperations;
import com.meniga.sdk.models.merchants.operators.MenigaTopMerchantOperationsImp;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthBalanceOperations;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthBalanceOperationsImp;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthOperations;
import com.meniga.sdk.models.networth.operators.MenigaNetWorthOperationsImp;
import com.meniga.sdk.models.offers.operators.MenigaOfferOperations;
import com.meniga.sdk.models.offers.operators.MenigaOfferOperationsImp;
import com.meniga.sdk.models.offers.redemptions.operators.MenigaRedemptionsOperations;
import com.meniga.sdk.models.offers.redemptions.operators.MenigaRedemptionsOperationsImp;
import com.meniga.sdk.models.offers.reimbursementaccounts.operators.MenigaReimbursementAccountOperations;
import com.meniga.sdk.models.offers.reimbursementaccounts.operators.MenigaReimbursementAccountOperationsImp;
import com.meniga.sdk.models.organizations.operators.MenigaOrganizationOperations;
import com.meniga.sdk.models.organizations.operators.MenigaOrganizationOperationsImp;
import com.meniga.sdk.models.organizations.operators.MenigaRealmOperations;
import com.meniga.sdk.models.organizations.operators.MenigaRealmOperationsImp;
import com.meniga.sdk.models.serverpublic.operators.MenigaPublicSettingsOperations;
import com.meniga.sdk.models.serverpublic.operators.MenigaPublicSettingsOperationsImp;
import com.meniga.sdk.models.terms.operators.MenigaTermsOperations;
import com.meniga.sdk.models.terms.operators.MenigaTermsOperationsImp;
import com.meniga.sdk.models.transactions.operators.MenigaCommentOperations;
import com.meniga.sdk.models.transactions.operators.MenigaCommentOperationsImp;
import com.meniga.sdk.models.transactions.operators.MenigaTagOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTagOperationsImp;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionOperationsImp;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionRuleOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionRuleOperationsImp;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionSeriesOperations;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionSeriesOperationsImp;
import com.meniga.sdk.models.upcoming.operators.MenigaUpcomingOperations;
import com.meniga.sdk.models.upcoming.operators.MenigaUpcomingOperationsImp;
import com.meniga.sdk.models.sync.operators.MenigaSyncOperations;
import com.meniga.sdk.models.sync.operators.MenigaSyncOperationsImp;
import com.meniga.sdk.models.user.operators.MenigaUserOperations;
import com.meniga.sdk.models.user.operators.MenigaUserOperationsImp;
import com.meniga.sdk.models.user.operators.MenigaUserProfileOperations;
import com.meniga.sdk.models.user.operators.MenigaUserProfileOperationsImp;
import com.meniga.sdk.models.userevents.operators.MenigaUserEventsOperations;
import com.meniga.sdk.models.userevents.operators.MenigaUserEventsOperationsImp;
import com.meniga.sdk.webservices.WebClient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaClientModule {

	private List<Method> provides;

	public static MenigaClientModule build() {
		return new MenigaClientModule();
	}

	public void inject(Class injectClass, Object injectee) {

		for (Field field : injectClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Inject.class)) {
				try {
					field.setAccessible(true);
					field.set(injectee, this.getProvidesObject(field));
				} catch (Exception ex) {
					ErrorHandler.reportAndHandle(ex);
				}
			}
		}
		this.clear();
	}

	private Object getProvidesObject(Field field) throws InvocationTargetException, IllegalAccessException {
		if (this.provides == null)
			this.createProvides();

		for (Method provide : this.provides) {
			String methodName = provide.getReturnType().getName();
			String fieldName = field.getType().getName();
			if (methodName.endsWith(fieldName)) {
				return provide.invoke(this);
			}
		}
		return null;
	}

	private void clear() {
		this.provides.clear();
		this.provides = null;
	}

	private void createProvides() {
		this.provides = new ArrayList<>();
		Method[] methods = MenigaClientModule.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Provides.class))
				this.provides.add(method);
		}
	}

	@Provides
	private WebClient provideWebClient() {
		return new WebClient();
	}

	@Provides
	private MenigaTransactionOperations provideMenigaTransactionOperations() {
		return new MenigaTransactionOperationsImp();
	}

	@Provides
	private MenigaAccountOperations provideMenigaAccountOperations() {
		return new MenigaAccountOperationsImp();
	}

	@Provides
	private MenigaTagOperations provideMenigaTagOperations() {
		return new MenigaTagOperationsImp();
	}

	@Provides
	private MenigaSyncOperations provideMenigaSynchronizationOperations() {
		return new MenigaSyncOperationsImp();
	}

	@Provides
	private MenigaUserEventsOperations provideMenigaUserEventsOperations() {
		return new MenigaUserEventsOperationsImp();
	}

	@Provides
	private MenigaFeedOperations provideMenigaFeedOperations() {
		return new MenigaFeedOperationsImp();
	}

	@Provides
	MenigaUserProfileOperations provideMenigaUserProfileOperations() {
		return new MenigaUserProfileOperationsImp();
	}

	@Provides
	private MenigaCategoryOperations provideMenigaCategoryOperations() {
		return new MenigaCategoryOperationsImp();
	}

	@Provides
	private MenigaTransactionSeriesOperations provideMenigaTransactionSeriesOperations() {
		return new MenigaTransactionSeriesOperationsImp();
	}

	@Provides
	public MenigaCommentOperations provideMenigaCommentOperations() {
		return new MenigaCommentOperationsImp();
	}

	@Provides
	public MenigaUserOperations provideMenigaUserOperations() {
		return new MenigaUserOperationsImp();
	}

	@Provides
	private MenigaOfferOperations provideMenigaOfferOperations() {
		return new MenigaOfferOperationsImp();
	}

	@Provides
	private MenigaNetWorthOperations provideMenigaNetWorthOperations() {
		return new MenigaNetWorthOperationsImp();
	}

	@Provides
	private MenigaTransactionRuleOperations provideMenigaTransactionRuleOperations() {
		return new MenigaTransactionRuleOperationsImp();
	}

	@Provides
	private MenigaNetWorthBalanceOperations provideMenigaNetWorthBalanceOperations() {
		return new MenigaNetWorthBalanceOperationsImp();
	}

	@Provides
	private MenigaRedemptionsOperations provideMenigaRedemptionsOperations() {
		return new MenigaRedemptionsOperationsImp();
	}

	@Provides
	private MenigaMerchantOperations provideMenigaMerchantOperations() {
		return new MenigaMerchantOperationsImp();
	}

	@Provides
	private MenigaTopMerchantOperations provideMenigaTopMerchantOperations() {
		return new MenigaTopMerchantOperationsImp();
	}

	@Provides
	private MenigaPublicSettingsOperations provideMenigaPublicOperations() {
		return new MenigaPublicSettingsOperationsImp();
	}

	@Provides
	private MenigaTermsOperations provideMenigaTermsOperations() {
		return new MenigaTermsOperationsImp();
	}

	@Provides
	private MenigaUpcomingOperations provideMenigaUpcomingOperations() {
		return new MenigaUpcomingOperationsImp();
	}

	@Provides
	private MenigaBudgetOperations provideMenigaBudgetOperations() {
		return new MenigaBudgetOperationsImp();
	}

	@Provides
	private MenigaChallengesOperations provideMenigaChallengesOperations() {
		return new MenigaChallengesOperationsImp();
	}

	@Provides
	private MenigaReimbursementAccountOperations provideMenigaReimbursementAccountOperations() {
		return new MenigaReimbursementAccountOperationsImp();
	}

	@Provides
	private MenigaOrganizationOperations provideMenigaOrganizationOperations() {
		return new MenigaOrganizationOperationsImp();
	}

	@Provides
	private MenigaRealmOperations provideMenigaRealmOperations() {
		return new MenigaRealmOperationsImp();
	}
}
