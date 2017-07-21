package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingComment implements Serializable, Parcelable, Cloneable {
	private long id;
	private DateTime created;
	private DateTime modified;
	private String comment;

	protected MenigaUpcomingComment() {
	}

	@Override
	public MenigaUpcomingComment clone() throws CloneNotSupportedException {
		return (MenigaUpcomingComment) super.clone();
	}

	/**
	 * @return The id of the upcoming transaction comment
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The created date of the upcoming transaction comment
	 */
	public DateTime getCreated() {
		return created;
	}

	/**
	 * @return The modified date of the upcoming transaction comment
	 */
	public DateTime getModified() {
		return modified;
	}

	/**
	 * @return The upcoming transaction comment
	 */
	public String getComment() {
		return comment;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeSerializable(this.created);
		dest.writeSerializable(this.modified);
		dest.writeString(this.comment);
	}

	protected MenigaUpcomingComment(Parcel in) {
		this.id = in.readLong();
		this.created = (DateTime) in.readSerializable();
		this.modified = (DateTime) in.readSerializable();
		this.comment = in.readString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingComment that = (MenigaUpcomingComment) o;

		if (id != that.id) {
			return false;
		}
		if (created != null ? !created.equals(that.created) : that.created != null) {
			return false;
		}
		if (modified != null ? !modified.equals(that.modified) : that.modified != null) {
			return false;
		}
		return comment != null ? comment.equals(that.comment) : that.comment == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (created != null ? created.hashCode() : 0);
		result = 31 * result + (modified != null ? modified.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}

	public static final Creator<MenigaUpcomingComment> CREATOR = new Creator<MenigaUpcomingComment>() {
		@Override
		public MenigaUpcomingComment createFromParcel(Parcel source) {
			return new MenigaUpcomingComment(source);
		}

		@Override
		public MenigaUpcomingComment[] newArray(int size) {
			return new MenigaUpcomingComment[size];
		}
	};
}
