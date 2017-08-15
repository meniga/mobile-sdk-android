package com.meniga.sdk.models.sync;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Contains info on whether or not the sync process has finished.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaSyncStatus implements Parcelable, Serializable {

	protected boolean hasCompletedSyncSession;
	protected boolean isSynchronizationNeeded;
	protected String synchronizationStatus;

	public static final Creator<MenigaSyncStatus> CREATOR = new Creator<MenigaSyncStatus>() {
		@Override
		public MenigaSyncStatus createFromParcel(Parcel source) {
			return new MenigaSyncStatus(source);
		}

		@Override
		public MenigaSyncStatus[] newArray(int size) {
			return new MenigaSyncStatus[size];
		}
	};

	protected MenigaSyncStatus(Parcel in) {
		this.hasCompletedSyncSession = in.readByte() != 0;
	}

	/**
	 * @return True if user has a completed synchronization session.
	 */
	public boolean getHasCompletedSyncSession() {
		return hasCompletedSyncSession;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(hasCompletedSyncSession ? (byte) 1 : (byte) 0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaSyncStatus that = (MenigaSyncStatus) o;

		return hasCompletedSyncSession == that.hasCompletedSyncSession;

	}

	@Override
	public int hashCode() {
		return (hasCompletedSyncSession ? 1 : 0);
	}
}
