package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTransactionCountEvent extends MenigaTransactionEvent implements MenigaFeedItem, Parcelable {

	private MenigaTransactionCountEventData countEventData;

	public MenigaTransactionCountEvent() {
	}


	public void setMessageData(MenigaTransactionCountEventData countEventData) {
		this.countEventData = countEventData;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeParcelable(this.countEventData, flags);
	}

	protected MenigaTransactionCountEvent(Parcel in) {
		super(in);
		this.countEventData = in.readParcelable(MenigaTransactionCountEventData.class.getClassLoader());
	}

	public static final Creator<MenigaTransactionCountEvent> CREATOR = new Creator<MenigaTransactionCountEvent>() {
		@Override
		public MenigaTransactionCountEvent createFromParcel(Parcel source) {
			return new MenigaTransactionCountEvent(source);
		}

		@Override
		public MenigaTransactionCountEvent[] newArray(int size) {
			return new MenigaTransactionCountEvent[size];
		}
	};

	public MenigaTransactionCountEventData getMessageCountEventData() {
		return countEventData;
	}
}
