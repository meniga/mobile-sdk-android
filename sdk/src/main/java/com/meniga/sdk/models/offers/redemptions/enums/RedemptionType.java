package com.meniga.sdk.models.offers.redemptions.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum RedemptionType implements Serializable {
	@SerializedName("Unknown")
	UNKNOWN,
	@SerializedName("Full")
	FULL,
	@SerializedName("FullPercentage")
	FULL_PERCENTAGE,
	@SerializedName("FullFixedAmount")
	FULL_FIXED_AMOUNT,
	@SerializedName("FullAccumulated")
	FULL_ACCUMULATED,
	@SerializedName("FullMissingBankClearance")
	FULL_MISSING_BANK_CLEARANCE,
	@SerializedName("FullAccumulatedAdjusted")
	FULL_ACCUMULATED_ADJUSTED,
	@SerializedName("PartOverMaxPerPurchase")
	PART_OVER_MAX_PER_PURCHASE,
	@SerializedName("PartOverMaxOnOffer")
	PART_OVER_MAX_ON_OFFER,
	@SerializedName("PartOverMaxOfferRefundAmount")
	PART_OVER_MAX_OFFER_REFUND_AMOUNT,
	@SerializedName("PartUnderMinAccumulated")
	PART_UNDER_MIN_ACCUMULATED,
	@SerializedName("PartAccumulated")
	PART_ACCUMULATED,
	@SerializedName("PartRefundNetted")
	PART_REFUND_NETTED,
	@SerializedName("ZeroUnderMinPurchaseAmount")
	ZERO_UNDER_MIN_PURCHASE_AMOUNT,
	@SerializedName("ZeroOverMaxOffer")
	ZERO_OVER_MAX_ON_OFFER,
	@SerializedName("ZeroOverMaxOfferRefundAmount")
	ZERO_OVER_MAX_OFFER_REFUND_AMOUNT,
	@SerializedName("ZeroOverMaxNumberOfRedeems")
	ZERO_OVER_MAX_NUMBER_OF_REDEEMS,
	@SerializedName("ZeroUnderMinAccumulatedAmount")
	ZERO_UNDER_MIN_ACCUMULATED_AMOUNT,
	@SerializedName("ZeroOtherOffer")
	ZERO_OTHER_OFFER,
	@SerializedName("ZeroAccumulated")
	ZERO_ACCUMULATED,
	@SerializedName("ZeroRefundNetted")
	ZERO_REFUND_NETTED,
	@SerializedName("RefundFullyNetted")
	REFUND_FULLY_NETTED,
	@SerializedName("RefundPartiallyNetted")
	REFUND_PARTIALLY_NETTED;

	@Override
	public String toString() {
		switch (this) {

			case FULL:
				return "Full";

			case FULL_PERCENTAGE:
				return "full_percentage";

			case FULL_FIXED_AMOUNT:
				return "full_fixedAmount";

			case FULL_ACCUMULATED:
				return "full_accumulated";

			case FULL_MISSING_BANK_CLEARANCE:
				return "full_missing_bank_clearance";

			case FULL_ACCUMULATED_ADJUSTED:
				return "full_accumulated_adjusted";

			case PART_OVER_MAX_PER_PURCHASE:
				return "part_over_max_per_purchase";

			case PART_OVER_MAX_ON_OFFER:
				return "part_over_max_on_offer";

			case PART_OVER_MAX_OFFER_REFUND_AMOUNT:
				return "part_over_max_offer_refund_amount";

			case PART_UNDER_MIN_ACCUMULATED:
				return "part_under_min_accumulated";

			case PART_ACCUMULATED:
				return "part_accumulated";

			case PART_REFUND_NETTED:
				return "part_refund_netted";

			case ZERO_UNDER_MIN_PURCHASE_AMOUNT:
				return "zero_under_min_purchase_amount";

			case ZERO_OVER_MAX_ON_OFFER:
				return "zero_over_max_offer";

			case ZERO_OVER_MAX_OFFER_REFUND_AMOUNT:
				return "zero_over_max_offer_refund_amount";

			case ZERO_OVER_MAX_NUMBER_OF_REDEEMS:
				return "zero_over_max_number_of_redeems";

			case ZERO_UNDER_MIN_ACCUMULATED_AMOUNT:
				return "zero_under_min_accumulated_amount";

			case ZERO_OTHER_OFFER:
				return "zero_other_offer";

			case ZERO_ACCUMULATED:
				return "zero_accumulated";

			case ZERO_REFUND_NETTED:
				return "zero_refund_netted";

			case REFUND_FULLY_NETTED:
				return "refund_fully_netted";

			case REFUND_PARTIALLY_NETTED:
				return "refund_partially_netted";

			default:
				return "unknown";
		}
	}
}
