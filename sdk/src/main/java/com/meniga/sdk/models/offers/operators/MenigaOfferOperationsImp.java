package com.meniga.sdk.models.offers.operators;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.MenigaOffer;
import com.meniga.sdk.models.offers.MenigaOfferMerchantLocationPage;
import com.meniga.sdk.models.offers.MenigaOfferPage;
import com.meniga.sdk.models.offers.MenigaSimilarBrandSpendingDetails;
import com.meniga.sdk.models.offers.enums.OfferFilterState;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.webservices.requests.AcceptOffersTermsAndConditions;
import com.meniga.sdk.webservices.requests.ActivateOfferById;
import com.meniga.sdk.webservices.requests.ActivateOfferByToken;
import com.meniga.sdk.webservices.requests.DeclineOffer;
import com.meniga.sdk.webservices.requests.DisableOffers;
import com.meniga.sdk.webservices.requests.EnableOffers;
import com.meniga.sdk.webservices.requests.GetMerchantLocationsByOfferId;
import com.meniga.sdk.webservices.requests.GetOfferById;
import com.meniga.sdk.webservices.requests.GetOfferByToken;
import com.meniga.sdk.webservices.requests.GetOffers;
import com.meniga.sdk.webservices.requests.GetRedemptionsByOfferId;
import com.meniga.sdk.webservices.requests.GetSimilarBrandSpendingDetails;
import com.meniga.sdk.webservices.requests.MarkOfferAsSeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferOperationsImp implements MenigaOfferOperations {

	@Override
	public Result<MenigaOfferPage> getOffers(int skip, int take, List<OfferFilterState> states, List<Long> offerId, boolean expiredCashBackOnly) {
		GetOffers req = new GetOffers();
		if (states == null || states.size() == 0) {
			states = new ArrayList<>();
			states.add(OfferFilterState.ALL);
		}
		req.skip = skip;
		req.take = take;
		req.filterStates = states;
		req.filterOfferIds = offerId;
		req.filterExpiredWithCashBackOnly = expiredCashBackOnly;

		return MenigaSDK.executor().getOffers(req);
	}

	@Override
	public Result<MenigaOffer> getOfferByToken(String token) {
		GetOfferByToken req = new GetOfferByToken(token);

		return MenigaSDK.executor().getOfferByToken(req);
	}

	@Override
	public Result<MenigaOffer> getOfferById(long id) {
		GetOfferById req = new GetOfferById(id);

		return MenigaSDK.executor().getOfferById(req);
	}

	@Override
	public Result<Void> activateById(long id) {
		ActivateOfferById req = new ActivateOfferById(id);

		return MenigaSDK.executor().activateOfferById(req);
	}

	@Override
	public Result<Void> activateByToken(String validationToken) {
		ActivateOfferByToken req = new ActivateOfferByToken(validationToken);

		return MenigaSDK.executor().activateOfferByToken(req);
	}

	@Override
	public Result<Void> markAsSeen(long id) {
		MarkOfferAsSeen req = new MarkOfferAsSeen(id);

		return MenigaSDK.executor().markOfferAsSeen(req);
	}

	@Override
	public Result<Void> acceptTermsAndConditions() {
		AcceptOffersTermsAndConditions req = new AcceptOffersTermsAndConditions();
		return MenigaSDK.executor().acceptTermsAndConditions(req);
	}

	@Override
	public Result<Void> enableOffers() {
		EnableOffers req = new EnableOffers();
		return MenigaSDK.executor().enableOffers(req);
	}

	@Override
	public Result<Void> disableOffers() {
		DisableOffers req = new DisableOffers();
		return MenigaSDK.executor().disableOffers(req);
	}

	@Override
	public Result<Void> deactivate(long id) {
		DeclineOffer req = new DeclineOffer(id);

		return MenigaSDK.executor().declineOffer(req);
	}

	@Override
	public Result<MenigaSimilarBrandSpendingDetails> similarBrandSpendingDetails(long id) {
		GetSimilarBrandSpendingDetails req = new GetSimilarBrandSpendingDetails(id);

		return MenigaSDK.executor().getSimilarBrandSpendingDetails(req);
	}

	@Override
	public Result<MenigaRedemptions> getRedemptionsById(long id) {
		GetRedemptionsByOfferId req = new GetRedemptionsByOfferId(id);
		return MenigaSDK.executor().getRedemptionsByOfferId(req);
	}

	@Override
	public Result<MenigaOfferMerchantLocationPage> getMerchantLocationsById(long id, Double latitude, Double longitude, Double radiumKm, Integer limitLocations) {
		GetMerchantLocationsByOfferId req = new GetMerchantLocationsByOfferId(id);
		req.latitude = latitude;
		req.longitude = longitude;
		req.radiusKm = radiumKm;
		req.limitLocations = limitLocations;
		return MenigaSDK.executor().getMerchantLocationsByOfferId(req);
	}
}
