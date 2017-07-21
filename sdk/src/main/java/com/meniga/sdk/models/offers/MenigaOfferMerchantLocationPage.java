package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferMerchantLocationPage extends ArrayList<MenigaOfferMerchantLocation> implements Parcelable, Serializable {

	protected MenigaOfferMerchantLocationPage() {
	}

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }

    @SuppressWarnings("unchecked")
    protected MenigaOfferMerchantLocationPage(Parcel in) {
        addAll(in.readArrayList(getClass().getClassLoader()));
    }

    public static final Creator<MenigaOfferMerchantLocationPage> CREATOR = new Creator<MenigaOfferMerchantLocationPage>() {
        @Override
        public MenigaOfferMerchantLocationPage createFromParcel(Parcel source) {
            return new MenigaOfferMerchantLocationPage(source);
        }

        @Override
        public MenigaOfferMerchantLocationPage[] newArray(int size) {
            return new MenigaOfferMerchantLocationPage[size];
        }
    };
}
