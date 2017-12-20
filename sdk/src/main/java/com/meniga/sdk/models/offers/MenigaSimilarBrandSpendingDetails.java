package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaSimilarBrandSpendingDetails implements Parcelable, Serializable {
	public static final Creator<MenigaSimilarBrandSpendingDetails> CREATOR = new Creator<MenigaSimilarBrandSpendingDetails>() {
		@Override
		public MenigaSimilarBrandSpendingDetails createFromParcel(Parcel source) {
			return new MenigaSimilarBrandSpendingDetails(source);
		}

		@Override
		public MenigaSimilarBrandSpendingDetails[] newArray(int size) {
			return new MenigaSimilarBrandSpendingDetails[size];
		}
	};

	protected DateTime startDate;
	protected DateTime endDate;
	protected List<MenigaBrandSpending> brandSpendings;

	protected MenigaSimilarBrandSpendingDetails() {
	}

	protected MenigaSimilarBrandSpendingDetails(Parcel in) {
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.brandSpendings = in.createTypedArrayList(MenigaBrandSpending.CREATOR);
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public List<MenigaBrandSpending> getBrandSpendings() {
		return brandSpendings;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
		dest.writeTypedList(brandSpendings);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaSimilarBrandSpendingDetails that = (MenigaSimilarBrandSpendingDetails) o;

		if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
			return false;
		}
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
			return false;
		}
		return brandSpendings != null ? brandSpendings.equals(that.brandSpendings) : that.brandSpendings == null;
	}

	@Override
	public int hashCode() {
		int result = startDate != null ? startDate.hashCode() : 0;
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (brandSpendings != null ? brandSpendings.hashCode() : 0);
		return result;
	}
}
