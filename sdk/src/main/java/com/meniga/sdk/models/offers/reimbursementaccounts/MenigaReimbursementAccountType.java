package com.meniga.sdk.models.offers.reimbursementaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.offers.reimbursementaccounts.operators.MenigaReimbursementAccountOperations;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaReimbursementAccountType implements Parcelable, Serializable {
    protected static MenigaReimbursementAccountOperations apiOperator;

    protected int id;
    protected String name;

	protected MenigaReimbursementAccountType() {
    }

	protected MenigaReimbursementAccountType(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
	}

    public static void setOperator(MenigaReimbursementAccountOperations apiOperator) {
        MenigaReimbursementAccount.apiOperator = apiOperator;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
	        return true;
        }
        if (o == null || getClass() != o.getClass()) {
	        return false;
        }

        MenigaReimbursementAccountType that = (MenigaReimbursementAccountType) o;

        if (id != that.id) {
	        return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public static final Creator<MenigaReimbursementAccountType> CREATOR = new Creator<MenigaReimbursementAccountType>() {
        @Override
        public MenigaReimbursementAccountType createFromParcel(Parcel source) {
            return new MenigaReimbursementAccountType(source);
        }

        @Override
        public MenigaReimbursementAccountType[] newArray(int size) {
            return new MenigaReimbursementAccountType[size];
        }
    };

	/*
	 * API operations below
	 */

	public static Result<MenigaReimbursementAccountTypePage> fetch(Integer skip, Integer take) {
		return MenigaReimbursementAccount.apiOperator.getReimbursementAccountTypes(skip, take);
	}
}
