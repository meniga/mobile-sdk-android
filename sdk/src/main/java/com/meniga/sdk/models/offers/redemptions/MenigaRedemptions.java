package com.meniga.sdk.models.offers.redemptions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.annotations.MetaProperty;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.redemptions.operators.MenigaRedemptionsOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRedemptions extends ArrayList<MenigaRedemptionTransaction> implements Parcelable, Serializable {

	protected static MenigaRedemptionsOperations apiOperator;

	private int page;
	@MetaProperty
	protected MenigaDecimal redeemedAmount;
	@MetaProperty
	protected MenigaDecimal nextReimbursementAmount;
	@MetaProperty
	protected int activatedOffers;
	@MetaProperty
	protected MenigaDecimal spentAmount;
	@MetaProperty
	protected int totalCount;

	protected List<MenigaScheduledReimbursement> scheduledReimbursements = new ArrayList<>();

	protected MenigaRedemptions() {
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaRedemptionsOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaRedemptionsOperations operator) {
		MenigaRedemptions.apiOperator = operator;
	}

	public static MenigaRedemptionsOperations getApiOperator() {
		return apiOperator;
	}

	public MenigaDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public MenigaDecimal getNextReimbursementAmount() {
		return nextReimbursementAmount;
	}

	public int getActivatedOffers() {
		return activatedOffers;
	}

	public MenigaDecimal getSpentAmount() {
		return spentAmount;
	}

	public List<MenigaScheduledReimbursement> getScheduledReimbursements() {
		return scheduledReimbursements;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setScheduledReimbursement(List<MenigaScheduledReimbursement> scheduled) {
		scheduledReimbursements = scheduled;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.redeemedAmount);
		dest.writeSerializable(this.nextReimbursementAmount);
		dest.writeInt(this.activatedOffers);
		dest.writeSerializable(this.spentAmount);
		dest.writeTypedList(this.scheduledReimbursements);
		dest.writeInt(this.totalCount);
	}

	protected MenigaRedemptions(Parcel in) {
		this.redeemedAmount = (MenigaDecimal) in.readSerializable();
		this.nextReimbursementAmount = (MenigaDecimal) in.readSerializable();
		this.activatedOffers = in.readInt();
		this.spentAmount = (MenigaDecimal) in.readSerializable();
		this.scheduledReimbursements = in.createTypedArrayList(MenigaScheduledReimbursement.CREATOR);
		this.totalCount = in.readInt();
	}

	public static final Creator<MenigaRedemptions> CREATOR = new Creator<MenigaRedemptions>() {
		@Override
		public MenigaRedemptions createFromParcel(Parcel source) {
			return new MenigaRedemptions(source);
		}

		@Override
		public MenigaRedemptions[] newArray(int size) {
			return new MenigaRedemptions[size];
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
		if (!super.equals(o)) {
			return false;
		}

		MenigaRedemptions that = (MenigaRedemptions) o;

		if (activatedOffers != that.activatedOffers) {
			return false;
		}
		if (totalCount != that.totalCount) {
			return false;
		}
		if (redeemedAmount != null ? !redeemedAmount.equals(that.redeemedAmount) : that.redeemedAmount != null) {
			return false;
		}
		if (nextReimbursementAmount != null ? !nextReimbursementAmount.equals(that.nextReimbursementAmount) : that.nextReimbursementAmount != null) {
			return false;
		}
		if (spentAmount != null ? !spentAmount.equals(that.spentAmount) : that.spentAmount != null) {
			return false;
		}
		return scheduledReimbursements != null ? scheduledReimbursements.equals(that.scheduledReimbursements) : that.scheduledReimbursements == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (redeemedAmount != null ? redeemedAmount.hashCode() : 0);
		result = 31 * result + (nextReimbursementAmount != null ? nextReimbursementAmount.hashCode() : 0);
		result = 31 * result + activatedOffers;
		result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
		result = 31 * result + (scheduledReimbursements != null ? scheduledReimbursements.hashCode() : 0);
		result = 31 * result + totalCount;
		return result;
	}

	/*
	 * Api methods below
	 */

	/**
	 * Fetch redemption transactions
	 *
	 * @return list of redemption transactions
	 */
	public static Result<MenigaRedemptions> fetch() {
		return MenigaRedemptions.apiOperator.getRedemptions(0, 1000, null, null);
	}

	/**
	 * Fetch redemption transactions
	 *
	 * @param dateFrom the inclusive transaction date to search from
	 * @param dateTo the exlusive transaction date to search to same day as fromDate is included
	 * @param skip number of records to skip
	 * @param take number of records to return
	 *
	 * @return list of redemption transactions
	 */
	public static Result<MenigaRedemptions> fetch(Integer skip, Integer take, DateTime dateFrom, DateTime dateTo) {
		return MenigaRedemptions.apiOperator.getRedemptions(skip, take, dateFrom, dateTo);
	}

	/**
	 * Fetch redemption transactions
	 *
	 * @param dateFrom the inclusive transaction date to search from
	 * @param dateTo the exlusive transaction date to search to same day as fromDate is included
	 *
	 * @return list of redemption transactions
	 */
	public static Result<MenigaRedemptions> fetch(DateTime dateFrom, DateTime dateTo) {
		return MenigaRedemptions.apiOperator.getRedemptions(0, 1000, dateFrom, dateTo);
	}
}
