package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionEvent extends MenigaEvent implements Parcelable, Serializable {

	public MenigaTransactionEvent() {
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public long getTransactionId() {
		return super.getTopicId();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

	protected MenigaTransactionEvent(Parcel in) {
		super(in);
	}

	public static final Creator<MenigaTransactionEvent> CREATOR = new Creator<MenigaTransactionEvent>() {
		@Override
		public MenigaTransactionEvent createFromParcel(Parcel source) {
			return new MenigaTransactionEvent(source);
		}

		@Override
		public MenigaTransactionEvent[] newArray(int size) {
			return new MenigaTransactionEvent[size];
		}
	};
}
