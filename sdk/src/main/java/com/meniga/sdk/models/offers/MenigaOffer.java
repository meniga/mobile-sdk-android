package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.enums.OfferFilterState;
import com.meniga.sdk.models.offers.enums.OfferState;
import com.meniga.sdk.models.offers.enums.RewardType;
import com.meniga.sdk.models.offers.operators.MenigaOfferOperations;
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@SuppressWarnings("unused")
public class MenigaOffer implements Parcelable {

	protected static MenigaOfferOperations apiOperator;

	protected long id;
	protected String title;
	protected String description;
	protected Long brandId;
	protected String brandName;
	protected String validationToken;
	protected OfferState state;
	protected RewardType rewardType;
	protected MenigaDecimal reward;
	protected MenigaDecimal totalRedeemedAmount;
	protected MenigaDecimal minimumPurchaseAmount;
	protected MenigaDecimal maximumRedemptionPerOffer;
	protected MenigaDecimal maximumRedemptionPerPurchase;
	protected MenigaDecimal minimumAccumulatedAmount;
	protected Integer maximumPurchase;
	protected MenigaDecimal lastReimbursementAmount;
	protected DateTime lastReimbursementDate;
	protected MenigaDecimal scheduledReimbursementAmount;
	protected DateTime scheduledReimbursementDate;
	protected int daysLeft;
	protected DateTime validFrom;
	protected DateTime validTo;
	protected DateTime activatedDate;
	protected DateTime declineDate;
	protected Long merchantId;
	protected String merchantName;
	protected boolean merchantDeclined;
	protected boolean activateOfferOnFirstPurchase;
	protected RelevanceHookDisplay relevanceHook;
	protected MenigaDecimal totalSpendingAtSimilarBrands;
	protected MenigaDecimal totalSpendingOnOffer;
	protected MenigaDecimal offerSimilarBrandsSpendingRatio;
	protected List<MenigaOfferMerchantLocation> merchantLocations;
	protected String webUri;
	protected String webUriLabel;

	protected MenigaOffer() {
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaOfferOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaOfferOperations operator) {
		MenigaOffer.apiOperator = operator;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Long getBrandId() {
		return brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getValidationToken() {
		return validationToken;
	}

	public OfferState getState() {
		return state;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public MenigaDecimal getReward() {
		if (reward == null) {
			return new MenigaDecimal(0);
		}
		return reward;
	}

	public MenigaDecimal getTotalRedeemedAmount() {
		return totalRedeemedAmount;
	}

	public MenigaDecimal getMinimumPurchaseAmount() {
		return minimumPurchaseAmount;
	}

	public MenigaDecimal getMaximumRedemptionPerOffer() {
		return maximumRedemptionPerOffer;
	}

	public MenigaDecimal getMaximumRedemptionPerPurchase() {
		return maximumRedemptionPerPurchase;
	}

	public MenigaDecimal getMinimumAccumulatedAmount() {
		return minimumAccumulatedAmount;
	}

	public Integer getMaximumPurchase() {
		return maximumPurchase;
	}

	public MenigaDecimal getLastReimbursementAmount() {
		return lastReimbursementAmount;
	}

	public DateTime getLastReimbursementDate() {
		return lastReimbursementDate;
	}

	public MenigaDecimal getScheduledReimbursementAmount() {
		return scheduledReimbursementAmount;
	}

	public DateTime getScheduledReimbursementDate() {
		return scheduledReimbursementDate;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public DateTime getValidFrom() {
		return validFrom;
	}

	public DateTime getValidTo() {
		return validTo;
	}

	public DateTime getDeclineDate() {
		return declineDate;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public boolean isMerchantDeclined() {
		return merchantDeclined;
	}

	public boolean isActivateOfferOnFirstPurchase() {
		return activateOfferOnFirstPurchase;
	}

	public RelevanceHookDisplay getRelevanceHook() {
		return relevanceHook;
	}

	public MenigaDecimal getTotalSpendingAtSimilarBrands() {
		return totalSpendingAtSimilarBrands;
	}

	public MenigaDecimal getTotalSpendingOnOffer() {
		return totalSpendingOnOffer;
	}

	public MenigaDecimal getOfferSimilarBrandsSpendingRatio() {
		return offerSimilarBrandsSpendingRatio;
	}

	public DateTime getActivatedDate() {
		return activatedDate;
	}

	public List<MenigaOfferMerchantLocation> getMerchantLocations() {
		return merchantLocations;
	}

	public String getWebUri() {
		return webUri;
	}

	public String getWebUriLabel() {
		return webUriLabel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaOffer that = (MenigaOffer) o;

		if (id != that.id) return false;
		if (daysLeft != that.daysLeft) return false;
		if (merchantDeclined != that.merchantDeclined) return false;
		if (activateOfferOnFirstPurchase != that.activateOfferOnFirstPurchase) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
		if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null)
			return false;
		if (validationToken != null ? !validationToken.equals(that.validationToken) : that.validationToken != null)
			return false;
		if (state != that.state) return false;
		if (rewardType != that.rewardType) return false;
		if (reward != null ? !reward.equals(that.reward) : that.reward != null) return false;
		if (totalRedeemedAmount != null ? !totalRedeemedAmount.equals(that.totalRedeemedAmount) : that.totalRedeemedAmount != null)
			return false;
		if (minimumPurchaseAmount != null ? !minimumPurchaseAmount.equals(that.minimumPurchaseAmount) : that.minimumPurchaseAmount != null)
			return false;
		if (maximumRedemptionPerOffer != null ? !maximumRedemptionPerOffer.equals(that.maximumRedemptionPerOffer) : that.maximumRedemptionPerOffer != null)
			return false;
		if (maximumRedemptionPerPurchase != null ? !maximumRedemptionPerPurchase.equals(that.maximumRedemptionPerPurchase) : that.maximumRedemptionPerPurchase != null)
			return false;
		if (minimumAccumulatedAmount != null ? !minimumAccumulatedAmount.equals(that.minimumAccumulatedAmount) : that.minimumAccumulatedAmount != null)
			return false;
		if (maximumPurchase != null ? !maximumPurchase.equals(that.maximumPurchase) : that.maximumPurchase != null)
			return false;
		if (lastReimbursementAmount != null ? !lastReimbursementAmount.equals(that.lastReimbursementAmount) : that.lastReimbursementAmount != null)
			return false;
		if (lastReimbursementDate != null ? !lastReimbursementDate.equals(that.lastReimbursementDate) : that.lastReimbursementDate != null)
			return false;
		if (scheduledReimbursementAmount != null ? !scheduledReimbursementAmount.equals(that.scheduledReimbursementAmount) : that.scheduledReimbursementAmount != null)
			return false;
		if (scheduledReimbursementDate != null ? !scheduledReimbursementDate.equals(that.scheduledReimbursementDate) : that.scheduledReimbursementDate != null)
			return false;
		if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null)
			return false;
		if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null) return false;
		if (activatedDate != null ? !activatedDate.equals(that.activatedDate) : that.activatedDate != null)
			return false;
		if (declineDate != null ? !declineDate.equals(that.declineDate) : that.declineDate != null)
			return false;
		if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
			return false;
		if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null)
			return false;
		if (relevanceHook != null ? !relevanceHook.equals(that.relevanceHook) : that.relevanceHook != null)
			return false;
		if (totalSpendingAtSimilarBrands != null ? !totalSpendingAtSimilarBrands.equals(that.totalSpendingAtSimilarBrands) : that.totalSpendingAtSimilarBrands != null)
			return false;
		if (totalSpendingOnOffer != null ? !totalSpendingOnOffer.equals(that.totalSpendingOnOffer) : that.totalSpendingOnOffer != null)
			return false;
		if (offerSimilarBrandsSpendingRatio != null ? !offerSimilarBrandsSpendingRatio.equals(that.offerSimilarBrandsSpendingRatio) : that.offerSimilarBrandsSpendingRatio != null)
			return false;
		if (merchantLocations != null ? !merchantLocations.equals(that.merchantLocations) : that.merchantLocations != null)
			return false;
		if (webUri != null ? !webUri.equals(that.webUri) : that.webUri != null) return false;
		return webUriLabel != null ? webUriLabel.equals(that.webUriLabel) : that.webUriLabel == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
		result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
		result = 31 * result + (validationToken != null ? validationToken.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (rewardType != null ? rewardType.hashCode() : 0);
		result = 31 * result + (reward != null ? reward.hashCode() : 0);
		result = 31 * result + (totalRedeemedAmount != null ? totalRedeemedAmount.hashCode() : 0);
		result = 31 * result + (minimumPurchaseAmount != null ? minimumPurchaseAmount.hashCode() : 0);
		result = 31 * result + (maximumRedemptionPerOffer != null ? maximumRedemptionPerOffer.hashCode() : 0);
		result = 31 * result + (maximumRedemptionPerPurchase != null ? maximumRedemptionPerPurchase.hashCode() : 0);
		result = 31 * result + (minimumAccumulatedAmount != null ? minimumAccumulatedAmount.hashCode() : 0);
		result = 31 * result + (maximumPurchase != null ? maximumPurchase.hashCode() : 0);
		result = 31 * result + (lastReimbursementAmount != null ? lastReimbursementAmount.hashCode() : 0);
		result = 31 * result + (lastReimbursementDate != null ? lastReimbursementDate.hashCode() : 0);
		result = 31 * result + (scheduledReimbursementAmount != null ? scheduledReimbursementAmount.hashCode() : 0);
		result = 31 * result + (scheduledReimbursementDate != null ? scheduledReimbursementDate.hashCode() : 0);
		result = 31 * result + daysLeft;
		result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
		result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
		result = 31 * result + (activatedDate != null ? activatedDate.hashCode() : 0);
		result = 31 * result + (declineDate != null ? declineDate.hashCode() : 0);
		result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
		result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
		result = 31 * result + (merchantDeclined ? 1 : 0);
		result = 31 * result + (activateOfferOnFirstPurchase ? 1 : 0);
		result = 31 * result + (relevanceHook != null ? relevanceHook.hashCode() : 0);
		result = 31 * result + (totalSpendingAtSimilarBrands != null ? totalSpendingAtSimilarBrands.hashCode() : 0);
		result = 31 * result + (totalSpendingOnOffer != null ? totalSpendingOnOffer.hashCode() : 0);
		result = 31 * result + (offerSimilarBrandsSpendingRatio != null ? offerSimilarBrandsSpendingRatio.hashCode() : 0);
		result = 31 * result + (merchantLocations != null ? merchantLocations.hashCode() : 0);
		result = 31 * result + (webUri != null ? webUri.hashCode() : 0);
		result = 31 * result + (webUriLabel != null ? webUriLabel.hashCode() : 0);
		return result;
	}

/*
	 * Api methods below
	 */

	/**
	 * Returns offers and relevant meta data.
	 *
	 * @param skip                      how many offers should be skipped
	 * @param take                      how many offers should be taken per page
	 * @param states                    state of offers that should be fetched.
	 * @param offerIds                  specific offers ids to fetch
	 * @param expiredWithRedemptionOnly Fetch only excpired offers that have redemptions
	 * @return a page of offers.
	 */
	public static Result<MenigaOfferPage> fetch(final int skip, final int take, final List<OfferFilterState> states, final List<Long> offerIds, final boolean expiredWithRedemptionOnly) {
		Result<MenigaOfferPage> task = MenigaOffer.apiOperator.getOffers(skip, take, states, offerIds, expiredWithRedemptionOnly);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaOfferPage>() {
			@Override
			public void onFinished(MenigaOfferPage result, boolean failed) {
				if (!failed && result != null) {
					result.skip = skip;
					result.take = take;
					result.states = states;
					result.offerIds = offerIds;
					result.expiredWithRedemptionOnly = expiredWithRedemptionOnly;
					if (result.size() >= result.getTotalCount()) {
						result.hasMorePages = false;
					}
				}
			}
		});
	}

	/**
	 * Returns offer with specific id
	 *
	 * @param id specific offer id
	 * @return offer
	 */
	public static Result<MenigaOffer> fetch(long id) {
		return MenigaOffer.apiOperator.getOfferById(id);
	}

	/**
	 * Returns offer with specific validation token
	 *
	 * @param validationToken specific offer validation token
	 * @return offer
	 */
	public static Result<MenigaOffer> fetch(String validationToken) {
		return MenigaOffer.apiOperator.getOfferByToken(validationToken);
	}

	/**
	 * Returns offers and relevant meta data.
	 *
	 * @param states state of offers that should be fetched.
	 * @return a page of offers
	 */
	public static Result<MenigaOfferPage> fetch(final List<OfferFilterState> states) {
		final int take = 100;
		final int skip = 0;
		Result<MenigaOfferPage> task = MenigaOffer.apiOperator.getOffers(skip, take, states, Collections.singletonList(0L), false);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaOfferPage>() {
			@Override
			public void onFinished(MenigaOfferPage result, boolean failed) {
				if (!failed && result != null) {
					result.skip = skip;
					result.take = take;
					result.states = states;
					if (result.size() >= result.getTotalCount()) {
						result.hasMorePages = false;
					}
				}
			}
		});
	}

	/**
	 * Returns offers and relevant meta data by specific ids
	 *
	 * @param offerIds specific offer ids to fetch.
	 * @return a page of offers
	 */
	public static Result<MenigaOfferPage> fetchId(final List<Long> offerIds) {
		final int take = 100;
		final int skip = 0;
		Result<MenigaOfferPage> task = MenigaOffer.apiOperator.getOffers(skip, take, null, offerIds, false);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaOfferPage>() {
			@Override
			public void onFinished(MenigaOfferPage result, boolean failed) {
				if (!failed && result != null) {
					result.skip = skip;
					result.take = take;
					result.offerIds = offerIds;
					if (result.size() >= result.getTotalCount()) {
						result.hasMorePages = false;
					}
				}
			}
		});
	}

	/**
	 * Returns offers and relevant meta data
	 *
	 * @param skip number of records to skip
	 * @param take Number of records to return
	 * @return a page of offers
	 */
	public static Result<MenigaOfferPage> fetch(final int skip, final int take) {
		Result<MenigaOfferPage> task = MenigaOffer.apiOperator.getOffers(skip, take, Collections.singletonList(OfferFilterState.ALL), Collections.singletonList(0L), false);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaOfferPage>() {
			@Override
			public void onFinished(MenigaOfferPage result, boolean failed) {
				if (!failed && result != null) {
					result.skip = skip;
					result.take = take;
					if (result.size() >= result.getTotalCount()) {
						result.hasMorePages = false;
					}
				}
			}
		});
	}

	/**
	 * Activate offer by offer id
	 *
	 * @return task
	 */
	public Result<Void> activate() {
		Result<Void> task = MenigaOffer.apiOperator.activateById(id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<Void>() {
			@Override
			public void onFinished(Void result, boolean failed) {
				if (!failed) {
					state = OfferState.ACTIVATED;
				}
			}
		});
	}

	/**
	 * Activate offer by offer by validation token
	 *
	 * @return task
	 */
	public Result<Void> activateByToken() {
		Result<Void> task = MenigaOffer.activateByToken(validationToken);
		TaskCompletionSource<Void> tsc = new TaskCompletionSource<>();
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<Void>() {
			@Override
			public void onFinished(Void result, boolean failed) {
				if (!failed) {
					state = OfferState.ACTIVATED;
				}
			}
		});
	}

	public static Result<Void> activateByToken(String validationToken) {
		return MenigaOffer.apiOperator.activateByToken(validationToken);
	}

	/**
	 * Mark offer as seen
	 *
	 * @param offerId id of offer
	 * @return Task with void result
	 */
	public static Result<Void> seen(long offerId) {
		return MenigaOffer.apiOperator.markAsSeen(offerId);
	}

	/**
	 * Decline offer
	 *
	 * @return task
	 */
	public Result<Void> decline() {
		return MenigaOffer.apiOperator.deactivate(id);
	}

	/**
	 * Mark offer as seen
	 *
	 * @return task
	 */
	public Result<Void> markAsSeen() {
		return MenigaOffer.apiOperator.markAsSeen(id);
	}

	/**
	 * Fetch similar brand spending details
	 *
	 * @return similar brand spending details
	 */
	public Result<MenigaSimilarBrandSpendingDetails> fetchSimilarBrandSpendingDetails() {
		return MenigaOffer.apiOperator.similarBrandSpendingDetails(id);
	}

	/**
	 * Fetch similar brand spending details by offer id
	 *
	 * @param id offer id of the offer for which the details are to be retrieved
	 * @return similar brand spending details
	 */
	public static Result<MenigaSimilarBrandSpendingDetails> fetchSimilarBrandSpendingDetailsById(int id) {
		return MenigaOffer.apiOperator.similarBrandSpendingDetails(id);
	}

	/**
	 * Fetch redemption transactions
	 *
	 * @return list of redemption transactions
	 */
	public Result<MenigaRedemptions> fetchRedemptions() {
		return MenigaOffer.apiOperator.getRedemptionsById(id);
	}

	/**
	 * Fetch redemption transactions by offer id
	 *
	 * @param id offer id of the offer for which the redemption transactions are to be retrieved
	 * @return list of redemption transactions
	 */
	public static Result<MenigaRedemptions> fetchRedemptionsById(int id) {
		return MenigaOffer.apiOperator.getRedemptionsById(id);
	}

	/**
	 * Fetch nearby merchant locations
	 *
	 * @param latitude       central latitude for location filtering
	 * @param longitude      central longitude for location filtering
	 * @param radiumKm       radius in km for location filtering
	 * @param limitLocations the maximum number of locations to be returned (per offer)
	 * @return list of redemption transactions
	 */
	public Result<MenigaOfferMerchantLocationPage> fetchNearbyMerchantLocations(Double latitude, Double longitude, Double radiumKm, Integer limitLocations) {
		return MenigaOffer.apiOperator.getMerchantLocationsById(id, latitude, longitude, radiumKm, limitLocations);
	}

	/**
	 * Fetch nearby merchant locations by offer id
	 *
	 * @param id             offer id of the offer for which the locations are to be retrieved
	 * @param latitude       central latitude for location filtering
	 * @param longitude      central longitude for location filtering
	 * @param radiumKm       radius in km for location filtering
	 * @param limitLocations the maximum number of locations to be returned (per offer)
	 * @return list of redemption transactions
	 */
	public static Result<MenigaOfferMerchantLocationPage> fetchNearbyMerchantLocationsById(int id, Double latitude, Double longitude, Double radiumKm, Integer limitLocations) {
		return MenigaOffer.apiOperator.getMerchantLocationsById(id, latitude, longitude, radiumKm, limitLocations);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.title);
		dest.writeString(this.description);
		dest.writeValue(this.brandId);
		dest.writeString(this.brandName);
		dest.writeString(this.validationToken);
		dest.writeInt(this.state == null ? -1 : this.state.ordinal());
		dest.writeInt(this.rewardType == null ? -1 : this.rewardType.ordinal());
		dest.writeSerializable(this.reward);
		dest.writeSerializable(this.totalRedeemedAmount);
		dest.writeSerializable(this.minimumPurchaseAmount);
		dest.writeSerializable(this.maximumRedemptionPerOffer);
		dest.writeSerializable(this.maximumRedemptionPerPurchase);
		dest.writeSerializable(this.minimumAccumulatedAmount);
		dest.writeValue(this.maximumPurchase);
		dest.writeSerializable(this.lastReimbursementAmount);
		dest.writeSerializable(this.lastReimbursementDate);
		dest.writeSerializable(this.scheduledReimbursementAmount);
		dest.writeSerializable(this.scheduledReimbursementDate);
		dest.writeInt(this.daysLeft);
		dest.writeSerializable(this.validFrom);
		dest.writeSerializable(this.validTo);
		dest.writeSerializable(this.activatedDate);
		dest.writeSerializable(this.declineDate);
		dest.writeValue(this.merchantId);
		dest.writeString(this.merchantName);
		dest.writeByte(this.merchantDeclined ? (byte) 1 : (byte) 0);
		dest.writeByte(this.activateOfferOnFirstPurchase ? (byte) 1 : (byte) 0);
		dest.writeParcelable(this.relevanceHook, flags);
		dest.writeSerializable(this.totalSpendingAtSimilarBrands);
		dest.writeSerializable(this.totalSpendingOnOffer);
		dest.writeSerializable(this.offerSimilarBrandsSpendingRatio);
		dest.writeTypedList(this.merchantLocations);
		dest.writeString(this.webUri);
		dest.writeString(this.webUriLabel);
	}

	protected MenigaOffer(Parcel in) {
		this.id = in.readLong();
		this.title = in.readString();
		this.description = in.readString();
		this.brandId = (Long) in.readValue(Long.class.getClassLoader());
		this.brandName = in.readString();
		this.validationToken = in.readString();
		int tmpState = in.readInt();
		this.state = tmpState == -1 ? null : OfferState.values()[tmpState];
		int tmpRewardType = in.readInt();
		this.rewardType = tmpRewardType == -1 ? null : RewardType.values()[tmpRewardType];
		this.reward = (MenigaDecimal) in.readSerializable();
		this.totalRedeemedAmount = (MenigaDecimal) in.readSerializable();
		this.minimumPurchaseAmount = (MenigaDecimal) in.readSerializable();
		this.maximumRedemptionPerOffer = (MenigaDecimal) in.readSerializable();
		this.maximumRedemptionPerPurchase = (MenigaDecimal) in.readSerializable();
		this.minimumAccumulatedAmount = (MenigaDecimal) in.readSerializable();
		this.maximumPurchase = (Integer) in.readValue(Integer.class.getClassLoader());
		this.lastReimbursementAmount = (MenigaDecimal) in.readSerializable();
		this.lastReimbursementDate = (DateTime) in.readSerializable();
		this.scheduledReimbursementAmount = (MenigaDecimal) in.readSerializable();
		this.scheduledReimbursementDate = (DateTime) in.readSerializable();
		this.daysLeft = in.readInt();
		this.validFrom = (DateTime) in.readSerializable();
		this.validTo = (DateTime) in.readSerializable();
		this.activatedDate = (DateTime) in.readSerializable();
		this.declineDate = (DateTime) in.readSerializable();
		this.merchantId = (Long) in.readValue(Long.class.getClassLoader());
		this.merchantName = in.readString();
		this.merchantDeclined = in.readByte() != 0;
		this.activateOfferOnFirstPurchase = in.readByte() != 0;
		this.relevanceHook = in.readParcelable(RelevanceHookDisplay.class.getClassLoader());
		this.totalSpendingAtSimilarBrands = (MenigaDecimal) in.readSerializable();
		this.totalSpendingOnOffer = (MenigaDecimal) in.readSerializable();
		this.offerSimilarBrandsSpendingRatio = (MenigaDecimal) in.readSerializable();
		this.merchantLocations = in.createTypedArrayList(MenigaOfferMerchantLocation.CREATOR);
		this.webUri = in.readString();
		this.webUriLabel = in.readString();
	}

	public static final Creator<MenigaOffer> CREATOR = new Creator<MenigaOffer>() {
		@Override
		public MenigaOffer createFromParcel(Parcel source) {
			return new MenigaOffer(source);
		}

		@Override
		public MenigaOffer[] newArray(int size) {
			return new MenigaOffer[size];
		}
	};
}
