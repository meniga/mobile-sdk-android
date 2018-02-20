package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 16.2.2018.
 *
 * Returned by the transaction update method. Contains other transactions that might have the
 * same category as the updated transaction, in case the category was updated
 */
public class MenigaTransactionUpdate implements Parcelable {
    private List<MenigaTransaction> updatedTransactions;
    private List<MenigaTransaction> suggestedTransactions;

    protected MenigaTransactionUpdate() {
    }

    protected MenigaTransactionUpdate(Parcel in) {
        this.updatedTransactions = in.createTypedArrayList(MenigaTransaction.CREATOR);
        this.suggestedTransactions = in.createTypedArrayList(MenigaTransaction.CREATOR);
    }

    protected MenigaTransactionUpdate(List<MenigaTransaction> updatedTransactions, List<MenigaTransaction> suggestedTransactions) {
        this.updatedTransactions = updatedTransactions;
        this.suggestedTransactions = suggestedTransactions;
    }

    public static MenigaTransactionUpdate fromList(List<MenigaTransaction> updatedTransactions, List<MenigaTransaction> suggestedTransactions) {
        return new MenigaTransactionUpdate(updatedTransactions, suggestedTransactions);
    }

    public List<MenigaTransaction> getUpdatedTransactions() {
        return updatedTransactions;
    }

    public List<MenigaTransaction> getSuggestedTransactions() {
        return suggestedTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaTransactionUpdate that = (MenigaTransactionUpdate) o;

        if (updatedTransactions != null ? !updatedTransactions.equals(that.updatedTransactions) : that.updatedTransactions != null) {
            return false;
        }
        return suggestedTransactions != null ? suggestedTransactions.equals(that.suggestedTransactions) : that.suggestedTransactions == null;
    }

    @Override
    public int hashCode() {
        int result = updatedTransactions != null ? updatedTransactions.hashCode() : 0;
        result = 31 * result + (suggestedTransactions != null ? suggestedTransactions.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.updatedTransactions);
        dest.writeTypedList(this.suggestedTransactions);
    }

    public static final Creator<MenigaTransactionUpdate> CREATOR = new Creator<MenigaTransactionUpdate>() {
        @Override
        public MenigaTransactionUpdate createFromParcel(Parcel source) {
            return new MenigaTransactionUpdate(source);
        }

        @Override
        public MenigaTransactionUpdate[] newArray(int size) {
            return new MenigaTransactionUpdate[size];
        }
    };
}
