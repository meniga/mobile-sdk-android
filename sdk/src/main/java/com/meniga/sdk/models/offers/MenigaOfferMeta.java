package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferMeta implements Parcelable {

	protected int availableOffers;
	protected int availableOffersActivated;
	protected int availableOffersDeclined;
	protected int offersActivated;
	protected int offersDeclined;
	protected int offersNotSeen;
	protected boolean offersEnabled;
	protected String offersEnabledTimestamp;
	protected boolean termsAndConditionsAccepted;
	protected boolean isReimbursementAccountValid;
	protected int totalCount;

	protected MenigaOfferMeta() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.availableOffers);
		dest.writeInt(this.availableOffersActivated);
		dest.writeInt(this.availableOffersDeclined);
		dest.writeInt(this.offersActivated);
		dest.writeInt(this.offersDeclined);
		dest.writeInt(this.offersNotSeen);
		dest.writeByte(this.offersEnabled ? (byte) 1 : (byte) 0);
		dest.writeString(this.offersEnabledTimestamp);
		dest.writeByte(this.termsAndConditionsAccepted ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isReimbursementAccountValid ? (byte) 1 : (byte) 0);
		dest.writeInt(this.totalCount);
	}

	protected MenigaOfferMeta(Parcel in) {
		this.availableOffers = in.readInt();
		this.availableOffersActivated = in.readInt();
		this.availableOffersDeclined = in.readInt();
		this.offersActivated = in.readInt();
		this.offersDeclined = in.readInt();
		this.offersNotSeen = in.readInt();
		this.offersEnabled = in.readByte() != 0;
		this.offersEnabledTimestamp = in.readString();
		this.termsAndConditionsAccepted = in.readByte() != 0;
		this.isReimbursementAccountValid = in.readByte() != 0;
		this.totalCount = in.readInt();
	}

	public static final Creator<MenigaOfferMeta> CREATOR = new Creator<MenigaOfferMeta>() {
		@Override
		public MenigaOfferMeta createFromParcel(Parcel source) {
			return new MenigaOfferMeta(source);
		}

		@Override
		public MenigaOfferMeta[] newArray(int size) {
			return new MenigaOfferMeta[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaOfferMeta that = (MenigaOfferMeta) o;

		if (availableOffers != that.availableOffers) {
			return false;
		}
		if (availableOffersActivated != that.availableOffersActivated) {
			return false;
		}
		if (availableOffersDeclined != that.availableOffersDeclined) {
			return false;
		}
		if (offersActivated != that.offersActivated) {
			return false;
		}
		if (offersDeclined != that.offersDeclined) {
			return false;
		}
		if (offersNotSeen != that.offersNotSeen) {
			return false;
		}
		if (offersEnabled != that.offersEnabled) {
			return false;
		}
		if (termsAndConditionsAccepted != that.termsAndConditionsAccepted) {
			return false;
		}
		if (isReimbursementAccountValid != that.isReimbursementAccountValid) {
			return false;
		}
		if (totalCount != that.totalCount) {
			return false;
		}
		return offersEnabledTimestamp != null ? offersEnabledTimestamp.equals(that.offersEnabledTimestamp) : that.offersEnabledTimestamp == null;
	}

	@Override
	public int hashCode() {
		int result = availableOffers;
		result = 31 * result + availableOffersActivated;
		result = 31 * result + availableOffersDeclined;
		result = 31 * result + offersActivated;
		result = 31 * result + offersDeclined;
		result = 31 * result + offersNotSeen;
		result = 31 * result + (offersEnabled? 1 : 0);
		result = 31 * result + (offersEnabledTimestamp != null ? offersEnabledTimestamp.hashCode() : 0);
		result = 31 * result + (termsAndConditionsAccepted ? 1 : 0);
		result = 31 * result + (isReimbursementAccountValid ? 1 : 0);
		result = 31 * result + totalCount;
		return result;
	}
}
