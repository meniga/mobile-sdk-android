package com.meniga.sdk.models.budget.enums;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
@Deprecated
public class GenerationTypeValue implements Parcelable, Serializable {
    private int value;
    private GenerationType type;

    public GenerationTypeValue(int val) {
        this.value = val;
        if (val == 0) {
            type = GenerationType.MANUAL;
        } else if (val < 0) {
            type = GenerationType.SAME_AS_MONTH;
        } else {
            type = GenerationType.AVERAGE_MONTHS;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GenerationType getType() {
        return type;
    }

    public void setType(GenerationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenerationTypeValue that = (GenerationTypeValue) o;

        if (value != that.value) {
            return false;
        }
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected GenerationTypeValue(Parcel in) {
        this.value = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : GenerationType.values()[tmpType];
    }

    public static final Creator<GenerationTypeValue> CREATOR = new Creator<GenerationTypeValue>() {
        @Override
        public GenerationTypeValue createFromParcel(Parcel source) {
            return new GenerationTypeValue(source);
        }

        @Override
        public GenerationTypeValue[] newArray(int size) {
            return new GenerationTypeValue[size];
        }
    };
}
