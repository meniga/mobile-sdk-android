package com.meniga.sdk.models.terms;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTermType implements Parcelable, Serializable {
    protected long id;
    protected String name;
    protected String description;

    protected MenigaTermType() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaTermType that = (MenigaTermType) o;

        if (id != that.id) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected MenigaTermType(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<MenigaTermType> CREATOR = new Parcelable.Creator<MenigaTermType>() {
        @Override
        public MenigaTermType createFromParcel(Parcel source) {
            return new MenigaTermType(source);
        }

        @Override
        public MenigaTermType[] newArray(int size) {
            return new MenigaTermType[size];
        }
    };
}
