package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferCashbackPrediction implements Parcelable, Serializable, Cloneable {
	public static final Parcelable.Creator<MenigaOfferCashbackPrediction> CREATOR = new Parcelable.Creator<MenigaOfferCashbackPrediction>() {
		public MenigaOfferCashbackPrediction createFromParcel(Parcel source) {
			return new MenigaOfferCashbackPrediction(source);
		}

		public MenigaOfferCashbackPrediction[] newArray(int size) {
			return new MenigaOfferCashbackPrediction[size];
		}
	};

	protected MenigaDecimal monthlyAmount;
	protected int historyMonths;
	protected int monthlyCount;
	protected double expectedSavingsRatio;
	protected double progress;
	protected MenigaDecimal predictedAmount;
	protected double averageAmount;

	protected MenigaOfferCashbackPrediction() {
	}

	protected MenigaOfferCashbackPrediction(Parcel in) {
		this.monthlyAmount = (MenigaDecimal) in.readSerializable();
		this.historyMonths = in.readInt();
		this.monthlyCount = in.readInt();
		this.expectedSavingsRatio = in.readDouble();
		this.progress = in.readDouble();
		this.predictedAmount = (MenigaDecimal) in.readSerializable();
		this.averageAmount = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.monthlyAmount);
		dest.writeInt(this.historyMonths);
		dest.writeInt(this.monthlyCount);
		dest.writeDouble(this.expectedSavingsRatio);
		dest.writeDouble(this.progress);
		dest.writeSerializable(this.predictedAmount);
		dest.writeDouble(this.averageAmount);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o == null || !(o instanceof MenigaOfferCashbackPrediction)) {
			return false;
		}

		MenigaOfferCashbackPrediction other = (MenigaOfferCashbackPrediction) o;

		if (this.monthlyAmount == null && other.monthlyAmount != null || this.monthlyAmount != null && other.monthlyAmount == null) {
			return false;
		}
		if (this.monthlyAmount != null) {
			if (!this.monthlyAmount.equals(other.monthlyAmount)) {
				return false;
			}
		}
		if (this.historyMonths != other.historyMonths) {
			return false;
		}
		if (this.monthlyCount != other.monthlyCount) {
			return false;
		}
		if (Double.compare(this.expectedSavingsRatio, other.expectedSavingsRatio) != 0) {
			return false;
		}
		if (Double.compare(this.progress, other.progress) != 0) {
			return false;
		}
		if (this.predictedAmount == null && other.predictedAmount != null || this.predictedAmount != null && other.predictedAmount == null) {
			return false;
		}
		if (this.predictedAmount != null) {
			if (!this.predictedAmount.equals(other.predictedAmount)) {
				return false;
			}
		}
		return Double.compare(this.averageAmount, other.averageAmount) == 0;
	}

	@Override
	protected MenigaOfferCashbackPrediction clone() throws CloneNotSupportedException {
		return (MenigaOfferCashbackPrediction) super.clone();
	}
}
