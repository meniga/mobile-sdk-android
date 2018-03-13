package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 12.3.2018.
 */
public class MenigaUserMetaData implements Parcelable, Serializable {
    private String key;
    private String value;

    protected MenigaUserMetaData() {
    }

    protected MenigaUserMetaData(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaUserMetaData that = (MenigaUserMetaData) o;

        if (key != null ? !key.equals(that.key) : that.key != null) {
            return false;
        }
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    public static final Creator<MenigaUserMetaData> CREATOR = new Creator<MenigaUserMetaData>() {
        @Override
        public MenigaUserMetaData createFromParcel(Parcel source) {
            return new MenigaUserMetaData(source);
        }

        @Override
        public MenigaUserMetaData[] newArray(int size) {
            return new MenigaUserMetaData[size];
        }
    };
}
