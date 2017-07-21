package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class MenigaTransactionCountEvent extends MenigaTransactionEvent implements MenigaFeedItem, Parcelable {

	private transient MenigaTransactionCountEventData messageData;

	public MenigaTransactionCountEventData getTransactionCountEventData() {
		return messageData;
	}

	public void setMessageData(MenigaTransactionCountEventData messageData) {
		this.messageData = messageData;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeParcelable(messageData, 0);
	}

	public MenigaTransactionCountEvent() {
	}

	protected MenigaTransactionCountEvent(Parcel in) {
		super(in);
		this.messageData = in.readParcelable(MenigaTransactionCountEvent.class.getClassLoader());
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		MenigaTransactionCountEvent that = (MenigaTransactionCountEvent) o;

		return messageData.equals(that.messageData);

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + messageData.hashCode();
		return result;
	}
}
