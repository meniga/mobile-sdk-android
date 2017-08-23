package com.meniga.sdk.models.challenges;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.challenges.enums.ChallengeInterval;
import com.meniga.sdk.models.challenges.enums.ChallengeType;
import com.meniga.sdk.models.challenges.enums.CustomChallengeColor;
import com.meniga.sdk.models.challenges.operators.MenigaChallengesOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaChallenge extends StateObject implements Serializable, Cloneable, Parcelable {

	private static MenigaChallengesOperations apiOperations;

	private UUID id;
	private boolean accepted;
	private DateTime acceptedDate;
	private Long topicId;
	private String title;
	private String description;
	private ChallengeType type;
	private DateTime publishDate;
	private DateTime startDate;
	private DateTime endDate;
	private List<Long> categoryIds;
	private Integer targetPercentage;
	private MenigaDecimal targetAmount;
	private MenigaDecimal spentAmount;
	private String iconUrl;
	private ChallengeInterval recurringInterval;
	@SerializedName("color")
	private CustomChallengeColor customChallengeColor;
	private boolean categoryIdsDirty;

	protected MenigaChallenge() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.id);
		dest.writeByte(this.accepted ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.acceptedDate);
		dest.writeValue(this.topicId);
		dest.writeString(this.title);
		dest.writeString(this.description);
		dest.writeInt(this.type == null ? -1 : this.type.ordinal());
		dest.writeSerializable(this.publishDate);
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
		if (categoryIds == null) {
			dest.writeInt(1);
		} else {
			dest.writeInt(0);
			dest.writeList(this.categoryIds);
		}
		dest.writeValue(this.targetPercentage);
		dest.writeSerializable(this.targetAmount);
		dest.writeSerializable(this.spentAmount);
		dest.writeString(this.iconUrl);
		dest.writeInt(this.recurringInterval == null ? -1 : this.recurringInterval.ordinal());
		dest.writeInt(this.customChallengeColor == null ? -1 : this.customChallengeColor.ordinal());
	}

	protected MenigaChallenge(Parcel in) {
		this.id = (UUID) in.readSerializable();
		this.accepted = in.readByte() != 0;
		this.acceptedDate = (DateTime) in.readSerializable();
		this.topicId = (Long) in.readValue(Long.class.getClassLoader());
		this.title = in.readString();
		this.description = in.readString();
		int tmpType = in.readInt();
		this.type = tmpType == -1 ? null : ChallengeType.values()[tmpType];
		this.publishDate = (DateTime) in.readSerializable();
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		boolean catsWereNull = in.readInt() == 1;
		if (!catsWereNull) {
			this.categoryIds = new ArrayList<>();
			in.readList(this.categoryIds, Long.class.getClassLoader());
		}
		this.targetPercentage = (Integer) in.readValue(Integer.class.getClassLoader());
		this.targetAmount = (MenigaDecimal) in.readSerializable();
		this.spentAmount = (MenigaDecimal) in.readSerializable();
		this.iconUrl = in.readString();
		int tmpRecurringInterval = in.readInt();
		this.recurringInterval = tmpRecurringInterval == -1 ? null : ChallengeInterval.values()[tmpRecurringInterval];
		int tmpcustmChallengeColorInterval = in.readInt();
		this.customChallengeColor = tmpcustmChallengeColorInterval == -1 ? null : CustomChallengeColor.values()[tmpcustmChallengeColorInterval];
	}

	public static final Parcelable.Creator<MenigaChallenge> CREATOR = new Parcelable.Creator<MenigaChallenge>() {
		@Override
		public MenigaChallenge createFromParcel(Parcel source) {
			return new MenigaChallenge(source);
		}

		@Override
		public MenigaChallenge[] newArray(int size) {
			return new MenigaChallenge[size];
		}
	};

	public UUID getId() {
		return id;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public DateTime getAcceptedDate() {
		return acceptedDate;
	}

	public Long getTopicId() {
		return topicId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public ChallengeType getType() {
		return type;
	}

	public DateTime getPublishDate() {
		return publishDate;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public List<Long> getCategoryIds() {
		if (categoryIds == null) {
			return new ArrayList<>();
		}
		return categoryIds;
	}

	public Integer getTargetPercentage() {
		return targetPercentage;
	}

	public MenigaDecimal getTargetAmount() {
		if (targetAmount == null) {
			return new MenigaDecimal(0);
		}
		return targetAmount;
	}

	public MenigaDecimal getSpentAmount() {
		if (spentAmount == null) {
			return new MenigaDecimal(0);
		}
		return spentAmount;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public ChallengeInterval getRecurringInterval() {
		return recurringInterval;
	}

	public CustomChallengeColor getCustomChallengeColor() {
		return customChallengeColor;
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaMerchantOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaChallengesOperations operator) {
		MenigaChallenge.apiOperations = operator;
	}

	/*
	Setters below
	 */

	public void setType(ChallengeType type) {
		changed();
		this.type = type;
	}

	public void setTargetAmount(MenigaDecimal amt) {
		changed();
		targetAmount = amt.abs();
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaChallenge)) {
			return;
		}

		MenigaChallenge last = (MenigaChallenge) lastRevision;
		type = last.type;
		targetAmount = last.targetAmount;
		title = last.title;
		description = last.description;
		startDate = last.startDate;
		endDate = last.endDate;
		iconUrl = last.iconUrl;
		categoryIds = last.categoryIds;
		customChallengeColor = last.customChallengeColor;
	}

	@Override
	public MenigaChallenge clone() throws CloneNotSupportedException {
		return (MenigaChallenge) super.clone();
	}

	@Override
	public String toString() {
		return title;
	}

	/**
	 * Merges another MenigaChallenge object into this. Replaces null fields on this object with
	 * fields from other if the field in other is not null
	 *
	 * @param other The other MenigaChallenge object to merge with this one
	 */
	public void merge(MenigaChallenge other) {
		if (other == null) {
			return;
		}
		Class myClass = getClass();
		if (categoryIds != null && categoryIds.size() == 0) {
			categoryIds = null;
		}
		if (other.categoryIds != null && other.categoryIds.size() == 0) {
			other.categoryIds = null;
		}
		for (Field field : myClass.getDeclaredFields()) {
			try {
				Object myValue = field.get(this);
				Object otherValue = field.get(other);
				if (myValue == null && otherValue != null) {
					field.setAccessible(true);
					field.set(this, otherValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaChallenge challenge = (MenigaChallenge) o;

		if (accepted != challenge.accepted) {
			return false;
		}
		if (id != null ? !id.equals(challenge.id) : challenge.id != null) {
			return false;
		}
		if (acceptedDate != null ? !acceptedDate.equals(challenge.acceptedDate) : challenge.acceptedDate != null) {
			return false;
		}
		if (topicId != null ? !topicId.equals(challenge.topicId) : challenge.topicId != null) {
			return false;
		}
		if (title != null ? !title.equals(challenge.title) : challenge.title != null) {
			return false;
		}
		if (description != null ? !description.equals(challenge.description) : challenge.description != null) {
			return false;
		}
		if (type != challenge.type) {
			return false;
		}
		if (publishDate != null ? !publishDate.equals(challenge.publishDate) : challenge.publishDate != null) {
			return false;
		}
		if (startDate != null ? !startDate.equals(challenge.startDate) : challenge.startDate != null) {
			return false;
		}
		if (endDate != null ? !endDate.equals(challenge.endDate) : challenge.endDate != null) {
			return false;
		}
		if (categoryIds != null ? !categoryIds.equals(challenge.categoryIds) : challenge.categoryIds != null) {
			return false;
		}
		if (targetPercentage != null ? !targetPercentage.equals(challenge.targetPercentage) : challenge.targetPercentage != null) {
			return false;
		}
		if (targetAmount != null ? !targetAmount.equals(challenge.targetAmount) : challenge.targetAmount != null) {
			return false;
		}
		if (spentAmount != null ? !spentAmount.equals(challenge.spentAmount) : challenge.spentAmount != null) {
			return false;
		}
		if (iconUrl != null ? !iconUrl.equals(challenge.iconUrl) : challenge.iconUrl != null) {
			return false;
		}
		if (recurringInterval != challenge.recurringInterval) {
			return false;
		}
		return customChallengeColor == challenge.customChallengeColor;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (accepted ? 1 : 0);
		result = 31 * result + (acceptedDate != null ? acceptedDate.hashCode() : 0);
		result = 31 * result + (topicId != null ? topicId.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (targetPercentage != null ? targetPercentage.hashCode() : 0);
		result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
		result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
		result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
		result = 31 * result + (recurringInterval != null ? recurringInterval.hashCode() : 0);
		result = 31 * result + (customChallengeColor != null ? customChallengeColor.hashCode() : 0);
		return result;
	}

	/**
	 * Gets all challenges, expired, suggested and accepted alike but not expired ones
	 *
	 * @return A task that includes all the challenge objects that match the default parameters
	 */
	public static Result<List<MenigaChallenge>> fetch() {
		return MenigaChallenge.fetch(true, false, false);
	}

	/**
	 * Gets a challenge by its unique id.
	 *
	 * @param id The id of the challenge to getChallenge
	 * @return A task that includes the challenge object that matches the id
	 */
	public static Result<MenigaChallenge> fetch(UUID id) {
		return MenigaChallenge.apiOperations.getChallenge(id);
	}

	/**
	 * Refreshes this instance with fresh new data from the server (if there have been any changes that is).
	 *
	 * @return A void task. The instance of this challenge will have been updated
	 */
	public Result<MenigaChallenge> refresh() {
		Result<MenigaChallenge> task = MenigaChallenge.fetch(id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaChallenge>() {
			@Override
			public void onFinished(MenigaChallenge result, boolean failed) {
				if (!failed && result != null) {
					try {
						Merge.merge(MenigaChallenge.this, result);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Gets all challenges, both new ones and accepted ones, also excluded ones if includeExpired is set to true
	 *
	 * @return A task that includes all the challenge objects that match the query parameters
	 */
	public static Result<List<MenigaChallenge>> fetch(boolean includeExpired, boolean excludeSuggested, boolean excludeAccepted) {
		return MenigaChallenge.apiOperations.getChallenges(includeExpired, excludeSuggested, excludeAccepted);
	}

	/**
	 * Updates the instance on the server. All fields that have a setter can be updated
	 *
	 * @return A task indicating if the operation was successful or not
	 */
	public Result<Void> update() {
		Result<Void> task = MenigaChallenge.apiOperations.updateChallenge(this, categoryIdsDirty);
		categoryIdsDirty = false;
		return task;
	}

	/**
	 * Delete the instance by id on the server.
	 *
	 * @return A task indicating if the operation was successful or not
	 */
	public static Result<Void> delete(UUID id) {
		Result<Void> task = MenigaChallenge.apiOperations.deleteChallenge(id);
		return task;
	}

	/**
	 * Delete the instance on the server.
	 *
	 * @return A task indicating if the operation was successful or not
	 */
	public Result<Void> delete() {
		Result<Void> task = MenigaChallenge.apiOperations.deleteChallenge(this.getId());
		return task;
	}

	/**
	 * Marks the challenges object as accepted
	 *
	 * @return A Task of type void, the task will have an error and be marked as failed if accept is not successful
	 */
	public Result<MenigaChallenge> accept(MenigaDecimal targetAmount) {
		if (targetAmount.lessThan(0.0)) {
			targetAmount = targetAmount.abs();
		}
		Result<MenigaChallenge> task = MenigaChallenge.apiOperations.acceptChallenge(id, targetAmount);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaChallenge>() {
			@Override
			public void onFinished(MenigaChallenge result, boolean failed) {
				if (!failed && result != null) {
					merge(result);
					accepted = true;
				}

			}
		});
	}

	/**
	 * Creates a new custom challenge object with a default value for iconId
	 *
	 * @param title        Title of the challenge
	 * @param description  Description of the challange
	 * @param startDate    Time period start for when the challenge starts
	 * @param endDate      Time period end for when the challenge starts
	 * @param categoryIds  Category ids that this challenge applies to
	 * @param targetAmount The budget target amount for the challenge
	 * @param color        Color of the challenge being created
	 * @return A task containing the newly created custom challenge
	 */
	public static Result<MenigaChallenge> create(String title, String description, DateTime startDate,
	                                             DateTime endDate, List<Long> categoryIds,
	                                             MenigaDecimal targetAmount, CustomChallengeColor color) {
		return MenigaChallenge.create(title, description, startDate, endDate, categoryIds, targetAmount, null, color);
	}

	/**
	 * Creates a new custom challenge object
	 *
	 * @param title        Title of the challenge
	 * @param description  Description of the challange
	 * @param startDate    Time period start for when the challenge starts
	 * @param endDate      Time period end for when the challenge starts
	 * @param categoryIds  Category ids that this challenge applies to
	 * @param targetAmount The budget target amount for the challenge
	 * @param iconId       Id of the icon associated with the challenge
	 * @param color        Color of the challenge being created
	 * @return A task containing the newly created custom challenge
	 */
	public static Result<MenigaChallenge> create(String title, String description, DateTime startDate,
	                                             DateTime endDate, List<Long> categoryIds,
	                                             MenigaDecimal targetAmount, Long iconId, CustomChallengeColor color) {
		return MenigaChallenge.apiOperations.createChallenge(
				title,
				description,
				startDate,
				endDate,
				categoryIds,
				targetAmount,
				iconId,
				color
		);
	}
}
