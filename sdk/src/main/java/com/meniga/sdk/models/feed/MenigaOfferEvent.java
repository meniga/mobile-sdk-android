package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.offers.RelevanceHook;
import com.meniga.sdk.models.offers.enums.OfferState;
import com.meniga.sdk.models.offers.enums.RewardType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferEvent implements MenigaFeedItem, Serializable, Cloneable, Parcelable {

	private String validationToken;
	private MenigaDecimal reward;
	private RelevanceHook relevanceHook;
	public OfferState state;
	private int daysLeft;
	private String merchantName;
	private MenigaDecimal minimumPurchaseAmount;
	private MenigaDecimal maximumCashbackPerPurchase;
	private DateTime date;
	private long topicId;
	private String title;
	private String body;
	private String typeName;
	private RewardType rewardType;

	private String eventTypeIdentifier;
	private String topicName;

	protected MenigaOfferEvent() {
	}

	protected MenigaOfferEvent(Parcel in) {
		this.validationToken = in.readString();
		this.reward = (MenigaDecimal) in.readSerializable();
		this.relevanceHook = in.readParcelable(RelevanceHook.class.getClassLoader());
		int tmpState = in.readInt();
		this.state = tmpState == -1 ? null : OfferState.values()[tmpState];
		this.daysLeft = in.readInt();
		this.merchantName = in.readString();
		this.minimumPurchaseAmount = (MenigaDecimal) in.readSerializable();
		this.maximumCashbackPerPurchase = (MenigaDecimal) in.readSerializable();
		this.date = (DateTime) in.readSerializable();
		this.topicId = in.readLong();
		this.topicName = in.readString();
		this.title = in.readString();
		this.body = in.readString();
		this.typeName = in.readString();
		int tmpRewardType = in.readInt();
		this.rewardType = tmpRewardType == -1 ? null : RewardType.values()[tmpRewardType];
	}

	public String getValidationToken() {
		return validationToken;
	}

	public MenigaDecimal getReward() {
		return reward;
	}

	public RelevanceHook getRelevanceHook() {
		return relevanceHook;
	}

	public OfferState getState() {
		return state;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public MenigaDecimal getMinimumPurchaseAmount() {
		return minimumPurchaseAmount;
	}

	public MenigaDecimal getMaximumCashbackPerPurchase() {
		return maximumCashbackPerPurchase;
	}

	@Override
	public MenigaOfferEvent clone() throws CloneNotSupportedException {
		return (MenigaOfferEvent) super.clone();
	}

	public DateTime getDate() {
		return date;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getTypeName() {
		return typeName;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.validationToken);
		dest.writeSerializable(this.reward);
		dest.writeParcelable(this.relevanceHook, flags);
		dest.writeInt(this.state == null ? -1 : this.state.ordinal());
		dest.writeInt(this.daysLeft);
		dest.writeString(this.merchantName);
		dest.writeSerializable(this.minimumPurchaseAmount);
		dest.writeSerializable(this.maximumCashbackPerPurchase);
		dest.writeSerializable(this.date);
		dest.writeLong(this.topicId);
		dest.writeString(this.topicName);
		dest.writeString(this.title);
		dest.writeString(this.body);
		dest.writeString(this.typeName);
		dest.writeInt(this.rewardType == null ? -1 : this.rewardType.ordinal());
	}

	public static final Creator<MenigaOfferEvent> CREATOR = new Creator<MenigaOfferEvent>() {
		@Override
		public MenigaOfferEvent createFromParcel(Parcel source) {
			return new MenigaOfferEvent(source);
		}

		@Override
		public MenigaOfferEvent[] newArray(int size) {
			return new MenigaOfferEvent[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaOfferEvent that = (MenigaOfferEvent) o;

		if (daysLeft != that.daysLeft) return false;
		if (topicId != that.topicId) return false;
		if (validationToken != null ? !validationToken.equals(that.validationToken) : that.validationToken != null)
			return false;
		if (reward != null ? !reward.equals(that.reward) : that.reward != null) return false;
		if (relevanceHook != null ? !relevanceHook.equals(that.relevanceHook) : that.relevanceHook != null)
			return false;
		if (state != that.state) return false;
		if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null)
			return false;
		if (minimumPurchaseAmount != null ? !minimumPurchaseAmount.equals(that.minimumPurchaseAmount) : that.minimumPurchaseAmount != null)
			return false;
		if (maximumCashbackPerPurchase != null ? !maximumCashbackPerPurchase.equals(that.maximumCashbackPerPurchase) : that.maximumCashbackPerPurchase != null)
			return false;
		if (date != null ? !date.equals(that.date) : that.date != null) return false;
		if (topicName != null ? !topicName.equals(that.topicName) : that.topicName != null)
			return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (body != null ? !body.equals(that.body) : that.body != null) return false;
		if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null)
			return false;
		return rewardType == that.rewardType;

	}

	@Override
	public int hashCode() {
		int result = validationToken != null ? validationToken.hashCode() : 0;
		result = 31 * result + (reward != null ? reward.hashCode() : 0);
		result = 31 * result + (relevanceHook != null ? relevanceHook.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + daysLeft;
		result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
		result = 31 * result + (minimumPurchaseAmount != null ? minimumPurchaseAmount.hashCode() : 0);
		result = 31 * result + (maximumCashbackPerPurchase != null ? maximumCashbackPerPurchase.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (int) (topicId ^ (topicId >>> 32));
		result = 31 * result + (topicName != null ? topicName.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
		result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
		result = 31 * result + (rewardType != null ? rewardType.hashCode() : 0);
		return result;
	}
}
