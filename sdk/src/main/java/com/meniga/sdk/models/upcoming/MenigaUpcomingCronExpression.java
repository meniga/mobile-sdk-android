package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.upcoming.enums.CronDayOfWeek;
import com.meniga.sdk.models.upcoming.enums.CronMonth;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingCronExpression implements Serializable, Parcelable, Cloneable {
	protected String dayOfMonth;
	protected Integer dayOfMonthInterval;
	protected CronMonth month;
	protected Integer monthInterval;
	protected String dayOfWeek;
	protected Integer dayOfWeekInterval;
	protected Integer weekOfYear;
	protected Integer weekInterval;

	public MenigaUpcomingCronExpression() {
	}

	@Override
	public MenigaUpcomingCronExpression clone() throws CloneNotSupportedException {
		return (MenigaUpcomingCronExpression) super.clone();
	}

	/**
	 * @return The day-of-month field of the cron expression. If null, the wildcard character * is assumed
	 */
	public String getDayOfMonth() {
		return dayOfMonth;
	}

	/**
	 * @return The interval field for the day-of-month in the cron expression. Used in conjunction
	 * with DayOfMonth. If null, the specified DayOfMonth is assumed.
	 */
	public Integer getDayOfMonthInterval() {
		return dayOfMonthInterval;
	}

	/**
	 * @return The month field of the cron expression. If null, the wildcard character *
	 * is assumed = ['January', 'Februray', 'March', 'April', 'May', 'June', 'July', 'August',
	 * 'September', 'October', 'November', 'December']
	 */
	public CronMonth getMonth() {
		return month;
	}

	/**
	 * @return The interval field for the month in the cron expression. Used in conjunction with Month.
	 * If null, the specified month is assumed
	 */
	public Integer getMonthInterval() {
		return monthInterval;
	}

	/**
	 * @return The day-of-week field of the cron expression. If null, the wildcard character * is
	 * assumed. = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @return The interval field for the day-of-week in the cron expression. Used in conjunction
	 * with DayOfWeek. If null, the specified DayOfWeek is assumed
	 */
	public Integer getDayOfWeekInterval() {
		return dayOfWeekInterval;
	}

	/**
	 * @return The week-of-year field of the cron expression. If null, the wildcard character * is assumed
	 */
	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	/**
	 * @return The interval field for the week-of-year in the cron expression. Used in conjunction
	 * with WeekOfYear. If null, the specified WeekOfYear is assumed
	 */
	public Integer getWeekInterval() {
		return weekInterval;
	}

	public MenigaUpcomingCronExpression setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		return this;
	}

	public MenigaUpcomingCronExpression setDayOfMonthInterval(Integer dayOfMonthInterval) {
		this.dayOfMonthInterval = dayOfMonthInterval;
		return this;
	}

	public MenigaUpcomingCronExpression setMonth(CronMonth month) {
		this.month = month;
		return this;
	}

	public MenigaUpcomingCronExpression setMonthInterval(Integer monthInterval) {
		this.monthInterval = monthInterval;
		return this;
	}

	public MenigaUpcomingCronExpression setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}

	public MenigaUpcomingCronExpression setDayOfWeekInterval(Integer dayOfWeekInterval) {
		this.dayOfWeekInterval = dayOfWeekInterval;
		return this;
	}

	public MenigaUpcomingCronExpression setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
		return this;
	}

	public MenigaUpcomingCronExpression setWeekInterval(Integer weekInterval) {
		this.weekInterval = weekInterval;
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

		MenigaUpcomingCronExpression that = (MenigaUpcomingCronExpression) o;

		if (dayOfMonth != null ? !dayOfMonth.equals(that.dayOfMonth) : that.dayOfMonth != null) {
			return false;
		}
		if (dayOfMonthInterval != null ? !dayOfMonthInterval.equals(that.dayOfMonthInterval) : that.dayOfMonthInterval != null) {
			return false;
		}
		if (month != that.month) {
			return false;
		}
		if (monthInterval != null ? !monthInterval.equals(that.monthInterval) : that.monthInterval != null) {
			return false;
		}
		if (dayOfWeek != that.dayOfWeek) {
			return false;
		}
		if (dayOfWeekInterval != null ? !dayOfWeekInterval.equals(that.dayOfWeekInterval) : that.dayOfWeekInterval != null) {
			return false;
		}
		if (weekOfYear != null ? !weekOfYear.equals(that.weekOfYear) : that.weekOfYear != null) {
			return false;
		}
		return weekInterval != null ? weekInterval.equals(that.weekInterval) : that.weekInterval == null;
	}

	@Override
	public int hashCode() {
		int result = dayOfMonth != null ? dayOfMonth.hashCode() : 0;
		result = 31 * result + (dayOfMonthInterval != null ? dayOfMonthInterval.hashCode() : 0);
		result = 31 * result + (month != null ? month.hashCode() : 0);
		result = 31 * result + (monthInterval != null ? monthInterval.hashCode() : 0);
		result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
		result = 31 * result + (dayOfWeekInterval != null ? dayOfWeekInterval.hashCode() : 0);
		result = 31 * result + (weekOfYear != null ? weekOfYear.hashCode() : 0);
		result = 31 * result + (weekInterval != null ? weekInterval.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(dayOfMonth);
		dest.writeValue(dayOfMonthInterval);
		dest.writeInt(month == null ? -1 : month.ordinal());
		dest.writeValue(monthInterval);
		dest.writeString(dayOfWeek);
		dest.writeValue(dayOfWeekInterval);
		dest.writeValue(weekOfYear);
		dest.writeValue(weekInterval);
	}

	protected MenigaUpcomingCronExpression(Parcel in) {
		dayOfMonth = in.readString();
		dayOfMonthInterval = (Integer) in.readValue(Integer.class.getClassLoader());
		int tmpMonth = in.readInt();
		month = tmpMonth == -1 ? null : CronMonth.values()[tmpMonth];
		monthInterval = (Integer) in.readValue(Integer.class.getClassLoader());
		dayOfWeek = in.readString();
		dayOfWeekInterval = (Integer) in.readValue(Integer.class.getClassLoader());
		weekOfYear = (Integer) in.readValue(Integer.class.getClassLoader());
		weekInterval = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<MenigaUpcomingCronExpression> CREATOR = new Creator<MenigaUpcomingCronExpression>() {
		@Override
		public MenigaUpcomingCronExpression createFromParcel(Parcel source) {
			return new MenigaUpcomingCronExpression(source);
		}

		@Override
		public MenigaUpcomingCronExpression[] newArray(int size) {
			return new MenigaUpcomingCronExpression[size];
		}
	};
}
