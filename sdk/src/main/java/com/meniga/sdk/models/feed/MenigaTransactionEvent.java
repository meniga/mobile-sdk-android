package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionEvent extends MenigaEvent implements Parcelable {

	public MenigaTransactionEvent() {
	}

	public long getTransactionId() {
		return super.getTopicId();
	}

	@Override
	public int describeContents() {
		return 0;
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
