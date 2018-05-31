package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.feed.enums.ScheduledEventType;
import com.meniga.sdk.models.feed.operators.MenigaFeedOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Model class for the scheduled event, such as a weekly/monthly expense report.
 */
public class MenigaScheduledEvent implements MenigaFeedItem, Serializable, Cloneable, Parcelable {
	public static final Parcelable.Creator<MenigaScheduledEvent> CREATOR = new Parcelable.Creator<MenigaScheduledEvent>() {
		@Override
		public MenigaScheduledEvent createFromParcel(Parcel source) {
			return new MenigaScheduledEvent(source);
		}
		@Override
		public MenigaScheduledEvent[] newArray(int size) {
			return new MenigaScheduledEvent[size];
		}
	};

	protected static MenigaFeedOperations apiOperator;

	protected long id;
	protected MenigaDecimal totalExpenses;
	protected MenigaDecimal totalIncome;
	protected int transactionCount;
	protected Map<Long, MenigaDecimal> expensesPerCategory;
	protected Map<Long, MenigaDecimal> incomePerCategory;
	protected Map<Long, Integer> transactionCountPerMerchant;
	protected ScheduledEventType scheduledEventType;
	protected DateTime startDate;
	protected DateTime endDate;
	protected List<ScheduledDayTransactions> transactionsPerDay;
	protected Long topicId;
	protected DateTime date;
	protected String title;
	protected String body;
	protected String typeName;

	protected String eventTypeIdentifier;
	protected String topicName;

	protected MenigaScheduledEvent() {
	}

	protected MenigaScheduledEvent(Parcel in) {
		this.id = in.readLong();
		this.totalExpenses = (MenigaDecimal) in.readSerializable();
		this.totalIncome = (MenigaDecimal) in.readSerializable();
		this.transactionCount = in.readInt();
		int expensesPerCategorySize = in.readInt();
		this.expensesPerCategory = new HashMap<>(expensesPerCategorySize);
		for (int i = 0; i < expensesPerCategorySize; i++) {
			Long key = (Long) in.readValue(Long.class.getClassLoader());
			MenigaDecimal value = (MenigaDecimal) in.readSerializable();
			this.expensesPerCategory.put(key, value);
		}
		int incomePerCategorySize = in.readInt();
		this.incomePerCategory = new HashMap<>(incomePerCategorySize);
		for (int i = 0; i < incomePerCategorySize; i++) {
			Long key = (Long) in.readValue(Long.class.getClassLoader());
			MenigaDecimal value = (MenigaDecimal) in.readSerializable();
			this.incomePerCategory.put(key, value);
		}
		int transactionCountPerMerchantSize = in.readInt();
		this.transactionCountPerMerchant = new HashMap<>(transactionCountPerMerchantSize);
		for (int i = 0; i < transactionCountPerMerchantSize; i++) {
			Long key = (Long) in.readValue(Long.class.getClassLoader());
			Integer value = (Integer) in.readValue(Integer.class.getClassLoader());
			this.transactionCountPerMerchant.put(key, value);
		}
		int tmpScheduledEventType = in.readInt();
		this.scheduledEventType = tmpScheduledEventType == -1 ? null : ScheduledEventType.values()[tmpScheduledEventType];
		this.endDate = (DateTime) in.readSerializable();
		this.startDate = (DateTime) in.readSerializable();
		this.transactionsPerDay = in.createTypedArrayList(ScheduledDayTransactions.CREATOR);
		this.topicId = (Long) in.readValue(Long.class.getClassLoader());
		this.date = (DateTime) in.readSerializable();
		this.topicName = in.readString();
		this.title = in.readString();
		this.body = in.readString();
		this.typeName = in.readString();
	}

	public static void setOperator(MenigaFeedOperations apiOperatorIn) {
		apiOperator = apiOperatorIn;
	}

	@Override
	public MenigaScheduledEvent clone() throws CloneNotSupportedException {
		return (MenigaScheduledEvent) super.clone();
	}

	public long getId() {
		return this.id;
	}

	public MenigaDecimal getTotalExpenses() {
		return this.totalExpenses;
	}

	public MenigaDecimal getTotalIncome() {
		return this.totalIncome;
	}

	public int getTransactionCount() {
		return this.transactionCount;
	}

	public Map<Long, MenigaDecimal> getExpensesPerCategory() {
		return this.expensesPerCategory;
	}

	public Map<Long, MenigaDecimal> getIncomePerCategory() {
		return this.incomePerCategory;
	}

	public Map<Long, Integer> getTransactionCountPerMerchant() {
		return this.transactionCountPerMerchant;
	}

	public ScheduledEventType getScheduledEventType() {
		return this.scheduledEventType;
	}

	public DateTime getEndDate() {
		return this.endDate;
	}

	public DateTime getStartDate() {
		return this.startDate;
	}

	public List<ScheduledDayTransactions> getTransactionsPerDay() {
		return this.transactionsPerDay;
	}

	public Long getTopicId() {
		return this.topicId;
	}

	public DateTime getDate() {
		return this.date;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier;
	}

	public String getTopicName() {
		return this.topicName;
	}

	public String getTitle() {
		return this.title;
	}

	public String getBody() {
		return this.body;
	}

	public String getTypeName() {
		return this.typeName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaScheduledEvent that = (MenigaScheduledEvent) o;

		if (this.id != that.id) {
			return false;
		}
		if (this.transactionCount != that.transactionCount) {
			return false;
		}
		if (this.totalExpenses != null ? !this.totalExpenses.equals(that.totalExpenses) : that.totalExpenses != null) {
			return false;
		}
		if (this.totalIncome != null ? !this.totalIncome.equals(that.totalIncome) : that.totalIncome != null) {
			return false;
		}
		if (this.expensesPerCategory != null ? !this.expensesPerCategory.equals(that.expensesPerCategory) : that.expensesPerCategory != null) {
			return false;
		}
		if (this.incomePerCategory != null ? !this.incomePerCategory.equals(that.incomePerCategory) : that.incomePerCategory != null) {
			return false;
		}
		if (this.transactionCountPerMerchant != null ? !this.transactionCountPerMerchant.equals(that.transactionCountPerMerchant) : that.transactionCountPerMerchant != null) {
			return false;
		}
		if (this.scheduledEventType != that.scheduledEventType) {
			return false;
		}
		if (this.endDate != null ? !this.endDate.equals(that.endDate) : that.endDate != null) {
			return false;
		}
		if (this.startDate != null ? !this.startDate.equals(that.startDate) : that.startDate != null) {
			return false;
		}
		if (this.transactionsPerDay != null ? !this.transactionsPerDay.equals(that.transactionsPerDay) : that.transactionsPerDay != null) {
			return false;
		}
		if (this.topicId != null ? !this.topicId.equals(that.topicId) : that.topicId != null) {
			return false;
		}
		if (this.date != null ? !this.date.equals(that.date) : that.date != null) {
			return false;
		}
		if (this.topicName != null ? !this.topicName.equals(that.topicName) : that.topicName != null) {
			return false;
		}
		if (this.title != null ? !this.title.equals(that.title) : that.title != null) {
			return false;
		}
		if (this.body != null ? !this.body.equals(that.body) : that.body != null) {
			return false;
		}
		return this.typeName != null ? this.typeName.equals(that.typeName) : that.typeName == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (this.id ^ (this.id >>> 32));
		result = 31 * result + (this.totalExpenses != null ? this.totalExpenses.hashCode() : 0);
		result = 31 * result + (this.totalIncome != null ? this.totalIncome.hashCode() : 0);
		result = 31 * result + this.transactionCount;
		result = 31 * result + (this.expensesPerCategory != null ? this.expensesPerCategory.hashCode() : 0);
		result = 31 * result + (this.incomePerCategory != null ? this.incomePerCategory.hashCode() : 0);
		result = 31 * result + (this.transactionCountPerMerchant != null ? this.transactionCountPerMerchant.hashCode() : 0);
		result = 31 * result + (this.scheduledEventType != null ? this.scheduledEventType.hashCode() : 0);
		result = 31 * result + (this.endDate != null ? this.endDate.hashCode() : 0);
		result = 31 * result + (this.startDate != null ? this.startDate.hashCode() : 0);
		result = 31 * result + (this.transactionsPerDay != null ? this.transactionsPerDay.hashCode() : 0);
		result = 31 * result + (this.topicId != null ? this.topicId.hashCode() : 0);
		result = 31 * result + (this.date != null ? this.date.hashCode() : 0);
		result = 31 * result + (this.topicName != null ? this.topicName.hashCode() : 0);
		result = 31 * result + (this.title != null ? this.title.hashCode() : 0);
		result = 31 * result + (this.body != null ? this.body.hashCode() : 0);
		result = 31 * result + (this.typeName != null ? this.typeName.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeSerializable(this.totalExpenses);
		dest.writeSerializable(this.totalIncome);
		dest.writeInt(this.transactionCount);
		dest.writeInt(this.expensesPerCategory.size());
		for (Map.Entry<Long, MenigaDecimal> entry : this.expensesPerCategory.entrySet()) {
			dest.writeValue(entry.getKey());
			dest.writeSerializable(entry.getValue());
		}
		dest.writeInt(this.incomePerCategory.size());
		for (Map.Entry<Long, MenigaDecimal> entry : this.incomePerCategory.entrySet()) {
			dest.writeValue(entry.getKey());
			dest.writeSerializable(entry.getValue());
		}
		dest.writeInt(this.transactionCountPerMerchant.size());
		for (Map.Entry<Long, Integer> entry : this.transactionCountPerMerchant.entrySet()) {
			dest.writeValue(entry.getKey());
			dest.writeValue(entry.getValue());
		}
		dest.writeInt(this.scheduledEventType == null ? -1 : this.scheduledEventType.ordinal());
		dest.writeSerializable(this.endDate);
		dest.writeSerializable(this.startDate);
		dest.writeTypedList(this.transactionsPerDay);
		dest.writeValue(this.topicId);
		dest.writeSerializable(this.date);
		dest.writeString(this.topicName);
		dest.writeString(this.title);
		dest.writeString(this.body);
		dest.writeString(this.typeName);
	}

	/*
	API methods below
	 */

	public static Result<MenigaScheduledEvent> fetch(long id) {
		return apiOperator.getScheduledEvent(id);
	}
}
