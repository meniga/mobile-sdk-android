package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class SynchronizationStatus implements Parcelable, Serializable {

	protected long syncHistoryId;
	protected boolean isSyncDone;
	protected DateTime syncSessionStartTime;
	protected List<RealmSyncResponse> realmSyncResponses;

	/**
	 * @return Gets the sync history id.
	 */
	public long getSyncHistoryId() {
		return syncHistoryId;
	}

	/**
	 * @return Gets or sets the sync session start time.
	 */
	public DateTime getSyncSessionStartTime() {
		return syncSessionStartTime;
	}

	/**
	 * @return Gets the realm sync responses.
	 */
	public List<RealmSyncResponse> getRealmSyncResponses() {
		return realmSyncResponses;
	}

	/**
	 * @return Gets a value indicating whether this sync session is done or is still running in the background
	 */
	public boolean getIsSyncDone() {
		return this.isSyncDone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SynchronizationStatus that = (SynchronizationStatus) o;

		if (syncHistoryId != that.syncHistoryId) return false;
		if (isSyncDone != that.isSyncDone) return false;
		if (syncSessionStartTime != null ? !syncSessionStartTime.equals(that.syncSessionStartTime) : that.syncSessionStartTime != null)
			return false;
		return realmSyncResponses != null ? realmSyncResponses.equals(that.realmSyncResponses) : that.realmSyncResponses == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (syncHistoryId ^ (syncHistoryId >>> 32));
		result = 31 * result + (isSyncDone ? 1 : 0);
		result = 31 * result + (syncSessionStartTime != null ? syncSessionStartTime.hashCode() : 0);
		result = 31 * result + (realmSyncResponses != null ? realmSyncResponses.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.syncHistoryId);
		dest.writeByte(this.isSyncDone ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.syncSessionStartTime);
		dest.writeTypedList(this.realmSyncResponses);
	}

	public SynchronizationStatus() {
	}

	protected SynchronizationStatus(Parcel in) {
		this.syncHistoryId = in.readLong();
		this.isSyncDone = in.readByte() != 0;
		this.syncSessionStartTime = (DateTime) in.readSerializable();
		this.realmSyncResponses = in.createTypedArrayList(RealmSyncResponse.CREATOR);
	}

	public static final Parcelable.Creator<SynchronizationStatus> CREATOR = new Parcelable.Creator<SynchronizationStatus>() {
		@Override
		public SynchronizationStatus createFromParcel(Parcel source) {
			return new SynchronizationStatus(source);
		}

		@Override
		public SynchronizationStatus[] newArray(int size) {
			return new SynchronizationStatus[size];
		}
	};
}
