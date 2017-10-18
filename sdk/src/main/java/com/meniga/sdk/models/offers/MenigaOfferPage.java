package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.annotations.MetaProperty;
import com.meniga.sdk.models.offers.enums.OfferFilterState;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferPage extends ArrayList<MenigaOffer> implements Serializable, Parcelable {

	protected int skip;
	protected int take;
	protected List<OfferFilterState> states = new ArrayList<>();
	protected List<Long> offerIds = new ArrayList<>();
	protected boolean expiredWithRedemptionOnly = false;
	protected boolean hasMorePages = true;

	@MetaProperty
	private int availableOffers;
	@MetaProperty
	private int availableOffersActivated;
	@MetaProperty
	private int availableOffersDeclined;
	@MetaProperty
	private int offersActivated;
	@MetaProperty
	private int offersDeclined;
	@MetaProperty
	private int offersNotSeen;
	@MetaProperty
	private boolean offersEnabled;
	@MetaProperty
	private DateTime offersEnabledTimestamp;
	@MetaProperty
	private boolean termsAndConditionsAccepted;
	@MetaProperty
	private boolean isReimbursementAccountValid;
	@MetaProperty
	private int totalCount;

	protected MenigaOfferPage() {
	}

	protected MenigaOfferPage(Parcel in) {
		this.skip = in.readInt();
		this.take = in.readInt();
		this.states = new ArrayList<>();
		in.readList(this.states, OfferFilterState.class.getClassLoader());
		this.offerIds = new ArrayList<>();
		in.readList(this.offerIds, Integer.class.getClassLoader());
		this.expiredWithRedemptionOnly = in.readByte() != 0;
		this.hasMorePages = in.readByte() != 0;
		this.availableOffers = in.readInt();
		this.availableOffersActivated = in.readInt();
		this.availableOffersDeclined = in.readInt();
		this.offersActivated = in.readInt();
		this.offersDeclined = in.readInt();
		this.offersNotSeen = in.readInt();
		this.offersEnabled = in.readByte() != 0;
		this.offersEnabledTimestamp = (DateTime) in.readSerializable();
		this.termsAndConditionsAccepted = in.readByte() != 0;
		this.isReimbursementAccountValid = in.readByte() != 0;
		this.totalCount = in.readInt();

		Parcelable[] offersRaw = in.readParcelableArray(MenigaOffer.class.getClassLoader());
		for (Parcelable parcel : offersRaw) {
			add((MenigaOffer) parcel);
		}
	}

	public static MenigaOfferPage newInstance() {
		return new MenigaOfferPage();
	}

	public int getSkip() {
		return skip;
	}

	public int getTake() {
		return take;
	}

	public List<OfferFilterState> getStates() {
		return states;
	}

	public List<Long> getOfferIds() {
		return offerIds;
	}

	public boolean isExpiredWithRedemptionOnly() {
		return expiredWithRedemptionOnly;
	}

	public boolean isHasMorePages() {
		return hasMorePages;
	}

	public int getAvailableOffers() {
		return availableOffers;
	}

	public int getAvailableOffersActivated() {
		return availableOffersActivated;
	}

	public int getAvailableOffersDeclined() {
		return availableOffersDeclined;
	}

	public int getOffersActivated() {
		return offersActivated;
	}

	public int getOffersDeclined() {
		return offersDeclined;
	}

	public int getOffersNotSeen() {
		return offersNotSeen;
	}

	public boolean isOffersEnabled() {
		return offersEnabled;
	}

	public DateTime getOffersEnabledTimestamp() {
		return offersEnabledTimestamp;
	}

	public boolean isReimbursementAccountValid() {
		return isReimbursementAccountValid;
	}

	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		MenigaOfferPage that = (MenigaOfferPage) o;

		if (skip != that.skip) {
			return false;
		}
		if (take != that.take) {
			return false;
		}
		if (expiredWithRedemptionOnly != that.expiredWithRedemptionOnly) {
			return false;
		}
		if (hasMorePages != that.hasMorePages) {
			return false;
		}
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
		if (states != null ? !states.equals(that.states) : that.states != null) {
			return false;
		}
		if (offerIds != null ? !offerIds.equals(that.offerIds) : that.offerIds != null) {
			return false;
		}
		return offersEnabledTimestamp != null ? offersEnabledTimestamp.equals(that.offersEnabledTimestamp) : that.offersEnabledTimestamp == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + skip;
		result = 31 * result + take;
		result = 31 * result + (states != null ? states.hashCode() : 0);
		result = 31 * result + (offerIds != null ? offerIds.hashCode() : 0);
		result = 31 * result + (expiredWithRedemptionOnly ? 1 : 0);
		result = 31 * result + (hasMorePages ? 1 : 0);
		result = 31 * result + availableOffers;
		result = 31 * result + availableOffersActivated;
		result = 31 * result + availableOffersDeclined;
		result = 31 * result + offersActivated;
		result = 31 * result + offersDeclined;
		result = 31 * result + offersNotSeen;
		result = 31 * result + (offersEnabled ? 1 : 0);
		result = 31 * result + (offersEnabledTimestamp != null ? offersEnabledTimestamp.hashCode() : 0);
		result = 31 * result + (termsAndConditionsAccepted ? 1 : 0);
		result = 31 * result + (isReimbursementAccountValid ? 1 : 0);
		result = 31 * result + totalCount;
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.skip);
		dest.writeInt(this.take);
		dest.writeList(this.states);
		dest.writeList(this.offerIds);
		dest.writeByte(this.expiredWithRedemptionOnly ? (byte) 1 : (byte) 0);
		dest.writeByte(this.hasMorePages ? (byte) 1 : (byte) 0);
		dest.writeInt(this.availableOffers);
		dest.writeInt(this.availableOffersActivated);
		dest.writeInt(this.availableOffersDeclined);
		dest.writeInt(this.offersActivated);
		dest.writeInt(this.offersDeclined);
		dest.writeInt(this.offersNotSeen);
		dest.writeByte(this.offersEnabled ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.offersEnabledTimestamp);
		dest.writeByte(this.termsAndConditionsAccepted ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isReimbursementAccountValid ? (byte) 1 : (byte) 0);
		dest.writeInt(this.totalCount);
		MenigaOffer[] arr = new MenigaOffer[size()];
		toArray(arr);
		dest.writeParcelableArray(arr, 0);
	}

	public static final Parcelable.Creator<MenigaOfferPage> CREATOR = new Parcelable.Creator<MenigaOfferPage>() {
		@Override
		public MenigaOfferPage createFromParcel(Parcel source) {
			return new MenigaOfferPage(source);
		}

		@Override
		public MenigaOfferPage[] newArray(int size) {
			return new MenigaOfferPage[size];
		}
	};
}
