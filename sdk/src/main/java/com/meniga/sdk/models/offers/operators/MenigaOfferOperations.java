package com.meniga.sdk.models.offers.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.MenigaOffer;
import com.meniga.sdk.models.offers.MenigaOfferMerchantLocationPage;
import com.meniga.sdk.models.offers.MenigaOfferPage;
import com.meniga.sdk.models.offers.MenigaSimilarBrandSpendingDetails;
import com.meniga.sdk.models.offers.enums.OfferFilterState;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.models.transactions.MenigaTransaction;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public interface MenigaOfferOperations {

	Result<MenigaOfferPage> getOffers(int skip, int take, List<OfferFilterState> states, List<Long> offerId, boolean expiredCashBackOnly);

	Result<MenigaOffer> getOfferById(long id);

	Result<MenigaOffer> getOfferByToken(String token);

	Result<List<MenigaTransaction>> getTransactions(long id);

	Result<Void> activateById(long id);

	Result<Void> activateByToken(String token);

	Result<Void> deactivate(long id);

	Result<Void> markAsSeen(long id);

	Result<Void> enableOffers();

	Result<Void> disableOffers();

	Result<Void> acceptTermsAndConditions();

	Result<MenigaSimilarBrandSpendingDetails> similarBrandSpendingDetails(long id);

	Result<MenigaRedemptions> getRedemptionsById(long id);

	Result<MenigaOfferMerchantLocationPage> getMerchantLocationsById(long id, Double latitude, Double longitude, Double radiumKm, Integer limitLocations);
}
