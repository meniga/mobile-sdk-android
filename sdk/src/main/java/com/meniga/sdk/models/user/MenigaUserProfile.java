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
import java.util.Objects;

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
	protected String currencyCode;

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
		this.currencyCode = in.readString();
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

	public String getCurrencyCode() {
		return currencyCode;
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
		dest.writeString(this.currencyCode);
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MenigaUserProfile that = (MenigaUserProfile) o;

		if (personId != that.personId)
			return false;
		if (gender != that.gender)
			return false;
		if (birthYear != null ? !birthYear.equals(that.birthYear) : that.birthYear != null)
			return false;
		if (created != null ? !created.equals(that.created) : that.created != null)
			return false;
		if (hasSavedProfile != null ? !hasSavedProfile.equals(that.hasSavedProfile) : that.hasSavedProfile != null)
			return false;
		if (incomeId != null ? !incomeId.equals(that.incomeId) : that.incomeId != null)
			return false;
		if (numberInFamily != null ? !numberInFamily.equals(that.numberInFamily) : that.numberInFamily != null)
			return false;
		if (numberOfCars != null ? !numberOfCars.equals(that.numberOfCars) : that.numberOfCars != null)
			return false;
		if (numberOfKids != null ? !numberOfKids.equals(that.numberOfKids) : that.numberOfKids != null)
			return false;
		if (apartmentRooms != null ? !apartmentRooms.equals(that.apartmentRooms) : that.apartmentRooms != null)
			return false;
		if (apartmentSize != null ? !apartmentSize.equals(that.apartmentSize) : that.apartmentSize != null)
			return false;
		if (apartmentSizeKey != null ? !apartmentSizeKey.equals(that.apartmentSizeKey) : that.apartmentSizeKey != null)
			return false;
		if (apartmentType != that.apartmentType)
			return false;
		if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null)
			return false;
		return currencyCode != null ? currencyCode.equals(that.currencyCode) : that.currencyCode == null;
	}

	@Override
	public int hashCode() {
		int result = gender != null ? gender.hashCode() : 0;
		result = 31 * result + (birthYear != null ? birthYear.hashCode() : 0);
		result = 31 * result + (created != null ? created.hashCode() : 0);
		result = 31 * result + (hasSavedProfile != null ? hasSavedProfile.hashCode() : 0);
		result = 31 * result + (incomeId != null ? incomeId.hashCode() : 0);
		result = 31 * result + (numberInFamily != null ? numberInFamily.hashCode() : 0);
		result = 31 * result + (numberOfCars != null ? numberOfCars.hashCode() : 0);
		result = 31 * result + (numberOfKids != null ? numberOfKids.hashCode() : 0);
		result = 31 * result + (apartmentRooms != null ? apartmentRooms.hashCode() : 0);
		result = 31 * result + (apartmentSize != null ? apartmentSize.hashCode() : 0);
		result = 31 * result + (apartmentSizeKey != null ? apartmentSizeKey.hashCode() : 0);
		result = 31 * result + (apartmentType != null ? apartmentType.hashCode() : 0);
		result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 31 * result + (int) (personId ^ (personId >>> 32));
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		return result;
	}
}
