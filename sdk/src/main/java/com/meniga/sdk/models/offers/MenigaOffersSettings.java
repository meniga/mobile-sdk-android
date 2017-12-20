package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.operators.MenigaOfferOperations;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOffersSettings implements Parcelable, Serializable {
	protected static MenigaOfferOperations apiOperator;

	protected MenigaOffersSettings() {
	}

	protected MenigaOffersSettings(Parcel in) {
	}

	public static void setOperator(MenigaOfferOperations apiOperator) {
		MenigaOffersSettings.apiOperator = apiOperator;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

	public static final Parcelable.Creator<MenigaOffersSettings> CREATOR = new Parcelable.Creator<MenigaOffersSettings>() {
		@Override
		public MenigaOffersSettings createFromParcel(Parcel source) {
			return new MenigaOffersSettings(source);
		}

		@Override
		public MenigaOffersSettings[] newArray(int size) {
			return new MenigaOffersSettings[size];
		}
	};

	/*
	 * API operations below
	 */

	public static Result<Void> enable() {
		return MenigaOffersSettings.apiOperator.enableOffers();
	}

	public static Result<Void> disable() {
		return MenigaOffersSettings.apiOperator.disableOffers();
	}

    public static Result<Void> acceptTermsAndConditions() {
        return MenigaOffersSettings.apiOperator.acceptTermsAndConditions();
    }
}
