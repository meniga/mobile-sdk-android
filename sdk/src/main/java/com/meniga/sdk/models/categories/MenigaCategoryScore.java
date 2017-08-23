package com.meniga.sdk.models.categories;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Represents a likelihood that the category contained in this class applies to the object in mind, e.g. a transaction.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCategoryScore implements Parcelable, Serializable, Cloneable {

	public static final Parcelable.Creator<MenigaCategoryScore> CREATOR = new Parcelable.Creator<MenigaCategoryScore>() {
		public MenigaCategoryScore createFromParcel(Parcel in) {
			return new MenigaCategoryScore(in);
		}

		public MenigaCategoryScore[] newArray(int size) {
			return new MenigaCategoryScore[size];
		}
	};

	protected long categoryId;
	protected double score;

	protected MenigaCategoryScore() {
	}

	public MenigaCategoryScore(Parcel parcel) {
		this.categoryId = parcel.readLong();
		this.score = parcel.readDouble();
	}

	/**
	 * @return The id of the category.
	 */
	public long getCategoryId() {
		return this.categoryId;
	}

	/**
	 * @return How likely a transaction is to have this categoryId.
	 */
	public double getScore() {
		return this.score;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.categoryId);
		dest.writeDouble(this.score);
	}

	@Override
	protected MenigaCategoryScore clone() throws CloneNotSupportedException {
		return (MenigaCategoryScore) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaCategoryScore that = (MenigaCategoryScore) o;

		if (categoryId != that.categoryId) {
			return false;
		}
		return Double.compare(that.score, score) == 0;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = (int) (categoryId ^ (categoryId >>> 32));
		temp = Double.doubleToLongBits(score);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
