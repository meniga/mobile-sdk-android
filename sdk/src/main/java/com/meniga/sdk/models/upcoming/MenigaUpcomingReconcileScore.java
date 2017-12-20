package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingReconcileScore implements Serializable, Parcelable, Cloneable {
	protected long upcomingId;
	protected Long transactionId;
	protected Float confidenceScore;
	protected Boolean isConfirmed;

	protected MenigaUpcomingReconcileScore() {
	}

	@Override
	public MenigaUpcomingReconcileScore clone() throws CloneNotSupportedException {
		return (MenigaUpcomingReconcileScore) super.clone();
	}

	/**
	 * @return The id of the suggested upcoming match
	 */
	public long getUpcomingId() {
		return upcomingId;
	}

	/**
	 * @return The id of the suggested transaction match
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @return The confidence score as calculated by the machine learning algorithm
	 */
	public Float getConfidenceScore() {
		return confidenceScore;
	}

	/**
	 * @return A flag indicating whether the user has confirmed this match
	 */
	public Boolean getConfirmed() {
		return isConfirmed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingReconcileScore that = (MenigaUpcomingReconcileScore) o;

		if (upcomingId != that.upcomingId) {
			return false;
		}
		if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null) {
			return false;
		}
		if (confidenceScore != null ? !confidenceScore.equals(that.confidenceScore) : that.confidenceScore != null) {
			return false;
		}
		return isConfirmed != null ? isConfirmed.equals(that.isConfirmed) : that.isConfirmed == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (upcomingId ^ (upcomingId >>> 32));
		result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
		result = 31 * result + (confidenceScore != null ? confidenceScore.hashCode() : 0);
		result = 31 * result + (isConfirmed != null ? isConfirmed.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.upcomingId);
		dest.writeValue(this.transactionId);
		dest.writeValue(this.confidenceScore);
		dest.writeValue(this.isConfirmed);
	}

	protected MenigaUpcomingReconcileScore(Parcel in) {
		this.upcomingId = in.readLong();
		this.transactionId = (Long) in.readValue(Long.class.getClassLoader());
		this.confidenceScore = (Float) in.readValue(Float.class.getClassLoader());
		this.isConfirmed = (Boolean) in.readValue(Boolean.class.getClassLoader());
	}

	public static final Parcelable.Creator<MenigaUpcomingReconcileScore> CREATOR = new Parcelable.Creator<MenigaUpcomingReconcileScore>() {
		@Override
		public MenigaUpcomingReconcileScore createFromParcel(Parcel source) {
			return new MenigaUpcomingReconcileScore(source);
		}

		@Override
		public MenigaUpcomingReconcileScore[] newArray(int size) {
			return new MenigaUpcomingReconcileScore[size];
		}
	};
}
