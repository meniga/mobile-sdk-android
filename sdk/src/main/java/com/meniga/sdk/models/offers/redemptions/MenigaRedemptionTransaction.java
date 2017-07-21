package com.meniga.sdk.models.offers.redemptions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.offers.redemptions.enums.ReimbursementStatus;
import com.meniga.sdk.models.offers.redemptions.enums.RedemptionType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaRedemptionTransaction implements Parcelable, Serializable {

	protected long id;
	protected long offerId;
	protected String text;
	protected DateTime date;
	protected MenigaDecimal amount;
	protected MenigaDecimal redemptionAmount;
	protected RedemptionType redemptionType;
	protected ReimbursementStatus reimbursementStatus;
	protected DateTime reimbursementDate;
	protected DateTime scheduledReimbursementDate;
	protected String reimbursementAccountInfo;

	protected MenigaRedemptionTransaction() {
	}

	protected MenigaRedemptionTransaction(Parcel in) {
		this.id = in.readLong();
		this.offerId = in.readLong();
		this.text = in.readString();
		this.date = (DateTime) in.readSerializable();
		this.amount = (MenigaDecimal) in.readSerializable();
		this.redemptionAmount = (MenigaDecimal) in.readSerializable();
		int tmpRedemptionType = in.readInt();
		this.redemptionType = tmpRedemptionType == -1 ? null : RedemptionType.values()[tmpRedemptionType];
		int tmpReimbursementStatus = in.readInt();
		this.reimbursementStatus = tmpReimbursementStatus == -1 ? null : ReimbursementStatus.values()[tmpReimbursementStatus];
		this.reimbursementDate = (DateTime) in.readSerializable();
		this.scheduledReimbursementDate = (DateTime) in.readSerializable();
		this.reimbursementAccountInfo = in.readString();
	}

	public String getReimbursementAccountInfo() {
		return reimbursementAccountInfo;
	}

	public long getId() {
		return id;
	}

	public long getOfferId() {
		return offerId;
	}

	public String getText() {
		return text;
	}


	public DateTime getDate() {
		return date;
	}

	public MenigaDecimal getAmount() {
		return amount;
	}

	public MenigaDecimal getRedemptionAmount() {
		return redemptionAmount;
	}

	public RedemptionType getRedemptionType() {
		return redemptionType;
	}

	public ReimbursementStatus getReimbursementStatus() {
		return reimbursementStatus;
	}

	public DateTime getReimbursementDate() {
		return reimbursementDate;
	}

	public DateTime getScheduledReimbursementDate() {
		return scheduledReimbursementDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaRedemptionTransaction that = (MenigaRedemptionTransaction) o;

		if (id != that.id) {
			return false;
		}
		if (offerId != that.offerId) {
			return false;
		}
		if (text != null ? !text.equals(that.text) : that.text != null) {
			return false;
		}
		if (date != null ? !date.equals(that.date) : that.date != null) {
			return false;
		}
		if (amount != null ? !amount.equals(that.amount) : that.amount != null) {
			return false;
		}
		if (redemptionAmount != null ? !redemptionAmount.equals(that.redemptionAmount) : that.redemptionAmount != null) {
			return false;
		}
		if (redemptionType != that.redemptionType) {
			return false;
		}
		if (reimbursementStatus != that.reimbursementStatus) {
			return false;
		}
		if (reimbursementDate != null ? !reimbursementDate.equals(that.reimbursementDate) : that.reimbursementDate != null) {
			return false;
		}
		if (scheduledReimbursementDate != null ? !scheduledReimbursementDate.equals(that.scheduledReimbursementDate) : that.scheduledReimbursementDate != null) {
			return false;
		}
		return reimbursementAccountInfo != null ? reimbursementAccountInfo.equals(that.reimbursementAccountInfo) : that.reimbursementAccountInfo == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (offerId ^ (offerId >>> 32));
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (redemptionAmount != null ? redemptionAmount.hashCode() : 0);
		result = 31 * result + (redemptionType != null ? redemptionType.hashCode() : 0);
		result = 31 * result + (reimbursementStatus != null ? reimbursementStatus.hashCode() : 0);
		result = 31 * result + (reimbursementDate != null ? reimbursementDate.hashCode() : 0);
		result = 31 * result + (scheduledReimbursementDate != null ? scheduledReimbursementDate.hashCode() : 0);
		result = 31 * result + (reimbursementAccountInfo != null ? reimbursementAccountInfo.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.offerId);
		dest.writeString(this.text);
		dest.writeSerializable(this.date);
		dest.writeSerializable(this.amount);
		dest.writeSerializable(this.redemptionAmount);
		dest.writeInt(this.redemptionType == null ? -1 : this.redemptionType.ordinal());
		dest.writeInt(this.reimbursementStatus == null ? -1 : this.reimbursementStatus.ordinal());
		dest.writeSerializable(this.reimbursementDate);
		dest.writeSerializable(this.scheduledReimbursementDate);
		dest.writeString(this.reimbursementAccountInfo);
	}

	public static final Creator<MenigaRedemptionTransaction> CREATOR = new Creator<MenigaRedemptionTransaction>() {
		@Override
		public MenigaRedemptionTransaction createFromParcel(Parcel source) {
			return new MenigaRedemptionTransaction(source);
		}

		@Override
		public MenigaRedemptionTransaction[] newArray(int size) {
			return new MenigaRedemptionTransaction[size];
		}
	};
}
