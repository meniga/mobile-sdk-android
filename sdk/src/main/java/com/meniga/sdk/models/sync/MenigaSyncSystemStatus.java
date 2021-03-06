package com.meniga.sdk.models.sync;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Contains info on the sync status of all realms and for each realm, its accounts
 *
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 9.3.2018.
 */
public class MenigaSyncSystemStatus implements Parcelable, Serializable {
    private long syncHistoryId;
    private boolean isSyncDone;
    private DateTime syncSessionStartTime;
    private List<RealmSyncResponse> realmSyncResponses;

    protected MenigaSyncSystemStatus() {
    }

    protected MenigaSyncSystemStatus(Parcel in) {
        this.syncHistoryId = in.readLong();
        this.isSyncDone = in.readByte() != 0;
        this.syncSessionStartTime = (DateTime) in.readSerializable();
        this.realmSyncResponses = in.createTypedArrayList(RealmSyncResponse.CREATOR);
    }

    public long getSyncHistoryId() {
        return syncHistoryId;
    }

    public boolean isSyncDone() {
        return isSyncDone;
    }

    public DateTime getSyncSessionStartTime() {
        return syncSessionStartTime;
    }

    public List<RealmSyncResponse> getRealmSyncResponses() {
        return realmSyncResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenigaSyncSystemStatus that = (MenigaSyncSystemStatus) o;

        if (syncHistoryId != that.syncHistoryId) {
            return false;
        }
        if (isSyncDone != that.isSyncDone) {
            return false;
        }
        if (syncSessionStartTime != null ? !syncSessionStartTime.equals(that.syncSessionStartTime) : that.syncSessionStartTime != null) {
            return false;
        }
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

    public static final Creator<MenigaSyncSystemStatus> CREATOR = new Creator<MenigaSyncSystemStatus>() {
        @Override
        public MenigaSyncSystemStatus createFromParcel(Parcel source) {
            return new MenigaSyncSystemStatus(source);
        }

        @Override
        public MenigaSyncSystemStatus[] newArray(int size) {
            return new MenigaSyncSystemStatus[size];
        }
    };
}
