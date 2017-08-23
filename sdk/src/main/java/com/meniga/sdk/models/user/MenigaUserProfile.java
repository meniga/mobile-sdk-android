package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.enums.ApartmentType;
import com.meniga.sdk.models.user.enums.Gender;
import com.meniga.sdk.models.user.operators.MenigaUserProfileOperations;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUserProfile implements Serializable, Parcelable, Cloneable {

	public static final Parcelable.Creator<MenigaUserProfile> CREATOR = new Parcelable.Creator<MenigaUserProfile>() {
		@Override
		public MenigaUserProfile createFromParcel(Parcel source) {
			return new MenigaUserProfile(source);
		}

		@Override
		public MenigaUserProfile[] newArray(int size) {
			return new MenigaUserProfile[size];
		}
	};

	protected static MenigaUserProfileOperations apiOperator;

	protected Gender gender;
	protected LocalDate birthYear;
	protected DateTime created;
	protected Boolean hasSavedProfile;
	protected Integer incomeId;
	protected Integer numberInFamily;
	protected Integer numberOfCars;
	protected Integer numberOfKids;
	protected Integer apartmentRooms;
	protected Integer apartmentSize;
	protected Integer apartmentSizeKey;
	protected ApartmentType apartmentType;
	protected String postalCode;
	protected long personId;

	protected MenigaUserProfile() {
	}

	protected MenigaUserProfile(Parcel in) {
		int tmpGender = in.readInt();
		this.gender = tmpGender == -1 ? null : Gender.values()[tmpGender];
		this.birthYear = (LocalDate) in.readSerializable();
		this.created = (DateTime) in.readSerializable();
		this.hasSavedProfile = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.incomeId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.numberInFamily = (Integer) in.readValue(Integer.class.getClassLoader());
		this.numberOfCars = (Integer) in.readValue(Integer.class.getClassLoader());
		this.numberOfKids = (Integer) in.readValue(Integer.class.getClassLoader());
		this.apartmentRooms = (Integer) in.readValue(Integer.class.getClassLoader());
		this.apartmentSize = (Integer) in.readValue(Integer.class.getClassLoader());
		this.apartmentSizeKey = (Integer) in.readValue(Integer.class.getClassLoader());
		int tmpApartmentType = in.readInt();
		this.apartmentType = tmpApartmentType == -1 ? null : ApartmentType.values()[tmpApartmentType];
		this.postalCode = in.readString();
		this.personId = in.readLong();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaUserProfileOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaUserProfileOperations operator) {
		MenigaUserProfile.apiOperator = operator;
	}

	@Override
	protected MenigaUserProfile clone() throws CloneNotSupportedException {
		return (MenigaUserProfile) super.clone();
	}

	public Gender getGender() {
		return this.gender;
	}

	public LocalDate getBirthYear() {
		return this.birthYear;
	}

	public long getPersonId() {
		return this.personId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
		dest.writeSerializable(this.birthYear);
		dest.writeSerializable(this.created);
		dest.writeValue(this.hasSavedProfile);
		dest.writeValue(this.incomeId);
		dest.writeValue(this.numberInFamily);
		dest.writeValue(this.numberOfCars);
		dest.writeValue(this.numberOfKids);
		dest.writeValue(this.apartmentRooms);
		dest.writeValue(this.apartmentSize);
		dest.writeValue(this.apartmentSizeKey);
		dest.writeInt(this.apartmentType == null ? -1 : this.apartmentType.ordinal());
		dest.writeString(this.postalCode);
		dest.writeLong(this.personId);
	}

	/*
	 * API operations below
	 */

	/**
	 * Returns a user object for current logged in user.
	 *
	 * @return The user object
	 */
	public static Result<MenigaUserProfile> fetch() {
		return MenigaUserProfile.apiOperator.getUserProfile();
	}
}
