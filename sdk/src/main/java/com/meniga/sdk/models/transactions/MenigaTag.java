package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.transactions.operators.MenigaTagOperations;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a tag in the Meniga system. Tags can only be created as text in a comment by using the
 * # character, terminated by a non alpha numeric character. Tags can also only be deleted by
 * removing the the text from the comment (updating or deleting the comment).
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTag implements Parcelable, Serializable, Cloneable {
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public MenigaTag createFromParcel(Parcel in) {
			return new MenigaTag(in);
		}

		public MenigaTag[] newArray(int size) {
			return new MenigaTag[size];
		}
	};

	protected static MenigaTagOperations apiOperator;

	protected int id;
	protected String name;

	protected MenigaTag() {

	}

	protected MenigaTag(Parcel data) {
		this.id = data.readInt();
		this.name = data.readString();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTagOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTagOperations operator) {
		MenigaTag.apiOperator = operator;
	}

	@Override
	protected MenigaTag clone() throws CloneNotSupportedException {
		return (MenigaTag) super.clone();
	}

	/**
	 * @return Get the tag name. This is the tag's text
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name Set the tag name. This field can not be persisted to the server this way. To change
	 *             a tag you have to change the comment is is located in.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The tag ID.
	 */
	public int getId() {
		return this.id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaTag menigaTag = (MenigaTag) o;

		if (id != menigaTag.id) {
			return false;
		}
		return name != null ? name.equals(menigaTag.name) : menigaTag.name == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/*
	 * API operations below
	 */
    /*

	/**
	 * Get all tags the currently logged in user has created
	 *
	 * @return A Task containing a list of all transactions the user has
	 */
	public static Result<List<MenigaTag>> fetch() {
		return MenigaTag.apiOperator.getTags();
	}

	/**
	 * Get a tag with a specific id
	 *
	 * @param id The id of the tag to retrieve
	 * @return A Task containing the tag with the provided id
	 */
	public static Result<MenigaTag> fetch(long id) {
		return MenigaTag.apiOperator.getTag(id);
	}
}
