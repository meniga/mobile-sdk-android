package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.upcoming.enums.RecurringPatternStatus;
import com.meniga.sdk.models.upcoming.enums.RecurringPatternType;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingRecurringPattern implements Serializable, Parcelable, Cloneable {
	protected long id;
	protected String text;
	protected MenigaDecimal amountInCurrency;
	protected String currencyCode;
	protected Long categoryId;
	protected Long accountId;
	protected MenigaUpcomingCronExpression pattern;
	protected DateTime repeatUntil;
	protected Boolean isWatched;
	protected Boolean isFlagged;
	protected RecurringPatternType type;
	protected RecurringPatternStatus status;

	public MenigaUpcomingRecurringPattern() {
	}

	@Override
	public MenigaUpcomingRecurringPattern clone() throws CloneNotSupportedException {
		return (MenigaUpcomingRecurringPattern) super.clone();
	}

	/**
	 * @return The id of the upcoming transaction recurring pattern
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The transaction text for the pattern
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return The amount of the upcoming transaction in the currency of CurrencyCode
	 */
	public MenigaDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	/**
	 * @return The currency of the pattern
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return The id of the category of the recurring pattern
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @return The id of the account of the recurring pattern
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @return A repeat pattern (Cron expression)
	 */
	public MenigaUpcomingCronExpression getPattern() {
		return pattern;
	}

	/**
	 * @return Indicates when the pattern finishes repeating
	 */
	public DateTime getRepeatUntil() {
		return repeatUntil;
	}

	/**
	 * @return Indicates whether the patterns transactions are to be watched
	 */
	public Boolean getWatched() {
		return isWatched;
	}

	/**
	 * @return Indicates whether the patterns transactions are to be flagged
	 */
	public Boolean getFlagged() {
		return isFlagged;
	}

	/**
	 * @return The recurring type = ['Unknown', 'Detected', 'Manual']
	 */
	public RecurringPatternType getType() {
		return type;
	}

	/**
	 * @return The recurring status = ['Unknown', 'Suggested', 'Accepted', 'Rejected']
	 */
	public RecurringPatternStatus getStatus() {
		return status;
	}

	public MenigaUpcomingRecurringPattern setId(long id) {
		this.id = id;
		return this;
	}

	public MenigaUpcomingRecurringPattern setText(String text) {
		this.text = text;
		return this;
	}

	public MenigaUpcomingRecurringPattern setAmountInCurrency(MenigaDecimal amountInCurrency) {
		this.amountInCurrency = amountInCurrency;
		return this;
	}

	public MenigaUpcomingRecurringPattern setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	}

	public MenigaUpcomingRecurringPattern setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	public MenigaUpcomingRecurringPattern setAccountId(Long accountId) {
		this.accountId = accountId;
		return this;
	}

	public MenigaUpcomingRecurringPattern setPattern(MenigaUpcomingCronExpression pattern) {
		this.pattern = pattern;
		return this;
	}

	public MenigaUpcomingRecurringPattern setRepeatUntil(DateTime repeatUntil) {
		this.repeatUntil = repeatUntil;
		return this;
	}

	public MenigaUpcomingRecurringPattern setWatched(Boolean watched) {
		isWatched = watched;
		return this;
	}

	public MenigaUpcomingRecurringPattern setFlagged(Boolean flagged) {
		isFlagged = flagged;
		return this;
	}

	public MenigaUpcomingRecurringPattern setType(RecurringPatternType type) {
		this.type = type;
		return this;
	}

	public MenigaUpcomingRecurringPattern setStatus(RecurringPatternStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingRecurringPattern that = (MenigaUpcomingRecurringPattern) o;

		if (id != that.id) {
			return false;
		}
		if (text != null ? !text.equals(that.text) : that.text != null) {
			return false;
		}
		if (amountInCurrency != null ? !amountInCurrency.equals(that.amountInCurrency) : that.amountInCurrency != null) {
			return false;
		}
		if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) {
			return false;
		}
		if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) {
			return false;
		}
		if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) {
			return false;
		}
		if (pattern != null ? !pattern.equals(that.pattern) : that.pattern != null) {
			return false;
		}
		if (repeatUntil != null ? !repeatUntil.equals(that.repeatUntil) : that.repeatUntil != null) {
			return false;
		}
		if (isWatched != null ? !isWatched.equals(that.isWatched) : that.isWatched != null) {
			return false;
		}
		if (isFlagged != null ? !isFlagged.equals(that.isFlagged) : that.isFlagged != null) {
			return false;
		}
		if (type != that.type) {
			return false;
		}
		return status == that.status;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
		result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
		result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
		result = 31 * result + (repeatUntil != null ? repeatUntil.hashCode() : 0);
		result = 31 * result + (isWatched != null ? isWatched.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.text);
		dest.writeSerializable(this.amountInCurrency);
		dest.writeString(this.currencyCode);
		dest.writeValue(this.categoryId);
		dest.writeValue(this.accountId);
		dest.writeParcelable(this.pattern, flags);
		dest.writeSerializable(this.repeatUntil);
		dest.writeValue(this.isWatched);
		dest.writeValue(this.isFlagged);
		dest.writeInt(this.type == null ? -1 : this.type.ordinal());
		dest.writeInt(this.status == null ? -1 : this.status.ordinal());
	}

	protected MenigaUpcomingRecurringPattern(Parcel in) {
		this.id = in.readLong();
		this.text = in.readString();
		this.amountInCurrency = (MenigaDecimal) in.readSerializable();
		this.currencyCode = in.readString();
		this.categoryId = (Long) in.readValue(Long.class.getClassLoader());
		this.accountId = (Long) in.readValue(Long.class.getClassLoader());
		this.pattern = in.readParcelable(MenigaUpcomingCronExpression.class.getClassLoader());
		this.repeatUntil = (DateTime) in.readSerializable();
		this.isWatched = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isFlagged = (Boolean) in.readValue(Boolean.class.getClassLoader());
		int tmpType = in.readInt();
		this.type = tmpType == -1 ? null : RecurringPatternType.values()[tmpType];
		int tmpStatus = in.readInt();
		this.status = tmpStatus == -1 ? null : RecurringPatternStatus.values()[tmpStatus];
	}

	public static final Creator<MenigaUpcomingRecurringPattern> CREATOR = new Creator<MenigaUpcomingRecurringPattern>() {
		@Override
		public MenigaUpcomingRecurringPattern createFromParcel(Parcel source) {
			return new MenigaUpcomingRecurringPattern(source);
		}

		@Override
		public MenigaUpcomingRecurringPattern[] newArray(int size) {
			return new MenigaUpcomingRecurringPattern[size];
		}
	};
}
