package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingDetails implements Serializable, Parcelable, Cloneable {
	protected MenigaUpcomingInvoice invoice;
	protected MenigaUpcomingScheduledPayment scheduledPayment;

	protected MenigaUpcomingDetails() {
	}

	@Override
	public MenigaUpcomingDetails clone() throws CloneNotSupportedException {
		return (MenigaUpcomingDetails) super.clone();
	}

	/**
	 * @return The invoice for the upcoming item
	 */
	public MenigaUpcomingInvoice getInvoice() {
		return invoice;
	}

	/**
	 * @return The pattern for the scheduled payment of the upcoming item series
	 */
	public MenigaUpcomingScheduledPayment getScheduledPayment() {
		return scheduledPayment;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.invoice, flags);
		dest.writeParcelable(this.scheduledPayment, flags);
	}

	protected MenigaUpcomingDetails(Parcel in) {
		this.invoice = in.readParcelable(MenigaUpcomingInvoice.class.getClassLoader());
		this.scheduledPayment = in.readParcelable(MenigaUpcomingScheduledPayment.class.getClassLoader());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingDetails that = (MenigaUpcomingDetails) o;

		if (invoice != null ? !invoice.equals(that.invoice) : that.invoice != null) {
			return false;
		}
		return scheduledPayment != null ? scheduledPayment.equals(that.scheduledPayment) : that.scheduledPayment == null;

	}

	@Override
	public int hashCode() {
		int result = invoice != null ? invoice.hashCode() : 0;
		result = 31 * result + (scheduledPayment != null ? scheduledPayment.hashCode() : 0);
		return result;
	}

	public static final Creator<MenigaUpcomingDetails> CREATOR = new Creator<MenigaUpcomingDetails>() {
		@Override
		public MenigaUpcomingDetails createFromParcel(Parcel source) {
			return new MenigaUpcomingDetails(source);
		}

		@Override
		public MenigaUpcomingDetails[] newArray(int size) {
			return new MenigaUpcomingDetails[size];
		}
	};
}
