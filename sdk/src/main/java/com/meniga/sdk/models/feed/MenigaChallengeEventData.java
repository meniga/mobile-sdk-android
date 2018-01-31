package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 6.12.2017.
 */
public class MenigaChallengeEventData implements Parcelable, Serializable {
    protected UUID challengeId;
    protected String title;
    protected boolean challengeSuccessful;
    protected MenigaDecimal targetAmount;
    protected String targetAmountFormatted;
    protected MenigaDecimal spentAmount;
    protected String spentAmountFormatted;
    protected MenigaDecimal amountDifference;
    protected String amountDifferenceFormatted;
    protected DateTime startDate;
    protected DateTime endDate;
    protected DateTime periodStart;
    protected DateTime periodEnd;
    protected String iconUrl;
    protected CustomChallengeColor metadata;
    protected List<Long> categoryIds;
    protected Float percentageSpent;
    protected Integer daysRemaining;

    protected MenigaChallengeEventData() {
    }

     public UUID getChallengeId() {
        return challengeId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChallengeSuccessful() {
        return challengeSuccessful;
    }

    public MenigaDecimal getTargetAmount() {
        return targetAmount;
    }

    public String getTargetAmountFormatted() {
        return targetAmountFormatted;
    }

    public MenigaDecimal getSpentAmount() {
        return spentAmount;
    }

    public String getSpentAmountFormatted() {
        return spentAmountFormatted;
    }

    public MenigaDecimal getAmountDifference() {
        return amountDifference;
    }

    public String getAmountDifferenceFormatted() {
        return amountDifferenceFormatted;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public DateTime getPeriodStart() {
        return periodStart;
    }

    public DateTime getPeriodEnd() {
        return periodEnd;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public CustomChallengeColor getMetadata() {
        return metadata;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public Float getPercentageSpent() {
        return percentageSpent;
    }

    public Integer getDaysRemaining() {
        return daysRemaining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.challengeId);
        dest.writeString(this.title);
        dest.writeByte(this.challengeSuccessful ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.targetAmount);
        dest.writeString(this.targetAmountFormatted);
        dest.writeSerializable(this.spentAmount);
        dest.writeString(this.spentAmountFormatted);
        dest.writeSerializable(this.amountDifference);
        dest.writeString(this.amountDifferenceFormatted);
        dest.writeSerializable(this.startDate);
        dest.writeSerializable(this.endDate);
        dest.writeSerializable(this.periodStart);
        dest.writeSerializable(this.periodEnd);
        dest.writeString(this.iconUrl);
        dest.writeInt(this.metadata == null ? -1 : this.metadata.ordinal());
        dest.writeList(this.categoryIds);
        dest.writeValue(this.percentageSpent);
        dest.writeValue(this.daysRemaining);
    }

    protected MenigaChallengeEventData(Parcel in) {
        this.challengeId = (UUID) in.readSerializable();
        this.title = in.readString();
        this.challengeSuccessful = in.readByte() != 0;
        this.targetAmount = (MenigaDecimal) in.readSerializable();
        this.targetAmountFormatted = in.readString();
        this.spentAmount = (MenigaDecimal) in.readSerializable();
        this.spentAmountFormatted = in.readString();
        this.amountDifference = (MenigaDecimal) in.readSerializable();
        this.amountDifferenceFormatted = in.readString();
        this.startDate = (DateTime) in.readSerializable();
        this.endDate = (DateTime) in.readSerializable();
        this.periodStart = (DateTime) in.readSerializable();
        this.periodEnd = (DateTime) in.readSerializable();
        this.iconUrl = in.readString();
        int tmpMetadata = in.readInt();
        this.metadata = tmpMetadata == -1 ? null : CustomChallengeColor.values()[tmpMetadata];
        this.categoryIds = new ArrayList<Long>();
        in.readList(this.categoryIds, Long.class.getClassLoader());
        this.percentageSpent = (Float) in.readValue(Float.class.getClassLoader());
        this.daysRemaining = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<MenigaChallengeEventData> CREATOR = new Creator<MenigaChallengeEventData>() {
        @Override
        public MenigaChallengeEventData createFromParcel(Parcel source) {
            return new MenigaChallengeEventData(source);
        }

        @Override
        public MenigaChallengeEventData[] newArray(int size) {
            return new MenigaChallengeEventData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaChallengeEventData that = (MenigaChallengeEventData) o;

        if (challengeSuccessful != that.challengeSuccessful) {
            return false;
        }
        if (challengeId != null ? !challengeId.equals(that.challengeId) : that.challengeId != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (targetAmount != null ? !targetAmount.equals(that.targetAmount) : that.targetAmount != null) {
            return false;
        }
        if (targetAmountFormatted != null ? !targetAmountFormatted.equals(that.targetAmountFormatted) : that.targetAmountFormatted != null) {
            return false;
        }
        if (spentAmount != null ? !spentAmount.equals(that.spentAmount) : that.spentAmount != null) {
            return false;
        }
        if (spentAmountFormatted != null ? !spentAmountFormatted.equals(that.spentAmountFormatted) : that.spentAmountFormatted != null) {
            return false;
        }
        if (amountDifference != null ? !amountDifference.equals(that.amountDifference) : that.amountDifference != null) {
            return false;
        }
        if (amountDifferenceFormatted != null ? !amountDifferenceFormatted.equals(that.amountDifferenceFormatted) : that.amountDifferenceFormatted != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        if (periodStart != null ? !periodStart.equals(that.periodStart) : that.periodStart != null) {
            return false;
        }
        if (periodEnd != null ? !periodEnd.equals(that.periodEnd) : that.periodEnd != null) {
            return false;
        }
        if (iconUrl != null ? !iconUrl.equals(that.iconUrl) : that.iconUrl != null) {
            return false;
        }
        if (metadata != that.metadata) {
            return false;
        }
        if (categoryIds != null ? !categoryIds.equals(that.categoryIds) : that.categoryIds != null)
            return false;
        if (percentageSpent != null ? !percentageSpent.equals(that.percentageSpent) : that.percentageSpent != null) {
            return false;
        }
        return daysRemaining != null ? daysRemaining.equals(that.daysRemaining) : that.daysRemaining == null;
    }

    @Override
    public int hashCode() {
        int result = challengeId != null ? challengeId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (challengeSuccessful ? 1 : 0);
        result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
        result = 31 * result + (targetAmountFormatted != null ? targetAmountFormatted.hashCode() : 0);
        result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
        result = 31 * result + (spentAmountFormatted != null ? spentAmountFormatted.hashCode() : 0);
        result = 31 * result + (amountDifference != null ? amountDifference.hashCode() : 0);
        result = 31 * result + (amountDifferenceFormatted != null ? amountDifferenceFormatted.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (periodStart != null ? periodStart.hashCode() : 0);
        result = 31 * result + (periodEnd != null ? periodEnd.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        result = 31 * result + (percentageSpent != null ? percentageSpent.hashCode() : 0);
        result = 31 * result + (daysRemaining != null ? daysRemaining.hashCode() : 0);
        return result;
    }
}
